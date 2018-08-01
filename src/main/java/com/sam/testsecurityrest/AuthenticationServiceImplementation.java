
package com.sam.testsecurityrest;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.sql.Date;
import java.util.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
public class AuthenticationServiceImplementation implements UserAuthenticationService {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public AuthenticationServiceImplementation(Repository repository) {
        this.repository = repository;
    }

    private Repository repository;

    @Override
    public String login(@RequestHeader HttpHeaders headers) {
        // fetch credentials by Basic Auth
            List<String> credentials = null;

            try {
                credentials = Utility.getLoginCredentials(headers);
            } catch (Exception e) {
                log.info("errore nella classe: " + this.getClass() +" " +  e.getMessage());
                e.printStackTrace();
                //reagire come si vuole
            }

            if(credentials != null) {
                String username = credentials.get(0);
                String password = credentials.get(1);
                log.info("username: " + username);
                log.info("password: " + password);


                User user = repository.findUserByUsername(username);
                if (user != null && user.getPassword().equals(password)) {
                    // utente presente nel DB

                    List<Role> roles = user.getRoles();
                    String id = user.getId();
                    String key = user.getKey();

                    //token creation
                    return Jwts.builder()
                            .setSubject(username)
                            .setExpiration(new Date(2018,10,10))
                            .claim("id", id)
                            .claim("ruoli", roles)
                            .signWith(SignatureAlgorithm.HS512, key)
                            .compact();

                } else
                    // utente non presente nel DB.
                    return "error";
            }
            return "error";

    }

    @Override
    public User findUserByUSername(String username) {
        return repository.findUserByUsername(username);
    }

    @Override
    public User findUserById(String id) {
        return repository.findUserById(id);
    }
}



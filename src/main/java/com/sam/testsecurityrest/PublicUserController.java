package com.sam.testsecurityrest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.deploy.net.HttpResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
//@CrossOrigin("*")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class PublicUserController {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    AuthenticationServiceImplementation authenticationServiceImplementation;

    @Autowired
    Repository repository;

    // registrazione utente
    @CrossOrigin("*")
    @PostMapping(value = "/register", produces = "application/json")
    ResponseEntity<Map<String, String>> register(@RequestHeader HttpHeaders headers) {
        log.info("login here !!!!!");
        // controllo se esiste già username !!!!

        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("sam")
                .password("asd")
                .roles(roles)
                .build();

        repository.insert(user);
        JsonBuilder json = new JsonBuilder<String, String>();
        json.addKeyValue("messaggio", "utente creato correttamente");
        log.info(json.getJson().toString());
        return new ResponseEntity<Map<String, String>>(json.getJson(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public User getUSer(@RequestHeader HttpHeaders headers) {

        log.info("called test");
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("sam")
                .password("123")
                .roles(roles)
                .build();
        return user;
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestHeader HttpHeaders headers) {
        String token = authenticationServiceImplementation.login(headers);

        if (authenticationServiceImplementation.login(headers).equals("error")) {
            // utente non presente.
            return new ResponseEntity<>(new JsonBuilder<String, String>().addKeyValueThis("messaggio", "errore di autenticazione").getJson(), HttpStatus.FORBIDDEN);
        } else {
            // utente presente nel DB
            JsonBuilder<String, String> json = new JsonBuilder<>();
            json.addKeyValue("token", token);
            return new ResponseEntity<>(json.getJson(), HttpStatus.OK);

        }
    }
}


package com.sam.testsecurityrest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.TextCodec;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bson.assertions.Assertions;
import org.bson.internal.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
//@AllArgsConstructor(access = PACKAGE)
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserAuthenticationService auth;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public TokenAuthenticationProvider(UserAuthenticationService auth) {
        this.auth = auth;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }



    // chiamata da TokenAuthenticationFilter -> attempt
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        log.info("Credenziali di accesso: " + (String) authentication.getCredentials());

        try {
            String token = (String) authentication.getCredentials();
            String id = null;

            log.info("the token: " + token);
            try {

                String key = TextCodec.BASE64.encode("secret");

                id = (String) Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("id");
                log.info(id); // user id

            } catch (SignatureException e) {
                log.info(e.getMessage());
            }

            if (id != null) {
                log.info(auth.findUserById(id).toString());
                return auth.findUserById(id);
            } else
                throw new AuthenticationCredentialsNotFoundException("errore token");
        } catch (Exception e) {
            log.info("sam-exception" + e.getMessage());
            throw new AuthenticationCredentialsNotFoundException("errore credenziali");
        }
    }
}

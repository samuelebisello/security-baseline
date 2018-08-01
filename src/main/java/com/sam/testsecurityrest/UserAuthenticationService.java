package com.sam.testsecurityrest;

import org.springframework.http.HttpHeaders;

import java.util.Optional;


public interface UserAuthenticationService {

    String login(HttpHeaders headers);
    User findUserById(String id);




}

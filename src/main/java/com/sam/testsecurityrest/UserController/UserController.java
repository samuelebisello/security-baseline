package com.sam.testsecurityrest.UserController;

import com.sam.testsecurityrest.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    List<User> getAllUsers() {
        return null;
        // ritornare solo nome e id;
    }

}

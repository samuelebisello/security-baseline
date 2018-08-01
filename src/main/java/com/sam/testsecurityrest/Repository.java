package com.sam.testsecurityrest;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends MongoRepository<User, String> {

    User insert(User user);
    User deleteUserById(String Username);
    User findUserById(String id);
    User findUserByUsername(String username);
    List<User> findAllByRoles(Role role);

}

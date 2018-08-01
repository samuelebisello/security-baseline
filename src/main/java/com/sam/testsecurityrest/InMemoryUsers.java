package com.sam.testsecurityrest;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryUsers { //implements UserCrudService {

/*    private static Map<String, User> users = new HashMap<>();
    static {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        User user = User.builder()
                .id("asd")
                .username("sam")
                .password("asd")
                .roles(roles)
                .build();
        users.put(user.getId(),user);
    }

    @Override
    public User save(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public Optional<User> find(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.
                values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .findFirst();
    }*/
}

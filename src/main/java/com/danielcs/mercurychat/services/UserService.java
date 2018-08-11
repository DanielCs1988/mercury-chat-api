package com.danielcs.mercurychat.services;

import com.danielcs.webserver.core.annotations.Dependency;

import java.util.HashSet;
import java.util.Set;

@Dependency
public class UserService {

    private Set<String> users = new HashSet<>();

    public Set<String> getUsers() {
        return users;
    }

    public void loginUser(String userId) {
        this.users.add(userId);
    }

    public void logoutUser(String userId) {
        this.users.remove(userId);
    }
}

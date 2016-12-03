package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.User;

public class UserImpl implements User {

    private long id;

    private String name;

    private String password;

    public UserImpl(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

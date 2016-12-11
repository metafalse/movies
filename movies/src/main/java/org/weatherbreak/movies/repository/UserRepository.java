package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.User;

import java.util.List;

public interface UserRepository {

    long addUser(User user);

    User getUser(long userId);

    List<User> getUsers();

    List<User> getUsersByName(String name);

    void update(User user);
}

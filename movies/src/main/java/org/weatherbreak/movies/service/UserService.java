package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User getUser(long userId);

    List<User> getUsers();

    List<User> getUsersByName(String name);

    void updateUser(User user);

    boolean isValidPassword(long userId, String password);
}

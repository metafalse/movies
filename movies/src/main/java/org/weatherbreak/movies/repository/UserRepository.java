package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.User;

public interface UserRepository {

    User getUser(long userId);
}

package org.weatherbreak.movies.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.UserImpl;
import org.weatherbreak.movies.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(long userId) {
        return (User) this.sessionFactory.getCurrentSession().get(UserImpl.class, userId);
    }
}

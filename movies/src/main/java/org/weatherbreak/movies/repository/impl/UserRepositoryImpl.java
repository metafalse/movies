package org.weatherbreak.movies.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.util.StringUtils;
import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.UserImpl;
import org.weatherbreak.movies.repository.UserRepository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long addUser(User user) {
        return (Long) this.sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getUser(long userId) {
        return (User) this.sessionFactory.getCurrentSession().get(UserImpl.class, userId);
    }

    @Override
    public List<User> getUsers() {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(UserImpl.class);
        List<User> users = crit.list();
        return users;
    }

    @Override
    public List<User> getUsersByName(String name) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(User.class);
        if(!StringUtils.isEmpty(name)){
            crit.add(Restrictions.like("name", "%" + name + "%"));
        }
        List<User> searchResult = crit.list();
        return searchResult;
    }

    @Override
    public void update(User user) {
        this.sessionFactory.getCurrentSession().update(user);
    }
}

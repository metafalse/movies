package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.User;

import javax.persistence.*;

@Entity
@Table(name="users")
public class UserImpl implements User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    public UserImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Movie;

import javax.persistence.*;

@Entity
@Table(name="movies")
public class MovieImpl implements Movie {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    public MovieImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}

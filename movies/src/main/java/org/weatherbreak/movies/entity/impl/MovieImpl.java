package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Movie;

public class MovieImpl implements Movie {

    private long id;

    private String name;

    public MovieImpl(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    };
}

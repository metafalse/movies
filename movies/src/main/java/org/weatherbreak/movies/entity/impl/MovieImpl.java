package org.weatherbreak.movies.entity.impl;

public class MovieImpl {

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

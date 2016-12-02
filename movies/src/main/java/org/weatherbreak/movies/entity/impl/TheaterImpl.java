package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Theater;

public class TheaterImpl implements Theater {

    private long id;

    private String name;

    public TheaterImpl(long id, String name) {
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

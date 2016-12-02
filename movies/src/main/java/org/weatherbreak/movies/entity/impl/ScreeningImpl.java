package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.Theater;

import java.util.Date;

public class ScreeningImpl implements Screening {

    private long id;

    private Date showTime;

    private Movie movie;

    private Theater theater;

    public ScreeningImpl(long id, Date showTime, Movie movie, Theater theater) {
        this.id = id;
        this.showTime = showTime;
        this.movie = movie;
        this.theater = theater;
    }

    public long getId() {
        return id;
    }

    public Date getShowTime() {
        return showTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theater getTheater() {
        return theater;
    }
}

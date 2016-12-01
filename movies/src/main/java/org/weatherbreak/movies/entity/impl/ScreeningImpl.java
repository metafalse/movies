package org.weatherbreak.movies.entity.impl;

import java.util.Date;

public class ScreeningImpl {

    private long id;

    private Date showTime;

    private MovieImpl movieImpl;

    private TheaterImpl theaterImpl;

    public ScreeningImpl(long id, Date showTime, MovieImpl movieImpl, TheaterImpl theaterImpl) {
        this.id = id;
        this.showTime = showTime;
        this.movieImpl = movieImpl;
        this.theaterImpl = theaterImpl;
    }

    public long getId() {
        return id;
    }

    public Date getShowTime() {
        return showTime;
    }

    public MovieImpl getMovieImpl() {
        return movieImpl;
    }

    public TheaterImpl getTheaterImpl() {
        return theaterImpl;
    }
}

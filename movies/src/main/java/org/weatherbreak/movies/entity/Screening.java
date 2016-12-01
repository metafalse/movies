package org.weatherbreak.movies.entity;

import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.util.Date;

public interface Screening {

    long getId();

    Date getShowTime();

    MovieImpl getMovie();

    TheaterImpl getTheater();
}

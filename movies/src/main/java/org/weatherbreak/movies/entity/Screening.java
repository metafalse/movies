package org.weatherbreak.movies.entity;

import java.util.Date;

public interface Screening {

    long getId();

    Movie getMovie();

    Theater getTheater();

    Date getShowtime();
}

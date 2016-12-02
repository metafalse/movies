package org.weatherbreak.movies.entity;

import java.util.Date;

public interface Screening {

    long getId();

    Date getShowTime();

    Movie getMovie();

    Theater getTheater();
}

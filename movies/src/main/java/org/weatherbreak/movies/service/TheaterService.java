package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.Theater;

import java.util.List;

public interface TheaterService {

    Theater addTheater(Theater theater);

    Theater getTheater(long theaterId);

    List<Theater> getTheaters();

    List<Theater> getTheatersByName(String theaterName);

    List<Theater> getTheatersByMovieId(long movieId);
}

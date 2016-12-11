package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.Theater;

import java.util.List;

public interface TheaterRepository {

    long addTheater(Theater theater);

    Theater getTheater(long theaterId);

    List<Theater> getTheaters();

    List<Theater> getTheatersByName(String theaterName);
}

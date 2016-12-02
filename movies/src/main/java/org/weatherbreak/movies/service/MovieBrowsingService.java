package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Theater;

import java.util.List;

public interface MovieBrowsingService {

    List<Movie> getMovies();

    List<Movie> getMoviesByName(String name);

    List<Movie> getMoviesByTheaterId(long id);

    List<Theater> getTheaters();

    List<Theater> getTheatersByName(String name);

    List<Theater> getTheatersByMovieId(long id);
}

package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Theater;

import java.util.List;

public interface MovieBrowsingService {

    List<Movie> getMovies();

    List<Movie> getMoviesByName(String movieName);

    List<Movie> getMoviesByTheaterId(long theaterId);

    List<Theater> getTheaters();

    List<Theater> getTheatersByName(String theaterName);

    List<Theater> getTheatersByMovieId(long movieId);
}

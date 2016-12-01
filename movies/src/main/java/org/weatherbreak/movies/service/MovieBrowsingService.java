package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.util.List;

public interface MovieBrowsingService {

    List<MovieImpl> getMovies();

    List<MovieImpl> getMoviesByName(String name);

    List<MovieImpl> getMoviesByTheaterId(long id);

    List<TheaterImpl> getTheaters();

    List<TheaterImpl> getTheatersByName(String name);

    List<TheaterImpl> getTheatersByMovieId(long id);
}

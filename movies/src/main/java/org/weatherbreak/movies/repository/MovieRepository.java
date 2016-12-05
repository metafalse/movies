package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.Movie;

import java.util.List;

public interface MovieRepository {

    List<Movie> getMovies();

    List<Movie> getMoviesByName(String movieName);
}

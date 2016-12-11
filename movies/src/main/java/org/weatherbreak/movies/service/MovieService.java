package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie addMovie(Movie movie);

    Movie getMovie(long movieId);

    List<Movie> getMovies();

    List<Movie> getMoviesByName(String movieName);

    List<Movie> getMoviesByTheaterId(long theaterId);
}

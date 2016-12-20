package org.weatherbreak.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.repository.MovieRepository;
import org.weatherbreak.movies.repository.ScreeningRepository;
import org.weatherbreak.movies.service.MovieService;
import org.weatherbreak.movies.service.exception.ErrorCode;
import org.weatherbreak.movies.service.exception.InvalidFieldException;
import org.weatherbreak.movies.service.exception.MoviesException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private static final int MAX_NAME_LENGTH = 45;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    @Transactional
    public Movie addMovie(Movie movie) {
        if (StringUtils.isEmpty(movie.getName()) || movie.getName().length() > MAX_NAME_LENGTH) {
            throw new InvalidFieldException("name is invalid");
        }

        long movieId = movieRepository.addMovie(movie);
        return getMovie(movieId);
    }

    @Override
    @Transactional
    public Movie getMovie(long movieId) {
        return movieRepository.getMovie(movieId);
    }

    @Override
    @Transactional
    public List<Movie> getMovies() {
        return movieRepository.getMovies();
    }

    @Override
    @Transactional
    public List<Movie> getMoviesByName(String movieName) {
        if (StringUtils.isEmpty(movieName)){
            throw new MoviesException(ErrorCode.MISSING_DATA, "no search parameter provided");
        }
        return movieRepository.getMoviesByName(movieName);
    }

    @Override
    @Transactional
    public List<Movie> getMoviesByTheaterId(long theaterId) {
        List<Movie> movies = new ArrayList<>();
        List<Screening> screenings = screeningRepository.getScreeningsByTheaterId(theaterId);
        for (Screening screening : screenings) {
            movies.add(screening.getMovie());
        }
        HashSet<Movie> hashSet = new HashSet<>();
        hashSet.addAll(movies);
        List<Movie> uniqueList = new ArrayList<>();
        uniqueList.addAll(hashSet);
        return uniqueList;
    }
}

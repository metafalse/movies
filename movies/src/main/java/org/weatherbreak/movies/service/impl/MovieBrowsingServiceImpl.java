package org.weatherbreak.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.repository.MovieRepository;
import org.weatherbreak.movies.repository.ScreeningRepository;
import org.weatherbreak.movies.repository.TheaterRepository;
import org.weatherbreak.movies.service.MovieBrowsingService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieBrowsingServiceImpl implements MovieBrowsingService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    @Transactional
    public List<Movie> getMovies() {
        return movieRepository.getMovies();
    }

    @Override
    @Transactional
    public List<Movie> getMoviesByName(String movieName) {
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
        return movies;
    }

    @Override
    @Transactional
    public List<Theater> getTheaters() {
        return theaterRepository.getTheaters();
    }

    @Override
    @Transactional
    public List<Theater> getTheatersByName(String theaterName) {
        return theaterRepository.getTheatersByName(theaterName);
    }

    @Override
    @Transactional
    public List<Theater> getTheatersByMovieId(long movieId) {
        List<Theater> theaters = new ArrayList<>();
        List<Screening> screenings = screeningRepository.getScreeningsByMovieId(movieId);
        for (Screening screening : screenings) {
            theaters.add(screening.getTheater());
        }
        return theaters;
    }
}

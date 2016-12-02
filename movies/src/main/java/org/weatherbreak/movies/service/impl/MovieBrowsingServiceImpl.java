package org.weatherbreak.movies.service.impl;

import org.springframework.stereotype.Service;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.ScreeningImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;
import org.weatherbreak.movies.service.MovieBrowsingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieBrowsingServiceImpl implements MovieBrowsingService {

    private List<Movie> movies;
    private List<Theater> theaters;
    private List<Screening> screenings;

    public MovieBrowsingServiceImpl() {
        Movie et      = new MovieImpl(1, "E.T. the Extra-Terrestrial");
        Movie titanic = new MovieImpl(2, "Titanic");
        Movie shining = new MovieImpl(3, "The Shining");

        Theater sf = new TheaterImpl(1, "San Francisco theater");
        Theater sj = new TheaterImpl(2, "San Jose theater");
        Theater dc = new TheaterImpl(3, "Daly City theater");

        movies = Arrays.asList(et, titanic, shining);
        theaters = Arrays.asList(sf, sj, dc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        try {
            screenings = Arrays.asList(
                new ScreeningImpl(1, sdf.parse("2016-12-24 11:11:11"), et, sf),
                new ScreeningImpl(2, sdf.parse("2016-12-25 12:12:12"), et, sj),
                new ScreeningImpl(3, sdf.parse("2016-12-26 13:13:13"), titanic, sj)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Movie> getMoviesByName(String name) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getName().contains(name))
                result.add(movie);
        }
        return result;
    }

    public List<Movie> getMoviesByTheaterId(long id) {
        List<Movie> result = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getTheater().getId() == id)
                result.add(screening.getMovie());
        }
        return result;
    }

    public List<Theater> getTheaters() {
        return theaters;
    }

    public List<Theater> getTheatersByName(String name) {
        List<Theater> result = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.getName().contains(name))
                result.add(theater);
        }
        return result;
    }

    public List<Theater> getTheatersByMovieId(long id) {
        List<Theater> result = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getMovie().getId() == id)
                result.add(screening.getTheater());
        }
        return result;
    }
}

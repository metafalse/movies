package org.weatherbreak.movies.service.impl;

import org.springframework.stereotype.Service;
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

    private List<MovieImpl> movieImpls;
    private List<TheaterImpl> theaterImpls;
    private List<ScreeningImpl> screeningImpls;

    public MovieBrowsingServiceImpl() {
        MovieImpl et      = new MovieImpl(1, "E.T. the Extra-Terrestrial");
        MovieImpl titanic = new MovieImpl(2, "Titanic");
        MovieImpl shining = new MovieImpl(3, "The Shining");

        TheaterImpl sf = new TheaterImpl(1, "San Francisco theater");
        TheaterImpl sj = new TheaterImpl(2, "San Jose theater");
        TheaterImpl dc = new TheaterImpl(3, "Daly City theater");

        movieImpls = Arrays.asList(et, titanic, shining);
        theaterImpls = Arrays.asList(sf, sj, dc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        try {
            screeningImpls = Arrays.asList(
                new ScreeningImpl(1, sdf.parse("2016-12-24 11:11:11"), et, sf),
                new ScreeningImpl(2, sdf.parse("2016-12-25 12:12:12"), et, sj),
                new ScreeningImpl(3, sdf.parse("2016-12-26 13:13:13"), titanic, sj)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<MovieImpl> getMovies() {
        return movieImpls;
    }

    public List<MovieImpl> getMoviesByName(String name) {
        List<MovieImpl> result = new ArrayList<>();
        for (MovieImpl movieImpl : movieImpls) {
            if (movieImpl.getName().contains(name))
                result.add(movieImpl);
        }
        return result;
    }

    public List<MovieImpl> getMoviesByTheaterId(long id) {
        List<MovieImpl> result = new ArrayList<>();
        for (ScreeningImpl screeningImpl : screeningImpls) {
            if (screeningImpl.getTheaterImpl().getId() == id)
                result.add(screeningImpl.getMovieImpl());
        }
        return result;
    }

    public List<TheaterImpl> getTheaters() {
        return theaterImpls;
    }

    public List<TheaterImpl> getTheatersByName(String name) {
        List<TheaterImpl> result = new ArrayList<>();
        for (TheaterImpl theaterImpl : theaterImpls) {
            if (theaterImpl.getName().contains(name))
                result.add(theaterImpl);
        }
        return result;
    }

    public List<TheaterImpl> getTheatersByMovieId(long id) {
        List<TheaterImpl> result = new ArrayList<>();
        for (ScreeningImpl screeningImpl : screeningImpls) {
            if (screeningImpl.getMovieImpl().getId() == id)
                result.add(screeningImpl.getTheaterImpl());
        }
        return result;
    }
}

package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.ScreeningImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestMovieService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private ScreeningService screeningService;

    private static Movie etAdded;
    private static Movie titanicAdded;
    private static Movie shiningAdded;
    private static Theater sfAdded;
    private static Theater sjAdded;
    private static Theater dcAdded;

    @Before
    public void beforeTestClass() {
        MovieImpl et = new MovieImpl();
        MovieImpl titanic = new MovieImpl();
        MovieImpl shining = new MovieImpl();

        et.setName("E.T. the Extra-Terrestrial");
        titanic.setName("Titanic");
        shining.setName("The Shining");

        etAdded = movieService.addMovie(et);
        titanicAdded = movieService.addMovie(titanic);
        shiningAdded = movieService.addMovie(shining);

        TheaterImpl sf = new TheaterImpl();
        TheaterImpl sj = new TheaterImpl();
        TheaterImpl dc = new TheaterImpl();

        sf.setName("San Francisco theater");
        sj.setName("San Jose theater");
        dc.setName("Daly City theater");

        sfAdded = theaterService.addTheater(sf);
        sjAdded = theaterService.addTheater(sj);
        dcAdded = theaterService.addTheater(dc);

        ScreeningImpl scr1 = new ScreeningImpl();
        ScreeningImpl scr2 = new ScreeningImpl();
        ScreeningImpl scr3 = new ScreeningImpl();

        scr1.setMovie(etAdded);
        scr2.setMovie(etAdded);
        scr3.setMovie(titanicAdded);

        scr1.setTheater(sfAdded);
        scr2.setTheater(sjAdded);
        scr3.setTheater(sjAdded);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            scr1.setShowtime(sdf.parse("2016-12-24 11:11:11"));
            scr2.setShowtime(sdf.parse("2016-12-25 12:12:12"));
            scr3.setShowtime(sdf.parse("2016-12-26 13:13:13"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        screeningService.addScreening(scr1);
        screeningService.addScreening(scr2);
        screeningService.addScreening(scr3);
    }

    @Test
    public void addMovie() {
        Assert.assertNotEquals(0, etAdded.getId());
        Assert.assertNotEquals(0, titanicAdded.getId());
        Assert.assertNotEquals(0, shiningAdded.getId());
    }

    @Test
    public void getMovie() {
        Movie found1 = movieService.getMovie(etAdded.getId());
        Assert.assertEquals(etAdded.getId(),   found1.getId());
        Assert.assertEquals(etAdded.getName(), found1.getName());

        Movie found2 = movieService.getMovie(titanicAdded.getId());
        Assert.assertEquals(titanicAdded.getId(),   found2.getId());
        Assert.assertEquals(titanicAdded.getName(), found2.getName());

        Movie found3 = movieService.getMovie(shiningAdded.getId());
        Assert.assertEquals(shiningAdded.getId(),   found3.getId());
        Assert.assertEquals(shiningAdded.getName(), found3.getName());
    }

    @Test
    public void getMovies() {
        List<Movie> founds = movieService.getMovies();

        for (Movie found : founds) {
            if (found.getId() == etAdded.getId())
                Assert.assertEquals("E.T. the Extra-Terrestrial", found.getName());

            if (found.getId() == titanicAdded.getId())
                Assert.assertEquals("Titanic", found.getName());

            if (found.getId() == shiningAdded.getId())
                Assert.assertEquals("The Shining", found.getName());
        }
    }

    @Test
    public void getMoviesByName() {
        List<Movie> founds1 = movieService.getMoviesByName("E.T.");

        for (Movie found : founds1) {
            if (found.getId() == etAdded.getId())
                Assert.assertEquals("E.T. the Extra-Terrestrial", found.getName());
        }

        List<Movie> founds2 = movieService.getMoviesByName("ni");

        for (Movie found : founds2) {
            if (found.getId() == titanicAdded.getId())
                Assert.assertEquals("Titanic", found.getName());

            if (found.getId() == shiningAdded.getId())
                Assert.assertEquals("The Shining", found.getName());
        }

        List<Movie> notFound = movieService.getMoviesByName("Kin-dza-dza!");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getMoviesByTheaterId() {
        List<Movie> founds1 = movieService.getMoviesByTheaterId(sfAdded.getId());

        for (Movie found : founds1) {
            if (found.getId() == etAdded.getId())
                Assert.assertEquals("E.T. the Extra-Terrestrial", found.getName());
        }

        List<Movie> founds2 = movieService.getMoviesByTheaterId(sjAdded.getId());

        for (Movie found : founds2) {
            if (found.getId() == etAdded.getId())
                Assert.assertEquals("E.T. the Extra-Terrestrial", found.getName());

            if (found.getId() == titanicAdded.getId())
                Assert.assertEquals("Titanic", found.getName());
        }

        List<Movie> notFound = movieService.getMoviesByTheaterId(dcAdded.getId());
        Assert.assertEquals(0, notFound.size());
    }
}

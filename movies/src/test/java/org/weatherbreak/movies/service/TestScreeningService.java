package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.ScreeningImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestScreeningService extends AbstractJUnit4SpringContextTests {
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
    private static Screening scr1Added;
    private static Screening scr2Added;
    private static Screening scr3Added;

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

        scr1Added = screeningService.addScreening(scr1);
        scr2Added = screeningService.addScreening(scr2);
        scr3Added = screeningService.addScreening(scr3);
    }

    @Test
    public void addScreening() {
        Assert.assertNotEquals(0, scr1Added.getId());
        Assert.assertNotEquals(0, scr2Added.getId());
        Assert.assertNotEquals(0, scr3Added.getId());
    }

    @Test
    public void getScreening() {
        Screening found1 = screeningService.getScreening(scr1Added.getId());
        Assert.assertEquals(scr1Added.getId(),              found1.getId());
        Assert.assertEquals(scr1Added.getMovie().getId(),   found1.getMovie().getId());
        Assert.assertEquals(scr1Added.getTheater().getId(), found1.getTheater().getId());
        Assert.assertEquals("2016-12-24 11:11:11.0",        found1.getShowtime().toString());

        Screening found2 = screeningService.getScreening(scr2Added.getId());
        Assert.assertEquals(scr2Added.getId(),              found2.getId());
        Assert.assertEquals(scr2Added.getMovie().getId(),   found2.getMovie().getId());
        Assert.assertEquals(scr2Added.getTheater().getId(), found2.getTheater().getId());
        Assert.assertEquals("2016-12-25 12:12:12.0",        found2.getShowtime().toString());

        Screening found3 = screeningService.getScreening(scr3Added.getId());
        Assert.assertEquals(scr3Added.getId(),              found3.getId());
        Assert.assertEquals(scr3Added.getMovie().getId(),   found3.getMovie().getId());
        Assert.assertEquals(scr3Added.getTheater().getId(), found3.getTheater().getId());
        Assert.assertEquals("2016-12-26 13:13:13.0",        found3.getShowtime().toString());
    }

    @Test
    public void getScreenings() {
        List<Screening> founds = screeningService.getScreenings();

        for (Screening found : founds) {
            if (found.getId() == scr1Added.getId()) {
                Assert.assertEquals(scr1Added.getId(),              found.getId());
                Assert.assertEquals(scr1Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr1Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-24 11:11:11.0",        found.getShowtime().toString());
            }

            if (found.getId() == scr2Added.getId()) {
                Assert.assertEquals(scr2Added.getId(),              found.getId());
                Assert.assertEquals(scr2Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr2Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-25 12:12:12.0",        found.getShowtime().toString());
            }

            if (found.getId() == scr3Added.getId()) {
                Assert.assertEquals(scr3Added.getId(),              found.getId());
                Assert.assertEquals(scr3Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr3Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-26 13:13:13.0",        found.getShowtime().toString());
            }
        }
    }

    @Test
    public void getScreeningsByMovieId() {
        List<Screening> founds1 = screeningService.getScreeningsByMovieId(etAdded.getId());

        for (Screening found : founds1) {
            if (found.getId() == scr1Added.getId()) {
                Assert.assertEquals(scr1Added.getId(),              found.getId());
                Assert.assertEquals(scr1Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr1Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-24 11:11:11.0",        found.getShowtime().toString());
            }

            if (found.getId() == scr2Added.getId()) {
                Assert.assertEquals(scr2Added.getId(),              found.getId());
                Assert.assertEquals(scr2Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr2Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-25 12:12:12.0",        found.getShowtime().toString());
            }
        }

        List<Screening> founds2 = screeningService.getScreeningsByMovieId(titanicAdded.getId());

        for (Screening found : founds2) {
            if (found.getId() == scr3Added.getId()) {
                Assert.assertEquals(scr3Added.getId(),              found.getId());
                Assert.assertEquals(scr3Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr3Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-26 13:13:13.0",        found.getShowtime().toString());
            }
        }

        List<Screening> notFound = screeningService.getScreeningsByMovieId(shiningAdded.getId());
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getScreeningsByTheaterId() {
        List<Screening> founds1 = screeningService.getScreeningsByTheaterId(sfAdded.getId());

        for (Screening found : founds1) {
            if (found.getId() == scr1Added.getId()) {
                Assert.assertEquals(scr1Added.getId(),              found.getId());
                Assert.assertEquals(scr1Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr1Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-24 11:11:11.0",        found.getShowtime().toString());
            }
        }

        List<Screening> founds2 = screeningService.getScreeningsByTheaterId(sjAdded.getId());

        for (Screening found : founds2) {
            if (found.getId() == scr3Added.getId()) {
                Assert.assertEquals(scr3Added.getId(),              found.getId());
                Assert.assertEquals(scr3Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr3Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-26 13:13:13.0",        found.getShowtime().toString());
            }

            if (found.getId() == scr2Added.getId()) {
                Assert.assertEquals(scr2Added.getId(),              found.getId());
                Assert.assertEquals(scr2Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr2Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-25 12:12:12.0",        found.getShowtime().toString());
            }
        }

        List<Screening> notFound = screeningService.getScreeningsByTheaterId(dcAdded.getId());
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getScreeningsByMovieIdAndTheaterId() {
        List<Screening> founds1 = screeningService.getScreeningsByMovieIdAndTheaterId(etAdded.getId(), sfAdded.getId());

        for (Screening found : founds1) {
            if (found.getId() == scr1Added.getId()) {
                Assert.assertEquals(scr1Added.getId(),              found.getId());
                Assert.assertEquals(scr1Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr1Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-24 11:11:11.0",        found.getShowtime().toString());
            }
        }

        List<Screening> founds2 = screeningService.getScreeningsByMovieIdAndTheaterId(etAdded.getId(), sjAdded.getId());

        for (Screening found : founds2) {
            if (found.getId() == scr3Added.getId()) {
                Assert.assertEquals(scr3Added.getId(),              found.getId());
                Assert.assertEquals(scr3Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr3Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-26 13:13:13.0",        found.getShowtime().toString());
            }
        }

        List<Screening> founds3 = screeningService.getScreeningsByMovieIdAndTheaterId(titanicAdded.getId(), sjAdded.getId());

        for (Screening found : founds3) {
            if (found.getId() == scr2Added.getId()) {
                Assert.assertEquals(scr2Added.getId(),              found.getId());
                Assert.assertEquals(scr2Added.getMovie().getId(),   found.getMovie().getId());
                Assert.assertEquals(scr2Added.getTheater().getId(), found.getTheater().getId());
                Assert.assertEquals("2016-12-25 12:12:12.0",        found.getShowtime().toString());
            }
        }

        List<Screening> notFound = screeningService.getScreeningsByMovieIdAndTheaterId(shiningAdded.getId(), dcAdded.getId());
        Assert.assertEquals(0, notFound.size());
    }
}

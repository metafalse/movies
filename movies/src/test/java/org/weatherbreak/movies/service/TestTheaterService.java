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
public class TestTheaterService extends AbstractJUnit4SpringContextTests {
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
    public void addTheater() {
        Assert.assertNotEquals(0, sfAdded.getId());
        Assert.assertNotEquals(0, sjAdded.getId());
        Assert.assertNotEquals(0, dcAdded.getId());
    }

    @Test
    public void getTheater() {
        Theater found1 = theaterService.getTheater(sfAdded.getId());
        Assert.assertEquals(sfAdded.getId(),   found1.getId());
        Assert.assertEquals(sfAdded.getName(), found1.getName());

        Theater found2 = theaterService.getTheater(sjAdded.getId());
        Assert.assertEquals(sjAdded.getId(),   found2.getId());
        Assert.assertEquals(sjAdded.getName(), found2.getName());

        Theater found3 = theaterService.getTheater(dcAdded.getId());
        Assert.assertEquals(dcAdded.getId(),   found3.getId());
        Assert.assertEquals(dcAdded.getName(), found3.getName());
    }

    @Test
    public void getTheaters() {
        List<Theater> founds = theaterService.getTheaters();

        for (Theater found : founds) {
            if (found.getId() == sfAdded.getId())
                Assert.assertEquals("San Francisco theater", found.getName());

            if (found.getId() == sjAdded.getId())
                Assert.assertEquals("San Jose theater", found.getName());

            if (found.getId() == dcAdded.getId())
                Assert.assertEquals("Daly City theater", found.getName());
        }
    }

    @Test
    public void getTheatersByName() {
        List<Theater> founds1 = theaterService.getTheatersByName("San");

        for (Theater found : founds1) {
            if (found.getId() == sfAdded.getId())
                Assert.assertEquals("San Francisco theater", found.getName());

            if (found.getId() == sjAdded.getId())
                Assert.assertEquals("San Jose theater", found.getName());
        }

        List<Theater> founds2 = theaterService.getTheatersByName("City");

        for (Theater found : founds2) {
            if (found.getId() == dcAdded.getId())
                Assert.assertEquals("Daly City theater", found.getName());
        }

        List<Theater> notFound = theaterService.getTheatersByName("Antarctic");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getTheatersByMovieId() {
        List<Theater> founds1 = theaterService.getTheatersByMovieId(etAdded.getId());

        for (Theater found : founds1) {
            if (found.getId() == sfAdded.getId())
                Assert.assertEquals("San Francisco theater", found.getName());

            if (found.getId() == sjAdded.getId())
                Assert.assertEquals("San Jose theater", found.getName());
        }

        List<Theater> founds2 = theaterService.getTheatersByMovieId(titanicAdded.getId());

        for (Theater found : founds2) {
            if (found.getId() == sjAdded.getId())
                Assert.assertEquals("San Jose theater", found.getName());
        }

        List<Theater> notFound = theaterService.getTheatersByMovieId(shiningAdded.getId());
        Assert.assertEquals(0, notFound.size());
    }
}

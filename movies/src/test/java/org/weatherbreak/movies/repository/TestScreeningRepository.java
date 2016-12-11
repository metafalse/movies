package org.weatherbreak.movies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.ScreeningImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestScreeningRepository extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    private static long etId;
    private static long titanicId;
    private static long shiningId;
    private static long sfId;
    private static long sjId;
    private static long dcId;
    private static long scr1Id;
    private static long scr2Id;
    private static long scr3Id;

    @Before
    public void beforeTestClass() {
        MovieImpl et = new MovieImpl();
        MovieImpl titanic = new MovieImpl();
        MovieImpl shining = new MovieImpl();

        et.setName("E.T. the Extra-Terrestrial");
        titanic.setName("Titanic");
        shining.setName("The Shining");

        etId = movieRepository.addMovie(et);
        titanicId = movieRepository.addMovie(titanic);
        shiningId = movieRepository.addMovie(shining);

        TheaterImpl sf = new TheaterImpl();
        TheaterImpl sj = new TheaterImpl();
        TheaterImpl dc = new TheaterImpl();

        sf.setName("San Francisco theater");
        sj.setName("San Jose theater");
        dc.setName("Daly City theater");

        sfId = theaterRepository.addTheater(sf);
        sjId = theaterRepository.addTheater(sj);
        dcId = theaterRepository.addTheater(dc);

        ScreeningImpl scr1 = new ScreeningImpl();
        ScreeningImpl scr2 = new ScreeningImpl();
        ScreeningImpl scr3 = new ScreeningImpl();

        scr1.setMovie(movieRepository.getMovie(etId));
        scr2.setMovie(movieRepository.getMovie(etId));
        scr3.setMovie(movieRepository.getMovie(titanicId));

        scr1.setTheater(theaterRepository.getTheater(sfId));
        scr2.setTheater(theaterRepository.getTheater(sjId));
        scr3.setTheater(theaterRepository.getTheater(sjId));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            scr1.setShowtime(sdf.parse("2016-12-24 11:11:11"));
            scr2.setShowtime(sdf.parse("2016-12-25 12:12:12"));
            scr3.setShowtime(sdf.parse("2016-12-26 13:13:13"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        scr1Id = screeningRepository.addScreening(scr1);
        scr2Id = screeningRepository.addScreening(scr2);
        scr3Id = screeningRepository.addScreening(scr3);
    }

    @Test
    public void addScreening() {
        Assert.assertNotEquals(0, scr1Id);
        Assert.assertNotEquals(0, scr2Id);
        Assert.assertNotEquals(0, scr3Id);
    }

    @Test
    public void getScreening() {
        Screening found1 = screeningRepository.getScreening(scr1Id);

        if (found1.getId() == scr1Id) {
            Assert.assertEquals(etId, found1.getMovie().getId());
            Assert.assertEquals(sfId, found1.getTheater().getId());
            Assert.assertEquals("Sat Dec 24 11:11:11 PST 2016", found1.getShowtime().toString());
        }

        Screening found2 = screeningRepository.getScreening(scr2Id);

        if (found2.getId() == scr2Id) {
            Assert.assertEquals(etId, found2.getMovie().getId());
            Assert.assertEquals(sjId, found2.getTheater().getId());
            Assert.assertEquals("Sun Dec 25 12:12:12 PST 2016", found2.getShowtime().toString());
        }

        Screening found3 = screeningRepository.getScreening(scr3Id);

        if (found3.getId() == scr3Id) {
            Assert.assertEquals(titanicId, found3.getMovie().getId());
            Assert.assertEquals(sjId, found3.getTheater().getId());
            Assert.assertEquals("Mon Dec 26 13:13:13 PST 2016", found3.getShowtime().toString());
        }
    }

    @Test
    public void getScreenings() {
        List<Screening> founds = screeningRepository.getScreenings();

        for (Screening found : founds) {
            if (found.getId() == scr1Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sfId, found.getTheater().getId());
                Assert.assertEquals("Sat Dec 24 11:11:11 PST 2016", found.getShowtime().toString());
            }

            if (found.getId() == scr2Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sjId, found.getTheater().getId());
                Assert.assertEquals("Sun Dec 25 12:12:12 PST 2016", found.getShowtime().toString());
            }

            if (found.getId() == scr3Id) {
                Assert.assertEquals(titanicId, found.getMovie().getId());
                Assert.assertEquals(sjId, found.getTheater().getId());
                Assert.assertEquals("Mon Dec 26 13:13:13 PST 2016", found.getShowtime().toString());
            }
        }
    }

    @Test
    public void getScreeningsByMovieId() {
        List<Screening> founds1 = screeningRepository.getScreeningsByMovieId(etId);

        for (Screening found : founds1) {
            if (found.getId() == scr1Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sfId, found.getTheater().getId());
                Assert.assertEquals("Sat Dec 24 11:11:11 PST 2016", found.getShowtime().toString());
            }

            if (found.getId() == scr2Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sjId, found.getTheater().getId());
                Assert.assertEquals("Sun Dec 25 12:12:12 PST 2016", found.getShowtime().toString());
            }
        }

        List<Screening> founds2 = screeningRepository.getScreeningsByMovieId(titanicId);

        for (Screening found : founds2) {
            if (found.getId() == scr3Id) {
                Assert.assertEquals(titanicId, found.getMovie().getId());
                Assert.assertEquals(sjId, found.getTheater().getId());
                Assert.assertEquals("Mon Dec 26 13:13:13 PST 2016", found.getShowtime().toString());
            }
        }

        List<Screening> notFound = screeningRepository.getScreeningsByMovieId(shiningId);
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getScreeningsByTheaterId() {
        List<Screening> founds1 = screeningRepository.getScreeningsByTheaterId(sfId);

        for (Screening found : founds1) {
            if (found.getId() == scr1Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sfId, found.getTheater().getId());
                Assert.assertEquals("Sat Dec 24 11:11:11 PST 2016", found.getShowtime().toString());
            }
        }

        List<Screening> founds2 = screeningRepository.getScreeningsByTheaterId(sjId);

        for (Screening found : founds2) {
            if (found.getId() == scr2Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sjId, found.getTheater().getId());
                Assert.assertEquals("Sun Dec 25 12:12:12 PST 2016", found.getShowtime().toString());
            }

            if (found.getId() == scr3Id) {
                Assert.assertEquals(titanicId, found.getMovie().getId());
                Assert.assertEquals(sjId, found.getTheater().getId());
                Assert.assertEquals("Mon Dec 26 13:13:13 PST 2016", found.getShowtime().toString());
            }
        }

        List<Screening> notFound = screeningRepository.getScreeningsByTheaterId(dcId);
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getScreeningsByTheaterIdAndMovieId() {
        List<Screening> founds1 = screeningRepository.getScreeningsByMovieIdAndTheaterId(etId, sfId);

        for (Screening found : founds1) {
            if (found.getId() == scr1Id) {
                Assert.assertEquals(etId, found.getMovie().getId());
                Assert.assertEquals(sfId, found.getTheater().getId());
                Assert.assertEquals("Sat Dec 24 11:11:11 PST 2016", found.getShowtime().toString());
            }
        }

        List<Screening> founds2 = screeningRepository.getScreeningsByMovieIdAndTheaterId(etId, sjId);

        for (Screening found : founds2) {
            Assert.assertEquals(etId, found.getMovie().getId());
            Assert.assertEquals(sjId, found.getTheater().getId());
            Assert.assertEquals("Sun Dec 25 12:12:12 PST 2016", found.getShowtime().toString());
        }

        List<Screening> founds3 = screeningRepository.getScreeningsByMovieIdAndTheaterId(titanicId, sjId);

        for (Screening found : founds3) {
            Assert.assertEquals(titanicId, found.getMovie().getId());
            Assert.assertEquals(sjId, found.getTheater().getId());
            Assert.assertEquals("Mon Dec 26 13:13:13 PST 2016", found.getShowtime().toString());
        }

        List<Screening> notFound = screeningRepository.getScreeningsByMovieIdAndTheaterId(shiningId, dcId);
        Assert.assertEquals(0, notFound.size());
    }
}

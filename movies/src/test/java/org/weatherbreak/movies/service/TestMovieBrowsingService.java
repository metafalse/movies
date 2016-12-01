package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestMovieBrowsingService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MovieBrowsingService movieBrowsingService;

    @Test
    public void getMovies() {
        List<MovieImpl> moviesImpl = movieBrowsingService.getMovies();
        Assert.assertEquals(1, moviesImpl.get(0).getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", moviesImpl.get(0).getName());

        Assert.assertEquals(2, moviesImpl.get(1).getId());
        Assert.assertEquals("Titanic", moviesImpl.get(1).getName());

        Assert.assertEquals(3, moviesImpl.get(2).getId());
        Assert.assertEquals("The Shining", moviesImpl.get(2).getName());
    }

    @Test
    public void getMoviesByName() {
        MovieImpl et = movieBrowsingService.getMoviesByName("E.T.").get(0);
        Assert.assertEquals(1, et.getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", et.getName());

        List<MovieImpl> titanicAndShining = movieBrowsingService.getMoviesByName("ni");
        Assert.assertEquals(2, titanicAndShining.get(0).getId());
        Assert.assertEquals("Titanic", titanicAndShining.get(0).getName());
        Assert.assertEquals(3, titanicAndShining.get(1).getId());
        Assert.assertEquals("The Shining", titanicAndShining.get(1).getName());
    }

    @Test
    public void getMoviesByTheaterId() {
        MovieImpl et = movieBrowsingService.getMoviesByTheaterId(1).get(0);
        Assert.assertEquals(1, et.getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", et.getName());

        List<MovieImpl> etAndTitanic = movieBrowsingService.getMoviesByTheaterId(2);
        Assert.assertEquals(1, etAndTitanic.get(0).getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", etAndTitanic.get(0).getName());
        Assert.assertEquals(2, etAndTitanic.get(1).getId());
        Assert.assertEquals("Titanic", etAndTitanic.get(1).getName());
    }

    @Test
    public void getTheaters() {
        List<TheaterImpl> theatersImpl = movieBrowsingService.getTheaters();
        Assert.assertEquals(1, theatersImpl.get(0).getId());
        Assert.assertEquals("San Francisco theater", theatersImpl.get(0).getName());

        Assert.assertEquals(2, theatersImpl.get(1).getId());
        Assert.assertEquals("San Jose theater", theatersImpl.get(1).getName());

        Assert.assertEquals(3, theatersImpl.get(2).getId());
        Assert.assertEquals("Daly City theater", theatersImpl.get(2).getName());
    }

    @Test
    public void getTheatersByName() {
        List<TheaterImpl> sfAndSj = movieBrowsingService.getTheatersByName("San");
        Assert.assertEquals(1, sfAndSj.get(0).getId());
        Assert.assertEquals("San Francisco theater", sfAndSj.get(0).getName());
        Assert.assertEquals(2, sfAndSj.get(1).getId());
        Assert.assertEquals("San Jose theater", sfAndSj.get(1).getName());

        TheaterImpl dc = movieBrowsingService.getTheatersByName("City").get(0);
        Assert.assertEquals(3, dc.getId());
        Assert.assertEquals("Daly City theater", dc.getName());
    }

    @Test
    public void getTheatersByMovieId() {
        List<TheaterImpl> sfAndSj = movieBrowsingService.getTheatersByMovieId(1);
        Assert.assertEquals(1, sfAndSj.get(0).getId());
        Assert.assertEquals("San Francisco theater", sfAndSj.get(0).getName());
        Assert.assertEquals(2, sfAndSj.get(1).getId());
        Assert.assertEquals("San Jose theater", sfAndSj.get(1).getName());

        TheaterImpl sj = movieBrowsingService.getTheatersByMovieId(2).get(0);
        Assert.assertEquals(2, sj.getId());
        Assert.assertEquals("San Jose theater", sj.getName());
    }
}

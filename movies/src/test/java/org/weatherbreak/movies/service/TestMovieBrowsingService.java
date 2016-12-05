package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Theater;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestMovieBrowsingService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MovieBrowsingService movieBrowsingService;

    @Test
    public void getMovies() {
        List<Movie> movies = movieBrowsingService.getMovies();
        Assert.assertEquals(1, movies.get(0).getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", movies.get(0).getName());

        Assert.assertEquals(2, movies.get(1).getId());
        Assert.assertEquals("Titanic", movies.get(1).getName());

        Assert.assertEquals(3, movies.get(2).getId());
        Assert.assertEquals("The Shining", movies.get(2).getName());
    }

    @Test
    public void getMoviesByName() {
        Movie et = movieBrowsingService.getMoviesByName("E.T.").get(0);
        Assert.assertEquals(1, et.getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", et.getName());

        List<Movie> titanicAndShining = movieBrowsingService.getMoviesByName("ni");
        Assert.assertEquals(2, titanicAndShining.get(0).getId());
        Assert.assertEquals("Titanic", titanicAndShining.get(0).getName());
        Assert.assertEquals(3, titanicAndShining.get(1).getId());
        Assert.assertEquals("The Shining", titanicAndShining.get(1).getName());

        List<Movie> notFound = movieBrowsingService.getMoviesByName("Kin-dza-dza!");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getMoviesByTheaterId() {
        Movie et = movieBrowsingService.getMoviesByTheaterId(1).get(0);
        Assert.assertEquals(1, et.getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", et.getName());

        List<Movie> etAndTitanic = movieBrowsingService.getMoviesByTheaterId(2);
        Assert.assertEquals(1, etAndTitanic.get(0).getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", etAndTitanic.get(0).getName());
        Assert.assertEquals(2, etAndTitanic.get(1).getId());
        Assert.assertEquals("Titanic", etAndTitanic.get(1).getName());

        List<Movie> notFound = movieBrowsingService.getMoviesByTheaterId(3);
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getTheaters() {
        List<Theater> theaters = movieBrowsingService.getTheaters();
        Assert.assertEquals(1, theaters.get(0).getId());
        Assert.assertEquals("San Francisco theater", theaters.get(0).getName());

        Assert.assertEquals(2, theaters.get(1).getId());
        Assert.assertEquals("San Jose theater", theaters.get(1).getName());

        Assert.assertEquals(3, theaters.get(2).getId());
        Assert.assertEquals("Daly City theater", theaters.get(2).getName());
    }

    @Test
    public void getTheatersByName() {
        List<Theater> sfAndSj = movieBrowsingService.getTheatersByName("San");
        Assert.assertEquals(1, sfAndSj.get(0).getId());
        Assert.assertEquals("San Francisco theater", sfAndSj.get(0).getName());
        Assert.assertEquals(2, sfAndSj.get(1).getId());
        Assert.assertEquals("San Jose theater", sfAndSj.get(1).getName());

        Theater dc = movieBrowsingService.getTheatersByName("City").get(0);
        Assert.assertEquals(3, dc.getId());
        Assert.assertEquals("Daly City theater", dc.getName());

        List<Theater> notFound = movieBrowsingService.getTheatersByName("Antarctic");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getTheatersByMovieId() {
        List<Theater> sfAndSj = movieBrowsingService.getTheatersByMovieId(1);
        Assert.assertEquals(1, sfAndSj.get(0).getId());
        Assert.assertEquals("San Francisco theater", sfAndSj.get(0).getName());
        Assert.assertEquals(2, sfAndSj.get(1).getId());
        Assert.assertEquals("San Jose theater", sfAndSj.get(1).getName());

        Theater sj = movieBrowsingService.getTheatersByMovieId(2).get(0);
        Assert.assertEquals(2, sj.getId());
        Assert.assertEquals("San Jose theater", sj.getName());

        List<Theater> notFound = movieBrowsingService.getTheatersByMovieId(3);
        Assert.assertEquals(0, notFound.size());
    }
    /*

    @Test
    public void getTheaters() {
        List<Theater> theaters = movieBrowsingService.getTheaters();
        Assert.assertEquals(1, theaters.get(0).getId());
        Assert.assertEquals("San Francisco theater", theaters.get(0).getName());

        Assert.assertEquals(2, theaters.get(1).getId());
        Assert.assertEquals("San Jose theater", theaters.get(1).getName());

        Assert.assertEquals(3, theaters.get(2).getId());
        Assert.assertEquals("Daly City theater", theaters.get(2).getName());
    }

    @Test
    public void getTheatersByName() {
        List<Theater> sfAndSj = movieBrowsingService.getTheatersByName("San");
        Assert.assertEquals(1, sfAndSj.get(0).getId());
        Assert.assertEquals("San Francisco theater", sfAndSj.get(0).getName());
        Assert.assertEquals(2, sfAndSj.get(1).getId());
        Assert.assertEquals("San Jose theater", sfAndSj.get(1).getName());

        Theater dc = movieBrowsingService.getTheatersByName("City").get(0);
        Assert.assertEquals(3, dc.getId());
        Assert.assertEquals("Daly City theater", dc.getName());

        List<Theater> notFound = movieBrowsingService.getTheatersByName("Antarctic");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getTheatersByMovieId() {
        List<Theater> sfAndSj = movieBrowsingService.getTheatersByMovieId(1);
        Assert.assertEquals(1, sfAndSj.get(0).getId());
        Assert.assertEquals("San Francisco theater", sfAndSj.get(0).getName());
        Assert.assertEquals(2, sfAndSj.get(1).getId());
        Assert.assertEquals("San Jose theater", sfAndSj.get(1).getName());

        Theater sj = movieBrowsingService.getTheatersByMovieId(2).get(0);
        Assert.assertEquals(2, sj.getId());
        Assert.assertEquals("San Jose theater", sj.getName());

        List<Theater> notFound = movieBrowsingService.getTheatersByMovieId(3);
        Assert.assertEquals(0, notFound.size());
    }*/
}

package org.weatherbreak.movies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.impl.MovieImpl;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestMovieRepository extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private MovieRepository movieRepository;

    private static long etId;
    private static long titanicId;
    private static long shiningId;

    @Before
    public void beforeTestClass() {
        MovieImpl et = new MovieImpl();
        et.setName("E.T. the Extra-Terrestrial");
        etId = movieRepository.addMovie(et);

        MovieImpl titanic = new MovieImpl();
        titanic.setName("Titanic");
        titanicId = movieRepository.addMovie(titanic);

        MovieImpl shining = new MovieImpl();
        shining.setName("The Shining");
        shiningId = movieRepository.addMovie(shining);
    }

    @Test
    public void addMovie() {
        Assert.assertNotEquals(0, etId);
        Assert.assertNotEquals(0, titanicId);
        Assert.assertNotEquals(0, shiningId);
    }

    @Test
    public void getMovie() {
        Movie found1 = movieRepository.getMovie(etId);
        Assert.assertEquals(etId, found1.getId());
        Assert.assertEquals("E.T. the Extra-Terrestrial", found1.getName());

        Movie found2 = movieRepository.getMovie(titanicId);
        Assert.assertEquals(titanicId, found2.getId());
        Assert.assertEquals("Titanic", found2.getName());

        Movie found3 = movieRepository.getMovie(shiningId);
        Assert.assertEquals(shiningId, found3.getId());
        Assert.assertEquals("The Shining", found3.getName());
    }

    @Test
    public void getMovies() {
        List<Movie> founds = movieRepository.getMovies();

        for (Movie found : founds) {
            if (found.getId() == etId)
                Assert.assertEquals("E.T. the Extra-Terrestrial", found.getName());

            if (found.getId() == titanicId)
                Assert.assertEquals("Titanic", found.getName());

            if (found.getId() == shiningId)
                Assert.assertEquals("The Shining", found.getName());
        }
    }

    @Test
    public void getMoviesByName() {
        List<Movie> founds1 = movieRepository.getMoviesByName("E.T.");

        for (Movie found : founds1) {
            if (found.getId() == etId)
                Assert.assertEquals("E.T. the Extra-Terrestrial", found.getName());
        }

        List<Movie> founds2 = movieRepository.getMoviesByName("ni");

        for (Movie found : founds2) {
            if (found.getId() == titanicId)
                Assert.assertEquals("Titanic", found.getName());

            if (found.getId() == shiningId)
                Assert.assertEquals("The Shining", found.getName());
        }
    }
}

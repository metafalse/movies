package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.*;
import org.weatherbreak.movies.entity.impl.*;

import java.util.List;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestReviewService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    private static User newUser1;
    private static User newUser2;
    private static User newUser3;
    private static Movie newMovie1;
    private static Movie newMovie2;
    private static Movie newMovie3;
    private static Review added1;
    private static Review added2;
    private static Review added3;

    @Before
    public void beforeTestClass() {
        newUser1 = createUser();
        newUser2 = createUser();
        newMovie1 = createMovie();
        newMovie2 = createMovie();

        ReviewImpl newReview1 = new ReviewImpl();
        newReview1.setUser(newUser1);
        newReview1.setMovie(newMovie1);
        newReview1.setTitle("testTitle1");
        newReview1.setDescription("testDescription1");

        ReviewImpl newReview2 = new ReviewImpl();
        newReview2.setUser(newUser1);
        newReview2.setMovie(newMovie2);
        newReview2.setTitle("testTitle2");
        newReview2.setDescription("testDescription2");

        ReviewImpl newReview3 = new ReviewImpl();
        newReview3.setUser(newUser2);
        newReview3.setMovie(newMovie2);
        newReview3.setTitle("testTitle3");
        newReview3.setDescription("testDescription3");

        added1 = reviewService.addReview(newReview1);
        added2 = reviewService.addReview(newReview2);
        added3 = reviewService.addReview(newReview3);

        newUser3 = createUser();
        newMovie3 = createMovie();
    }

    @Test
    public void addReview() {
        Assert.assertNotEquals(0, added1.getId());
        Assert.assertNotEquals(0, added2.getId());
        Assert.assertNotEquals(0, added3.getId());
    }

    @Test
    public void getReview() {
        Review found1 = reviewService.getReview(added1.getId());

        Assert.assertEquals(added1.getId(),            found1.getId());
        Assert.assertEquals(added1.getUser().getId(),  found1.getUser().getId());
        Assert.assertEquals(added1.getMovie().getId(), found1.getMovie().getId());
        Assert.assertEquals("testTitle1",              found1.getTitle());
        Assert.assertEquals("testDescription1",        found1.getDescription());

        Review found2 = reviewService.getReview(added2.getId());

        Assert.assertEquals(added2.getId(),            found2.getId());
        Assert.assertEquals(added2.getUser().getId(),  found2.getUser().getId());
        Assert.assertEquals(added2.getMovie().getId(), found2.getMovie().getId());
        Assert.assertEquals("testTitle2",              found2.getTitle());
        Assert.assertEquals("testDescription2",        found2.getDescription());

        Review found3 = reviewService.getReview(added3.getId());

        Assert.assertEquals(added3.getId(),            found3.getId());
        Assert.assertEquals(added3.getUser().getId(),  found3.getUser().getId());
        Assert.assertEquals(added3.getMovie().getId(), found3.getMovie().getId());
        Assert.assertEquals("testTitle3",              found3.getTitle());
        Assert.assertEquals("testDescription3",        found3.getDescription());
    }

    @Test
    public void getReviews() {
        List<Review> founds = reviewService.getReviews();

        for (Review found : founds) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added1.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle1",              found.getTitle());
                Assert.assertEquals("testDescription1",        found.getDescription());
            }

            if (found.getId() == added2.getId()) {
                Assert.assertEquals(added2.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added2.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle2",              found.getTitle());
                Assert.assertEquals("testDescription2",        found.getDescription());
            }

            if (found.getId() == added3.getId()) {
                Assert.assertEquals(added3.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added3.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle3",              found.getTitle());
                Assert.assertEquals("testDescription3",        found.getDescription());
            }
        }
    }

    @Test
    public void getReviewsByUserId() {
        List<Review> founds1 = reviewService.getReviewsByUserId(newUser1.getId());

        for (Review found : founds1) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added1.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle1",              found.getTitle());
                Assert.assertEquals("testDescription1",        found.getDescription());
            }

            if (found.getId() == added2.getId()) {
                Assert.assertEquals(added2.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added2.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle2",              found.getTitle());
                Assert.assertEquals("testDescription2",        found.getDescription());
            }
        }

        List<Review> founds2 = reviewService.getReviewsByUserId(newUser2.getId());

        for (Review found : founds2) {
            if (found.getId() == added3.getId()) {
                Assert.assertEquals(added3.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added3.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle3",              found.getTitle());
                Assert.assertEquals("testDescription3",        found.getDescription());
            }
        }

        List<Review> notFound = reviewService.getReviewsByUserId(newUser3.getId());
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getReviewsByMovieId() {
        List<Review> founds1 = reviewService.getReviewsByMovieId(newMovie1.getId());

        for (Review found : founds1) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added1.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle1",              found.getTitle());
                Assert.assertEquals("testDescription1",        found.getDescription());
            }
        }

        List<Review> founds2 = reviewService.getReviewsByMovieId(newMovie2.getId());

        for (Review found : founds2) {
            if (found.getId() == added2.getId()) {
                Assert.assertEquals(added2.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added2.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle2",              found.getTitle());
                Assert.assertEquals("testDescription2",        found.getDescription());
            }

            if (found.getId() == added3.getId()) {
                Assert.assertEquals(added3.getUser().getId(),  found.getUser().getId());
                Assert.assertEquals(added3.getMovie().getId(), found.getMovie().getId());
                Assert.assertEquals("testTitle3",              found.getTitle());
                Assert.assertEquals("testDescription3",        found.getDescription());
            }
        }

        List<Review> notFound = reviewService.getReviewsByMovieId(newMovie3.getId());
        Assert.assertEquals(0, notFound.size());
    }

    private User createUser() {
        UserImpl newUser = new UserImpl();
        newUser.setName("user" + new Random().nextInt(99999));
        newUser.setPassword("1234");

        return userService.addUser(newUser);
    }

    private Movie createMovie() {
        MovieImpl newMovie = new MovieImpl();
        newMovie.setName("movie" + new Random().nextInt(99999));

        return movieService.addMovie(newMovie);
    }
}

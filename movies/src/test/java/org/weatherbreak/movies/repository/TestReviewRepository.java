package org.weatherbreak.movies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Review;
import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.entity.impl.ReviewImpl;
import org.weatherbreak.movies.entity.impl.UserImpl;

import java.util.List;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestReviewRepository extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private static User newUser1;
    private static User newUser2;
    private static User newUser3;
    private static Movie newMovie1;
    private static Movie newMovie2;
    private static Movie newMovie3;
    private static long review1Id;
    private static long review2Id;
    private static long review3Id;

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

        review1Id = reviewRepository.addReview(newReview1);
        review2Id = reviewRepository.addReview(newReview2);
        review3Id = reviewRepository.addReview(newReview3);

        newUser3 = createUser();
        newMovie3 = createMovie();
    }

    @Test
    public void addReview() {
        Assert.assertNotEquals(0, review1Id);
        Assert.assertNotEquals(0, review2Id);
        Assert.assertNotEquals(0, review3Id);
    }

    @Test
    public void getReview() {
        Review found1 = reviewRepository.getReview(review1Id);

        Assert.assertEquals(review1Id, found1.getId());
        Assert.assertEquals(newUser1.getId(),   found1.getUser().getId());
        Assert.assertEquals(newMovie1.getId(),  found1.getMovie().getId());
        Assert.assertEquals("testTitle1",       found1.getTitle());
        Assert.assertEquals("testDescription1", found1.getDescription());

        Review found2 = reviewRepository.getReview(review2Id);

        Assert.assertEquals(review2Id,          found2.getId());
        Assert.assertEquals(newUser1.getId(),   found2.getUser().getId());
        Assert.assertEquals(newMovie2.getId(),  found2.getMovie().getId());
        Assert.assertEquals("testTitle2",       found2.getTitle());
        Assert.assertEquals("testDescription2", found2.getDescription());

        Review found3 = reviewRepository.getReview(review3Id);

        Assert.assertEquals(review3Id,          found3.getId());
        Assert.assertEquals(newUser2.getId(),   found3.getUser().getId());
        Assert.assertEquals(newMovie2.getId(),  found3.getMovie().getId());
        Assert.assertEquals("testTitle3",       found3.getTitle());
        Assert.assertEquals("testDescription3", found3.getDescription());
    }

    @Test
    public void getReviews() {
        List<Review> founds = reviewRepository.getReviews();

        for (Review found : founds) {
            if (found.getId() == review1Id) {
                Assert.assertEquals(review1Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie1.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle1",       found.getTitle());
                Assert.assertEquals("testDescription1", found.getDescription());
            }

            if (found.getId() == review2Id) {
                Assert.assertEquals(review2Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie2.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle2",       found.getTitle());
                Assert.assertEquals("testDescription2", found.getDescription());
            }

            if (found.getId() == review3Id) {
                Assert.assertEquals(review3Id,          found.getId());
                Assert.assertEquals(newUser2.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie2.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle3",       found.getTitle());
                Assert.assertEquals("testDescription3", found.getDescription());
            }
        }
    }

    @Test
    public void getReviewsByUserId() {
        List<Review> founds1 = reviewRepository.getReviewsByUserId(newUser1.getId());

        for (Review found : founds1) {
            if (found.getId() == review1Id) {
                Assert.assertEquals(review1Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie1.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle1",       found.getTitle());
                Assert.assertEquals("testDescription1", found.getDescription());
            }

            if (found.getId() == review2Id) {
                Assert.assertEquals(review2Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie2.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle2",       found.getTitle());
                Assert.assertEquals("testDescription2", found.getDescription());
            }
        }

        List<Review> founds2 = reviewRepository.getReviewsByUserId(newUser2.getId());

        for (Review found : founds2) {
            if (found.getId() == review3Id) {
                Assert.assertEquals(review3Id,          found.getId());
                Assert.assertEquals(newUser2.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie2.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle3",       found.getTitle());
                Assert.assertEquals("testDescription3", found.getDescription());
            }
        }

        List<Review> notFound = reviewRepository.getReviewsByUserId(newUser3.getId());
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void getReviewsByMovieId() {
        List<Review> founds1 = reviewRepository.getReviewsByMovieId(newMovie1.getId());

        for (Review found : founds1) {
            if (found.getId() == review1Id) {
                Assert.assertEquals(review1Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie1.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle1",       found.getTitle());
                Assert.assertEquals("testDescription1", found.getDescription());
            }
        }

        List<Review> founds2 = reviewRepository.getReviewsByMovieId(newMovie2.getId());

        for (Review found : founds2) {
            if (found.getId() == review2Id) {
                Assert.assertEquals(review2Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie2.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle2",       found.getTitle());
                Assert.assertEquals("testDescription2", found.getDescription());
            }

            if (found.getId() == review3Id) {
                Assert.assertEquals(review3Id,          found.getId());
                Assert.assertEquals(newUser2.getId(),   found.getUser().getId());
                Assert.assertEquals(newMovie2.getId(),  found.getMovie().getId());
                Assert.assertEquals("testTitle3",       found.getTitle());
                Assert.assertEquals("testDescription3", found.getDescription());
            }
        }

        List<Review> notFound = reviewRepository.getReviewsByUserId(newMovie3.getId());
        Assert.assertEquals(0, notFound.size());
    }

    private User createUser() {
        UserImpl newUser = new UserImpl();
        newUser.setName("user" + new Random().nextInt(99999));
        newUser.setPassword("1234");

        long userId = userRepository.addUser(newUser);
        return userRepository.getUser(userId);
    }

    private Movie createMovie() {
        MovieImpl newMovie = new MovieImpl();
        newMovie.setName("movie" + new Random().nextInt(99999));

        long movieId = movieRepository.addMovie(newMovie);
        return movieRepository.getMovie(movieId);
    }
}

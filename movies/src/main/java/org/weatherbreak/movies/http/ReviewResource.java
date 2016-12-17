package org.weatherbreak.movies.http;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weatherbreak.movies.entity.Review;
import org.weatherbreak.movies.entity.impl.ReviewImpl;
import org.weatherbreak.movies.http.entity.HttpReview;
import org.weatherbreak.movies.service.MovieService;
import org.weatherbreak.movies.service.ReviewService;
import org.weatherbreak.movies.service.UserService;
import org.weatherbreak.movies.service.exception.MoviesException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/reviews")
@Component
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ReviewResource {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/")
    public Response addReview(HttpReview newReview) {
        Review reviewToCreate = convert(newReview);
        Review added = reviewService.addReview(reviewToCreate);
        return Response.status(Status.CREATED).header("Location", "/reviews/" + added.getId()).entity(new HttpReview(added)).build();
    }

    @GET
    @Path("/")
    @Wrapped(element="reviews")
    public List<HttpReview> getReviews(@QueryParam("userId") long userId, @QueryParam("movieId") long movieId) throws MoviesException {
        List<HttpReview> returnList;
        List<Review> founds;

        if (userId != 0) {
            logger.info("getting reviews by userId : " + userId);
            founds = reviewService.getReviewsByUserId(userId);
        }
        else if (movieId != 0) {
            logger.info("getting reviews by movieId : " + movieId);
            founds = reviewService.getReviewsByMovieId(movieId);
        }
        else {
            logger.info("getting all reviews");
            founds = reviewService.getReviews();
        }

        returnList = new ArrayList<>(founds.size());
        for (Review found : founds) {
            returnList.add(new HttpReview(found));
        }

        return returnList;
    }

    @GET
    @Path("/{reviewId}")
    public HttpReview getReview(@PathParam("reviewId") long reviewId) {
        logger.info("getting review by id : " + reviewId);
        Review review = reviewService.getReview(reviewId);
        return new HttpReview(review);
    }

    private Review convert(HttpReview httpReview) {
        ReviewImpl review = new ReviewImpl();
        review.setUser(userService.getUser(httpReview.userId));
        review.setMovie(movieService.getMovie(httpReview.movieId));
        review.setTitle(httpReview.title);
        review.setDescription(httpReview.description);
        return review;
    }
}

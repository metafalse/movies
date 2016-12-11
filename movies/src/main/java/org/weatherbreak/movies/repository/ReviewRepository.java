package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.Review;

import java.util.List;

public interface ReviewRepository {

    long addReview(Review review);

    Review getReview(long reviewId);

    List<Review> getReviews();

    List<Review> getReviewsByUserId(long userId);

    List<Review> getReviewsByMovieId(long movieId);
}

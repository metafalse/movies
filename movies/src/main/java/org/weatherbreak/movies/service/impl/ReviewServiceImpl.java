package org.weatherbreak.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.weatherbreak.movies.entity.Review;
import org.weatherbreak.movies.repository.ReviewRepository;
import org.weatherbreak.movies.service.ReviewService;
import org.weatherbreak.movies.service.UserService;
import org.weatherbreak.movies.service.exception.InvalidFieldException;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final int MAX_TITLE_LENGTH = 45;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Review addReview(Review review) {
        if (review.getUser() == null) {
            throw new InvalidFieldException("user is required");
        }

        if (review.getMovie() == null) {
            throw new InvalidFieldException("movie is required");
        }

        if (StringUtils.isEmpty(review.getTitle()) || review.getTitle().length() > MAX_TITLE_LENGTH) {
            throw new InvalidFieldException("title is invalid");
        }

        if (StringUtils.isEmpty(review.getDescription()) || review.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidFieldException("description is invalid");
        }

        long reviewId = reviewRepository.addReview(review);
        return getReview(reviewId);
    }

    @Override
    @Transactional
    public Review getReview(long reviewId) {
        return reviewRepository.getReview(reviewId);
    }

    @Override
    @Transactional
    public List<Review> getReviews() {
        return reviewRepository.getReviews();
    }

    @Override
    @Transactional
    public List<Review> getReviewsByUserId(long userId) {
        return reviewRepository.getReviewsByUserId(userId);
    }

    @Override
    @Transactional
    public List<Review> getReviewsByMovieId(long movieId) {
        return reviewRepository.getReviewsByMovieId(movieId);
    }
}

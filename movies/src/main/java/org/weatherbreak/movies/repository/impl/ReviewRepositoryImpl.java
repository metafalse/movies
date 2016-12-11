package org.weatherbreak.movies.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.weatherbreak.movies.entity.Review;
import org.weatherbreak.movies.entity.impl.ReviewImpl;
import org.weatherbreak.movies.repository.ReviewRepository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long addReview(Review review) {
        return (Long) this.sessionFactory.getCurrentSession().save(review);
    }

    @Override
    public Review getReview(long reviewId) {
        return (Review) this.sessionFactory.getCurrentSession().get(ReviewImpl.class, reviewId);
    }

    @Override
    public List<Review> getReviews() {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(ReviewImpl.class);
        List<Review> reviews = crit.list();
        return reviews;
    }

    @Override
    public List<Review> getReviewsByUserId(long userId) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(ReviewImpl.class)
            .add(Restrictions.eq("user.id", userId));
        List<Review> reviews = crit.list();
        return reviews;
    }

    @Override
    public List<Review> getReviewsByMovieId(long movieId) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(ReviewImpl.class)
                .add(Restrictions.eq("movie.id", movieId));
        List<Review> reviews = crit.list();
        return reviews;
    }
}

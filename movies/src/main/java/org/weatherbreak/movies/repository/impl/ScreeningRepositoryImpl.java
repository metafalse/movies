package org.weatherbreak.movies.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.impl.ScreeningImpl;
import org.weatherbreak.movies.repository.ScreeningRepository;

import java.util.List;

@Repository
public class ScreeningRepositoryImpl implements ScreeningRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Screening> getScreeningsByTheaterId(long theaterId) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(ScreeningImpl.class)
            .add(Restrictions.eq("theater.id", theaterId));
        List<Screening> screenings = crit.list();
        return screenings;
    }

    @Override
    public List<Screening> getScreeningsByMovieId(long movieId) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(ScreeningImpl.class)
            .add(Restrictions.eq("movie.id", movieId));
        List<Screening> screenings = crit.list();
        return screenings;
    }
}

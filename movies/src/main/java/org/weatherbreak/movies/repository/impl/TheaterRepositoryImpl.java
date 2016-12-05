package org.weatherbreak.movies.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.entity.impl.TheaterImpl;
import org.weatherbreak.movies.repository.TheaterRepository;

import java.util.List;

@Repository
public class TheaterRepositoryImpl implements TheaterRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Theater> getTheaters() {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(TheaterImpl.class);
        List<Theater> theaters = crit.list();
        return theaters;
    }

    @Override
    public List<Theater> getTheatersByName(String theaterName) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(TheaterImpl.class)
            .add(Restrictions.like("name", "%" + theaterName + "%"));
        List<Theater> theaters = crit.list();
        return theaters;
    }
}

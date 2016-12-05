package org.weatherbreak.movies.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.repository.MovieRepository;

import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Movie> getMovies() {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(MovieImpl.class);
        List<Movie> movies = crit.list();
        return movies;
    }

    @Override
    public List<Movie> getMoviesByName(String movieName) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(MovieImpl.class)
            .add(Restrictions.like("name", "%" + movieName + "%"));
        List<Movie> movies = crit.list();
        return movies;
    }
}

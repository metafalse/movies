package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.Theater;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="screenings")
public class ScreeningImpl implements Screening {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity=MovieImpl.class)
    @JoinColumn(name="movies_id")
    private Movie movie;

    @ManyToOne(targetEntity=TheaterImpl.class)
    @JoinColumn(name="theaters_id")
    private Theater theater;

    @Column(name="showtime")
    private Date showtime;

    public ScreeningImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Date getShowTime() {
        return showtime;
    }

    @Override
    public Movie getMovie() {
        return movie;
    }

    @Override
    public Theater getTheater() {
        return theater;
    }
}

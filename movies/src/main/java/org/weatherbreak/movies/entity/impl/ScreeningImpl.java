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
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    @Override
    public Date getShowtime() {
        return showtime;
    }

    public void setShowtime(Date showtime) {
        this.showtime = showtime;
    }

    @Override
    public String toString() {
        return "ScreeningImpl [id = " + id + ", movie = " + movie
            + ", theater = " + theater + ", showtime = " + showtime + "]";
    }
}

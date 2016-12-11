package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.Review;
import org.weatherbreak.movies.entity.User;

import javax.persistence.*;

@Entity
@Table(name="reviews")
public class ReviewImpl implements Review {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity=UserImpl.class)
    @JoinColumn(name="users_id")
    private User user;

    @ManyToOne(targetEntity=MovieImpl.class)
    @JoinColumn(name="movies_id")
    private Movie movie;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    public ReviewImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReviewImpl [id = " + id + ", user = " + user + ", movie = " + movie
            + ", title = " + title + ", description = " + description + "]";
    }
}

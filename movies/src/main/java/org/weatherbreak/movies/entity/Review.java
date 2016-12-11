package org.weatherbreak.movies.entity;

public interface Review {

    long getId();

    User getUser();

    Movie getMovie();

    String getTitle();

    String getDescription();
}

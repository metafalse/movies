package org.weatherbreak.movies.entity;

public interface Purchase {

    long getId();

    User getUser();

    Screening getScreening();

    int getNumber();
}

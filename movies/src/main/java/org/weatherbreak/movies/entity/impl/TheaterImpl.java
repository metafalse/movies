package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Theater;

import javax.persistence.*;

@Entity
@Table(name="theaters")
public class TheaterImpl implements Theater {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    public TheaterImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TheaterImpl [id = " + id + ", name = " + name + "]";
    }
}

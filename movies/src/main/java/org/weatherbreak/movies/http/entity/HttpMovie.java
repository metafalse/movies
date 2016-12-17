package org.weatherbreak.movies.http.entity;

import org.weatherbreak.movies.entity.Movie;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movie")
public class HttpMovie {
    @XmlElement
    public long id;

    @XmlElement
    public String name;

    //required by framework
    protected HttpMovie() {}

    public HttpMovie(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
    }
}

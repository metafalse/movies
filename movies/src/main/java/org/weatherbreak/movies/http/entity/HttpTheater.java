package org.weatherbreak.movies.http.entity;

import org.weatherbreak.movies.entity.Theater;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "theater")
public class HttpTheater {
    @XmlElement
    public long id;

    @XmlElement
    public String name;

    //required by framework
    protected HttpTheater() {}

    public HttpTheater(Theater theater) {
        this.id = theater.getId();
        this.name = theater.getName();
    }
}

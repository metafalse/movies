package org.weatherbreak.movies.http.entity;

import org.weatherbreak.movies.entity.Screening;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "screening")
public class HttpScreening {
    @XmlElement
    public long id;

    @XmlElement
    public long movieId;

    @XmlElement
    public long theaterId;

    @XmlElement
    public Date showtime;

    //required by framework
    protected HttpScreening() {}

    public HttpScreening(Screening screening) {
        this.id = screening.getId();
        this.movieId = screening.getMovie().getId();
        this.theaterId = screening.getTheater().getId();
        this.showtime = screening.getShowtime();
    }
}

package org.weatherbreak.movies.http.entity;

import org.weatherbreak.movies.entity.Review;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "review")
public class HttpReview {
    @XmlElement
    public long id;

    @XmlElement
    public long userId;

    @XmlElement
    public long movieId;

    @XmlElement
    public String title;

    @XmlElement
    public String description;

    //required by framework
    protected HttpReview() {}

    public HttpReview(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.movieId = review.getMovie().getId();
        this.title = review.getTitle();
        this.description = review.getDescription();
    }
}

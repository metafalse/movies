package org.weatherbreak.movies.http;

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

    @Override
    public String toString() {
        return "HttpReview [id = " + id + ", userId = " + userId + ", movieId = " + movieId +
                ", title = " + title + ", description = " + description + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + (int) (movieId ^ (movieId >>> 32));
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HttpReview other = (HttpReview) obj;
        if (id != other.id)
            return false;
        if (userId != other.userId)
            return false;
        if (movieId != other.movieId)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }
}

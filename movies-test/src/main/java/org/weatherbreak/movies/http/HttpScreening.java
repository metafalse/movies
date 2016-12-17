package org.weatherbreak.movies.http;

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

    @Override
    public String toString() {
        return "HttpScreening [id = " + id + ", movieId = " + movieId +
                ", theater = " + theaterId +  ", showtime = " + showtime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (movieId ^ (movieId >>> 32));
        result = prime * result + (int) (theaterId ^ (theaterId >>> 32));
        result = prime * result + ((showtime == null) ? 0 : showtime.hashCode());
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
        HttpScreening other = (HttpScreening) obj;
        if (id != other.id)
            return false;
        if (movieId != other.movieId)
            return false;
        if (theaterId != other.theaterId)
            return false;
        if (showtime == null) {
            if (other.showtime != null)
                return false;
        } else if (showtime.compareTo(other.showtime) != 0)
            return false;
        return true;
    }
}

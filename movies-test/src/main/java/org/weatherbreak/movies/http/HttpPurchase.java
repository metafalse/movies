package org.weatherbreak.movies.http;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "purchase")
public class HttpPurchase {
    @XmlElement
    public long id;

    @XmlElement
    public long userId;

    @XmlElement
    public long screeningId;

    @XmlElement
    public int number;

    @Override
    public String toString() {
        return "HttpPurchase [id = " + id + ", userId = " + userId +
                ", screeningId = " + screeningId + ", number = " + number + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + (int) (screeningId ^ (screeningId >>> 32));
        result = prime * result + (number ^ (number >>> 32));
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
        HttpPurchase other = (HttpPurchase) obj;
        if (id != other.id)
            return false;
        if (userId != other.userId)
            return false;
        if (screeningId != other.screeningId)
            return false;
        if (number != other.number)
            return false;
        return true;
    }
}

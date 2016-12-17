package org.weatherbreak.movies.http.entity;

import org.weatherbreak.movies.entity.Purchase;

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

    //required by framework
    protected HttpPurchase() {}

    public HttpPurchase(Purchase purchase) {
        this.id = purchase.getId();
        this.userId = purchase.getUser().getId();
        this.screeningId = purchase.getScreening().getId();
        this.number = purchase.getNumber();
    }
}

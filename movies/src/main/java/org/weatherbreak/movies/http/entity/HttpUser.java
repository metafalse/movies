package org.weatherbreak.movies.http.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.weatherbreak.movies.entity.User;

@XmlRootElement(name = "user")
public class HttpUser {
    @XmlElement
    public long id;

    @XmlElement
    public String name;

    @XmlElement
    public String password;

    //required by framework
    protected HttpUser() {}

    public HttpUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        //not setting password
    }
}

package org.weatherbreak.movies.entity.impl;

import org.weatherbreak.movies.entity.Purchase;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.User;

import javax.persistence.*;

@Entity
@Table(name="purchases")
public class PurchaseImpl implements Purchase {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity=UserImpl.class)
    @JoinColumn(name="users_id")
    private User user;

    @ManyToOne(targetEntity=ScreeningImpl.class)
    @JoinColumn(name="screenings_id")
    private Screening screening;

    @Column(name="number")
    private int number;

    public PurchaseImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PurchaseImpl [id = " + id + ", user = " + user
            + ", screening = " + screening + ", number = " + number + "]";
    }
}

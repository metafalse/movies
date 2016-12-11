package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.*;
import org.weatherbreak.movies.entity.impl.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestPurchaseService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterService theaterService;

    private static User newUser1;
    private static User newUser2;
    private static User newUser3;
    private static Purchase added1;
    private static Purchase added2;
    private static Purchase added3;

    @Before
    public void beforeTestClass() {
        Screening newScreening = createScreening();

        newUser1 = createUser();
        PurchaseImpl newPurchase1 = new PurchaseImpl();
        newPurchase1.setUser(newUser1);
        newPurchase1.setScreening(newScreening);
        newPurchase1.setNumber(1);
        added1 = purchaseService.addPurchase(newPurchase1);

        PurchaseImpl newPurchase2 = new PurchaseImpl();
        newPurchase2.setUser(newUser1);
        newPurchase2.setScreening(newScreening);
        newPurchase2.setNumber(2);
        added2 = purchaseService.addPurchase(newPurchase2);

        newUser2 = createUser();
        PurchaseImpl newPurchase3 = new PurchaseImpl();
        newPurchase3.setUser(newUser2);
        newPurchase3.setScreening(newScreening);
        newPurchase3.setNumber(3);
        added3 = purchaseService.addPurchase(newPurchase3);

        newUser3 = createUser();
    }

    @Test
    public void addPurchase() {
        Assert.assertNotEquals(0, added1.getId());
        Assert.assertNotEquals(0, added2.getId());
        Assert.assertNotEquals(0, added3.getId());
    }

    @Test
    public void getPurchase() {
        Purchase found1 = purchaseService.getPurchase(added1.getId());

        Assert.assertEquals(added1.getId(),                found1.getId());
        Assert.assertEquals(added1.getUser().getId(),      found1.getUser().getId());
        Assert.assertEquals(added1.getScreening().getId(), found1.getScreening().getId());
        Assert.assertEquals(added1.getNumber(),            found1.getNumber());

        Purchase found2 = purchaseService.getPurchase(added2.getId());

        Assert.assertEquals(added2.getId(),                found2.getId());
        Assert.assertEquals(added2.getUser().getId(),      found2.getUser().getId());
        Assert.assertEquals(added2.getScreening().getId(), found2.getScreening().getId());
        Assert.assertEquals(added2.getNumber(),            found2.getNumber());

        Purchase found3 = purchaseService.getPurchase(added3.getId());

        Assert.assertEquals(added3.getId(),                found3.getId());
        Assert.assertEquals(added3.getUser().getId(),      found3.getUser().getId());
        Assert.assertEquals(added3.getScreening().getId(), found3.getScreening().getId());
        Assert.assertEquals(added3.getNumber(),            found3.getNumber());
    }

    @Test
    public void getPurchases() {
        List<Purchase> founds = purchaseService.getPurchases();

        for (Purchase found : founds) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getId(),                found.getId());
                Assert.assertEquals(added1.getUser().getId(),      found.getUser().getId());
                Assert.assertEquals(added1.getScreening().getId(), found.getScreening().getId());
                Assert.assertEquals(added1.getNumber(),            found.getNumber());
            }

            if (found.getId() == added2.getId()) {
                Assert.assertEquals(added2.getId(),                found.getId());
                Assert.assertEquals(added2.getUser().getId(),      found.getUser().getId());
                Assert.assertEquals(added2.getScreening().getId(), found.getScreening().getId());
                Assert.assertEquals(added2.getNumber(),            found.getNumber());
            }

            if (found.getId() == added3.getId()) {
                Assert.assertEquals(added3.getId(),                found.getId());
                Assert.assertEquals(added3.getUser().getId(),      found.getUser().getId());
                Assert.assertEquals(added3.getScreening().getId(), found.getScreening().getId());
                Assert.assertEquals(added3.getNumber(),            found.getNumber());
            }
        }
    }

    @Test
    public void getPurchasesByUserId() {
        List<Purchase> founds1 = purchaseService.getPurchasesByUserId(newUser1.getId());

        for (Purchase found : founds1) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getId(),                found.getId());
                Assert.assertEquals(added1.getUser().getId(),      found.getUser().getId());
                Assert.assertEquals(added1.getScreening().getId(), found.getScreening().getId());
                Assert.assertEquals(added1.getNumber(),            found.getNumber());
            }

            if (found.getId() == added2.getId()) {
                Assert.assertEquals(added2.getId(),                found.getId());
                Assert.assertEquals(added2.getUser().getId(),      found.getUser().getId());
                Assert.assertEquals(added2.getScreening().getId(), found.getScreening().getId());
                Assert.assertEquals(added2.getNumber(),            found.getNumber());
            }
        }

        List<Purchase> founds2 = purchaseService.getPurchasesByUserId(newUser2.getId());

        for (Purchase found : founds2) {
            if (found.getId() == added3.getId()) {
                Assert.assertEquals(added3.getId(),                found.getId());
                Assert.assertEquals(added3.getUser().getId(),      found.getUser().getId());
                Assert.assertEquals(added3.getScreening().getId(), found.getScreening().getId());
                Assert.assertEquals(added3.getNumber(),            found.getNumber());
            }
        }

        List<Purchase> notFound = purchaseService.getPurchasesByUserId(newUser3.getId());
        Assert.assertEquals(0, notFound.size());
    }

    private User createUser() {
        UserImpl newUser = new UserImpl();
        newUser.setName("user" + new Random().nextInt(99999));
        newUser.setPassword("1234");

        return userService.addUser(newUser);
    }

    private Screening createScreening() {
        ScreeningImpl newScreening = new ScreeningImpl();

        newScreening.setMovie(createMovie());
        newScreening.setTheater(createTheater());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            newScreening.setShowtime(sdf.parse("2222-22-22 22:22:22"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return screeningService.addScreening(newScreening);
    }

    private Movie createMovie() {
        MovieImpl newMovie = new MovieImpl();
        newMovie.setName("movie" + new Random().nextInt(99999));

        return movieService.addMovie(newMovie);
    }

    private Theater createTheater() {
        TheaterImpl newTheater = new TheaterImpl();
        newTheater.setName("theater" + new Random().nextInt(99999));

        return theaterService.addTheater(newTheater);
    }
}

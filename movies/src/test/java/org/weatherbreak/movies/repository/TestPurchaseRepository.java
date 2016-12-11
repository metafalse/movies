package org.weatherbreak.movies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.*;
import org.weatherbreak.movies.entity.impl.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestPurchaseRepository extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    private static User newUser1;
    private static User newUser2;
    private static User newUser3;
    private static Screening newScreening;
    private static long purchase1Id;
    private static long purchase2Id;
    private static long purchase3Id;

    @Before
    public void beforeTestClass() {
        newScreening = createScreening();

        newUser1 = createUser();
        PurchaseImpl newPurchase1 = new PurchaseImpl();
        newPurchase1.setUser(newUser1);
        newPurchase1.setScreening(newScreening);
        newPurchase1.setNumber(1);
        purchase1Id = purchaseRepository.addPurchase(newPurchase1);

        PurchaseImpl newPurchase2 = new PurchaseImpl();
        newPurchase2.setUser(newUser1);
        newPurchase2.setScreening(newScreening);
        newPurchase2.setNumber(2);
        purchase2Id = purchaseRepository.addPurchase(newPurchase2);

        newUser2 = createUser();
        PurchaseImpl newPurchase3 = new PurchaseImpl();
        newPurchase3.setUser(newUser2);
        newPurchase3.setScreening(newScreening);
        newPurchase3.setNumber(3);
        purchase3Id = purchaseRepository.addPurchase(newPurchase3);

        newUser3 = createUser();
    }

    @Test
    public void addPurchase() {
        Assert.assertNotEquals(0, purchase1Id);
        Assert.assertNotEquals(0, purchase2Id);
        Assert.assertNotEquals(0, purchase3Id);
    }

    @Test
    public void getPurchase() {
        Purchase found1 = purchaseRepository.getPurchase(purchase1Id);

        Assert.assertEquals(purchase1Id,          found1.getId());
        Assert.assertEquals(newUser1.getId(),     found1.getUser().getId());
        Assert.assertEquals(newScreening.getId(), found1.getScreening().getId());
        Assert.assertEquals(1,                    found1.getNumber());

        Purchase found2 = purchaseRepository.getPurchase(purchase2Id);

        Assert.assertEquals(purchase2Id,          found2.getId());
        Assert.assertEquals(newUser1.getId(),     found2.getUser().getId());
        Assert.assertEquals(newScreening.getId(), found2.getScreening().getId());
        Assert.assertEquals(2,                    found2.getNumber());

        Purchase found3 = purchaseRepository.getPurchase(purchase3Id);

        Assert.assertEquals(purchase3Id,          found3.getId());
        Assert.assertEquals(newUser2.getId(),     found3.getUser().getId());
        Assert.assertEquals(newScreening.getId(), found3.getScreening().getId());
        Assert.assertEquals(3,                    found3.getNumber());
    }

    @Test
    public void getPurchases() {
        List<Purchase> founds = purchaseRepository.getPurchases();

        for (Purchase found : founds) {
            if (found.getId() == purchase1Id) {
                Assert.assertEquals(purchase1Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),     found.getUser().getId());
                Assert.assertEquals(newScreening.getId(), found.getScreening().getId());
                Assert.assertEquals(1,                    found.getNumber());
            }

            if (found.getId() == purchase2Id) {
                Assert.assertEquals(purchase2Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),     found.getUser().getId());
                Assert.assertEquals(newScreening.getId(), found.getScreening().getId());
                Assert.assertEquals(2,                    found.getNumber());
            }

            if (found.getId() == purchase3Id) {
                Assert.assertEquals(purchase3Id,          found.getId());
                Assert.assertEquals(newUser2.getId(),     found.getUser().getId());
                Assert.assertEquals(newScreening.getId(), found.getScreening().getId());
                Assert.assertEquals(3,                    found.getNumber());
            }
        }
    }

    @Test
    public void getPurchasesByUserId() {
        List<Purchase> founds1 = purchaseRepository.getPurchasesByUserId(newUser1.getId());

        for (Purchase found : founds1) {
            if (found.getId() == purchase1Id) {
                Assert.assertEquals(purchase1Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),     found.getUser().getId());
                Assert.assertEquals(newScreening.getId(), found.getScreening().getId());
                Assert.assertEquals(1,                    found.getNumber());
            }

            if (found.getId() == purchase2Id) {
                Assert.assertEquals(purchase2Id,          found.getId());
                Assert.assertEquals(newUser1.getId(),     found.getUser().getId());
                Assert.assertEquals(newScreening.getId(), found.getScreening().getId());
                Assert.assertEquals(2,                    found.getNumber());
            }
        }

        List<Purchase> founds2 = purchaseRepository.getPurchasesByUserId(newUser2.getId());

        for (Purchase found : founds2) {
            if (found.getId() == purchase3Id) {
                Assert.assertEquals(purchase3Id,          found.getId());
                Assert.assertEquals(newUser2.getId(),     found.getUser().getId());
                Assert.assertEquals(newScreening.getId(), found.getScreening().getId());
                Assert.assertEquals(3,                    found.getNumber());
            }
        }

        List<Purchase> notFound = purchaseRepository.getPurchasesByUserId(newUser3.getId());
        Assert.assertEquals(0, notFound.size());
    }

    private User createUser() {
        UserImpl newUser = new UserImpl();
        newUser.setName("user" + new Random().nextInt(99999));
        newUser.setPassword("1234");

        long userId = userRepository.addUser(newUser);
        return userRepository.getUser(userId);
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

        long screeningId = screeningRepository.addScreening(newScreening);
        return screeningRepository.getScreening(screeningId);
    }

    private Movie createMovie() {
        MovieImpl newMovie = new MovieImpl();
        newMovie.setName("movie" + new Random().nextInt(99999));

        long movieId = movieRepository.addMovie(newMovie);
        return movieRepository.getMovie(movieId);
    }

    private Theater createTheater() {
        TheaterImpl newTheater = new TheaterImpl();
        newTheater.setName("theater" + new Random().nextInt(99999));

        long theaterId = theaterRepository.addTheater(newTheater);
        return theaterRepository.getTheater(theaterId);
    }
}

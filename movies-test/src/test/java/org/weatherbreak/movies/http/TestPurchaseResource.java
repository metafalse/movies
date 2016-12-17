package org.weatherbreak.movies.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class TestPurchaseResource {
    private static final String HTTP_HOST = "http://localhost:8081";
    private static final String URI_PATH = "/movies/purchases";
    private static final String URI_PATH_USERS = "/movies/users";
    private static final String URI_PATH_MOVIES = "/movies/movies";
    private static final String URI_PATH_THEATERS = "/movies/theaters";
    private static final String URI_PATH_SCREENINGS = "/movies/screenings";

    private Client client = ClientBuilder.newClient();
    private WebTarget target;
    private WebTarget targetUsers;
    private WebTarget targetMovies;
    private WebTarget targetTheaters;
    private WebTarget targetScreenings;

    @Before
    public void init(){
        target = client.target(HTTP_HOST).path(URI_PATH);
        targetUsers = client.target(HTTP_HOST).path(URI_PATH_USERS);
        targetMovies = client.target(HTTP_HOST).path(URI_PATH_MOVIES);
        targetTheaters = client.target(HTTP_HOST).path(URI_PATH_THEATERS);
        targetScreenings = client.target(HTTP_HOST).path(URI_PATH_SCREENINGS);
    }

    @Test
    public void testCreatePurchasesNoParamsXml() {
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity("<purchase/>", MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreatePurchasesNoParamsEntityXml() {
        HttpPurchase purchaseToSend = new HttpPurchase();
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(purchaseToSend, MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreatePurchaseNoParamsJson() {
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("{purchase:{}}", MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    @Test
    public void testCreatePurchaseNoParamsEntityJson() {
        HttpPurchase purchaseToSend = new HttpPurchase();
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(purchaseToSend, MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    private void verifyInvalid(Response response) {
        HttpError error = response.readEntity(HttpError.class);
        Assert.assertEquals(409, response.getStatus());
        Assert.assertEquals(409, error.status);
        Assert.assertEquals("INVALID_FIELD", error.code);
        Assert.assertEquals("user is required", error.message);
        Assert.assertEquals("", error.debug);
    }

    @Test
    public void testInvalidMessage() {
        HttpUser userToSend = new HttpUser();
        userToSend.name = "foo" + new Random().nextInt(99999);
        userToSend.password = "12345";
        Response responseUser = targetUsers.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userToSend, MediaType.APPLICATION_JSON));
        HttpUser createdUser = responseUser.readEntity(HttpUser.class);

        HttpMovie movieToSend = new HttpMovie();
        movieToSend.name = "foo" + new Random().nextInt(99999);
        Response responseMovie = targetMovies.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieToSend, MediaType.APPLICATION_JSON));
        HttpMovie createdMovie = responseMovie.readEntity(HttpMovie.class);

        HttpTheater theaterToSend = new HttpTheater();
        theaterToSend.name = "foo" + new Random().nextInt(99999);
        Response responseTheater = targetTheaters.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(theaterToSend, MediaType.APPLICATION_JSON));
        HttpTheater createdTheater = responseTheater.readEntity(HttpTheater.class);

        HttpScreening screeningToSend = new HttpScreening();
        screeningToSend.movieId = createdMovie.id;
        screeningToSend.theaterId = createdTheater.id;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            screeningToSend.showtime = sdf.parse("2016-12-24 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Response responseScreening = targetScreenings.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(screeningToSend, MediaType.APPLICATION_JSON));
        HttpScreening createdScreening = responseScreening.readEntity(HttpScreening.class);

        HttpPurchase userIsMissing = new HttpPurchase();
        userIsMissing.screeningId = createdScreening.id;
        userIsMissing.number = 2;
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("user is required", response1.readEntity(HttpError.class).message);

        HttpPurchase screeningIsMissing = new HttpPurchase();
        screeningIsMissing.userId = createdUser.id;
        screeningIsMissing.number = 2;
        Response response2 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(screeningIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("screening is required", response2.readEntity(HttpError.class).message);

        HttpPurchase numberIsMissing = new HttpPurchase();
        numberIsMissing.userId = createdUser.id;
        numberIsMissing.screeningId = createdScreening.id;
        Response response3 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(numberIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("number is invalid", response3.readEntity(HttpError.class).message);
    }

    @Test
    public void testCreateAndGetPurchase(){
        HttpUser userToSend = new HttpUser();
        userToSend.name = "foo" + new Random().nextInt(99999);
        userToSend.password = "12345";
        Response responseUser = targetUsers.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userToSend, MediaType.APPLICATION_JSON));
        HttpUser createdUser = responseUser.readEntity(HttpUser.class);

        HttpMovie movieToSend = new HttpMovie();
        movieToSend.name = "foo" + new Random().nextInt(99999);
        Response responseMovie = targetMovies.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieToSend, MediaType.APPLICATION_JSON));
        HttpMovie createdMovie = responseMovie.readEntity(HttpMovie.class);

        HttpTheater theaterToSend = new HttpTheater();
        theaterToSend.name = "foo" + new Random().nextInt(99999);
        Response responseTheater = targetTheaters.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(theaterToSend, MediaType.APPLICATION_JSON));
        HttpTheater createdTheater = responseTheater.readEntity(HttpTheater.class);

        HttpScreening screeningToSend = new HttpScreening();
        screeningToSend.movieId = createdMovie.id;
        screeningToSend.theaterId = createdTheater.id;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            screeningToSend.showtime = sdf.parse("2016-12-24 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Response responseScreening = targetScreenings.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(screeningToSend, MediaType.APPLICATION_JSON));
        HttpScreening createdScreening = responseScreening.readEntity(HttpScreening.class);

        HttpPurchase purchaseToSend = new HttpPurchase();
        purchaseToSend.userId = createdUser.id;
        purchaseToSend.screeningId = createdScreening.id;
        purchaseToSend.number = 2;

        // POST /purchases
        Response responsePost = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(purchaseToSend, MediaType.APPLICATION_JSON));
        HttpPurchase created = responsePost.readEntity(HttpPurchase.class);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull(created.id);
        Assert.assertEquals(created.userId, purchaseToSend.userId);
        Assert.assertEquals(created.screeningId, purchaseToSend.screeningId);
        Assert.assertEquals(created.number, purchaseToSend.number);

        // GET /purchases
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpPurchase> founds1 = response1.readEntity(new GenericType<List<HttpPurchase>>(){});
        for (HttpPurchase found : founds1) {
            if (found.id == created.id)
                Assert.assertEquals(found, created);
        }

        // GET /purchases?userId=:userId
        Response response2 = target.queryParam("userId", purchaseToSend.userId).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpPurchase> founds2 = response2.readEntity(new GenericType<List<HttpPurchase>>(){});
        Assert.assertEquals(founds2.get(0), created);

        // GET /purchases/:id
        WebTarget targetWithId = client.target(HTTP_HOST).path(URI_PATH + "/" + created.id);
        Response response3 = targetWithId.request().accept(MediaType.APPLICATION_JSON).get();
        HttpPurchase found3 = response3.readEntity(HttpPurchase.class);
        Assert.assertEquals(found3, created);
    }
}

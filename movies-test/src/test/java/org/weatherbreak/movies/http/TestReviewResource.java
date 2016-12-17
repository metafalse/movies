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
import java.util.List;
import java.util.Random;

public class TestReviewResource {
    private static final String HTTP_HOST = "http://localhost:8081";
    private static final String URI_PATH = "/movies/reviews";
    private static final String URI_PATH_USERS = "/movies/users";
    private static final String URI_PATH_MOVIES = "/movies/movies";

    private Client client = ClientBuilder.newClient();
    private WebTarget target;
    private WebTarget targetUsers;
    private WebTarget targetMovies;

    @Before
    public void init(){
        target = client.target(HTTP_HOST).path(URI_PATH);
        targetUsers = client.target(HTTP_HOST).path(URI_PATH_USERS);
        targetMovies = client.target(HTTP_HOST).path(URI_PATH_MOVIES);
    }

    @Test
    public void testCreateReviewsNoParamsXml() {
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity("<review/>", MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateReviewsNoParamsEntityXml() {
        HttpReview reviewToSend = new HttpReview();
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(reviewToSend, MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateReviewNoParamsJson() {
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("{review:{}}", MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    @Test
    public void testCreateReviewNoParamsEntityJson() {
        HttpReview reviewToSend = new HttpReview();
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(reviewToSend, MediaType.APPLICATION_JSON));

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

        HttpReview userIsMissing = new HttpReview();
        userIsMissing.movieId = createdMovie.id;
        userIsMissing.title = "testTitle";
        userIsMissing.description = "testDescription";
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("user is required", response1.readEntity(HttpError.class).message);

        HttpReview movieIsMissing = new HttpReview();
        movieIsMissing.userId = createdUser.id;
        movieIsMissing.title = "testTitle";
        movieIsMissing.description = "testDescription";
        Response response2 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("movie is required", response2.readEntity(HttpError.class).message);

        HttpReview titleIsMissing = new HttpReview();
        titleIsMissing.userId = createdUser.id;
        titleIsMissing.movieId = createdMovie.id;
        titleIsMissing.description = "testDescription";
        Response response3 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(titleIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("title is invalid", response3.readEntity(HttpError.class).message);

        HttpReview descriptionIsMissing = new HttpReview();
        descriptionIsMissing.userId = createdUser.id;
        descriptionIsMissing.movieId = createdMovie.id;
        descriptionIsMissing.title = "testTitle";
        Response response4 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(descriptionIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("description is invalid", response4.readEntity(HttpError.class).message);
    }

    @Test
    public void testCreateAndGetReview(){
        HttpUser userToSend = new HttpUser();
        userToSend.name = "foo" + new Random().nextInt(99999);
        userToSend.password = "12345";
        Response responseUser = targetUsers.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userToSend, MediaType.APPLICATION_JSON));
        HttpUser createdUser = responseUser.readEntity(HttpUser.class);

        HttpMovie movieToSend = new HttpMovie();
        movieToSend.name = "foo" + new Random().nextInt(99999);
        Response responseMovie = targetMovies.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieToSend, MediaType.APPLICATION_JSON));
        HttpMovie createdMovie = responseMovie.readEntity(HttpMovie.class);

        HttpReview reviewToSend = new HttpReview();
        reviewToSend.userId = createdUser.id;
        reviewToSend.movieId = createdMovie.id;
        reviewToSend.title = "testTitle";
        reviewToSend.description = "testDescription";

        // POST /reviews
        Response responsePost = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(reviewToSend, MediaType.APPLICATION_JSON));
        HttpReview created = responsePost.readEntity(HttpReview.class);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull(created.id);
        Assert.assertEquals(created.userId, reviewToSend.userId);
        Assert.assertEquals(created.movieId, reviewToSend.movieId);
        Assert.assertEquals(created.title, reviewToSend.title);
        Assert.assertEquals(created.description, reviewToSend.description);

        // GET /reviews
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpReview> founds1 = response1.readEntity(new GenericType<List<HttpReview>>(){});
        for (HttpReview found : founds1) {
            if (found.id == created.id)
                Assert.assertEquals(found, created);
        }

        // GET /reviews?userId=:userId
        Response response2 = target.queryParam("userId", reviewToSend.userId).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpReview> founds2 = response2.readEntity(new GenericType<List<HttpReview>>(){});
        Assert.assertEquals(founds2.get(0), created);

        // GET /screenings?movieId=:movieId
        Response response3 = target.queryParam("movieId", reviewToSend.movieId).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpReview> founds3 = response3.readEntity(new GenericType<List<HttpReview>>(){});
        Assert.assertEquals(founds3.get(0), created);

        // GET /screenings/:id
        WebTarget targetWithId = client.target(HTTP_HOST).path(URI_PATH + "/" + created.id);
        Response response4 = targetWithId.request().accept(MediaType.APPLICATION_JSON).get();
        HttpReview found4 = response4.readEntity(HttpReview.class);
        Assert.assertEquals(found4, created);
    }
}

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

public class TestScreeningResource {
    private static final String HTTP_HOST = "http://localhost:8081";
    private static final String URI_PATH = "/movies/screenings";
    private static final String URI_PATH_MOVIES = "/movies/movies";
    private static final String URI_PATH_THEATERS = "/movies/theaters";

    private Client client = ClientBuilder.newClient();
    private WebTarget target;
    private WebTarget targetMovies;
    private WebTarget targetTheaters;

    @Before
    public void init(){
        target = client.target(HTTP_HOST).path(URI_PATH);
        targetMovies = client.target(HTTP_HOST).path(URI_PATH_MOVIES);
        targetTheaters = client.target(HTTP_HOST).path(URI_PATH_THEATERS);
    }

    @Test
    public void testCreateScreeningsNoParamsXml() {
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity("<screening/>", MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateScreeningsNoParamsEntityXml() {
        HttpScreening screeningToSend = new HttpScreening();
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(screeningToSend, MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateScreeningsNoParamsJson() {
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("{screening:{}}", MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    @Test
    public void testCreateScreeningsNoParamsEntityJson() {
        HttpScreening screeningToSend = new HttpScreening();
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(screeningToSend, MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    private void verifyInvalid(Response response) {
        HttpError error = response.readEntity(HttpError.class);
        Assert.assertEquals(409, response.getStatus());
        Assert.assertEquals(409, error.status);
        Assert.assertEquals("INVALID_FIELD", error.code);
        Assert.assertEquals("movie is required", error.message);
        Assert.assertEquals("", error.debug);
    }

    @Test
    public void testInvalidMessage() {
        HttpMovie movieToSend = new HttpMovie();
        movieToSend.name = "foo" + new Random().nextInt(99999);
        Response responseMovie = targetMovies.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieToSend, MediaType.APPLICATION_JSON));
        HttpMovie createdMovie = responseMovie.readEntity(HttpMovie.class);

        HttpTheater theaterToSend = new HttpTheater();
        theaterToSend.name = "foo" + new Random().nextInt(99999);
        Response responseTheater = targetTheaters.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(theaterToSend, MediaType.APPLICATION_JSON));
        HttpTheater createdTheater = responseTheater.readEntity(HttpTheater.class);

        HttpScreening movieIsMissing = new HttpScreening();
        movieIsMissing.theaterId = createdTheater.id;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            movieIsMissing.showtime = sdf.parse("2016-12-24 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("movie is required", response1.readEntity(HttpError.class).message);

        HttpScreening theaterIsMissing = new HttpScreening();
        theaterIsMissing.movieId = createdMovie.id;
        try {
            theaterIsMissing.showtime = sdf.parse("2016-12-24 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Response response2 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(theaterIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("theater is required", response2.readEntity(HttpError.class).message);

        HttpScreening showtimeIsMissing = new HttpScreening();
        showtimeIsMissing.movieId = createdMovie.id;
        showtimeIsMissing.theaterId = createdTheater.id;
        Response response3 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(showtimeIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("showtime is required", response3.readEntity(HttpError.class).message);
    }

    @Test
    public void testCreateAndGetScreening(){
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

        // POST /screenings
        Response responsePost = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(screeningToSend, MediaType.APPLICATION_JSON));
        HttpScreening created = responsePost.readEntity(HttpScreening.class);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull(created.id);
        Assert.assertEquals(created.movieId, screeningToSend.movieId);
        Assert.assertEquals(created.theaterId, screeningToSend.theaterId);
        Assert.assertEquals(created.showtime, screeningToSend.showtime);

        // GET /screenings
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpScreening> founds1 = response1.readEntity(new GenericType<List<HttpScreening>>(){});
        for (HttpScreening found : founds1) {
            if (found.id == created.id)
                Assert.assertEquals(found, created);
        }

        // GET /screenings?movieId=:movieId
        Response response2 = target.queryParam("movieId", screeningToSend.movieId).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpScreening> founds2 = response2.readEntity(new GenericType<List<HttpScreening>>(){});
        Assert.assertEquals(founds2.get(0), created);

        // GET /screenings?theaterId=:theaterId
        Response response3 = target.queryParam("theaterId", screeningToSend.theaterId).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpScreening> founds3 = response3.readEntity(new GenericType<List<HttpScreening>>(){});
        Assert.assertEquals(founds3.get(0), created);

        // GET /screenings?movieId=:movieId&theaterId=:theaterId
        Response response4 = target.queryParam("movieId", screeningToSend.movieId).queryParam("theaterId", screeningToSend.theaterId).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpScreening> founds4 = response4.readEntity(new GenericType<List<HttpScreening>>(){});
        Assert.assertEquals(founds4.get(0), created);

        // GET /screenings/:id
        WebTarget targetWithId = client.target(HTTP_HOST).path(URI_PATH + "/" + created.id);
        Response response5 = targetWithId.request().accept(MediaType.APPLICATION_JSON).get();
        HttpScreening found5 = response5.readEntity(HttpScreening.class);
        Assert.assertEquals(found5, created);
    }
}

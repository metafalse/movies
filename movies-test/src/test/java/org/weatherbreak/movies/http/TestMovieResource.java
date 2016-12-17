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

public class TestMovieResource {
    private static final String HTTP_HOST = "http://localhost:8081";
    private static final String URI_PATH = "/movies/movies";

    private Client client = ClientBuilder.newClient();
    private WebTarget target;

    @Before
    public void init(){
        target = client.target(HTTP_HOST).path(URI_PATH);
    }

    @Test
    public void testCreateMoviesNoParamsXml() {
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity("<movie/>", MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateMoviesNoParamsEntityXml() {
        HttpMovie movieToSend = new HttpMovie();
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(movieToSend, MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateMoviesNoParamsJson() {
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("{movie:{}}", MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    @Test
    public void testCreateMoviesNoParamsEntityJson() {
        HttpMovie movieToSend = new HttpMovie();
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieToSend, MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    private void verifyInvalid(Response response) {
        HttpError error = response.readEntity(HttpError.class);
        Assert.assertEquals(409, response.getStatus());
        Assert.assertEquals(409, error.status);
        Assert.assertEquals("INVALID_FIELD", error.code);
        Assert.assertEquals("name is invalid", error.message);
        Assert.assertEquals("", error.debug);
    }

    @Test
    public void testCreateAndGetMovie() {
        HttpMovie movieToSend = new HttpMovie();
        movieToSend.name = "foo" + new Random().nextInt(99999);

        // POST /movies
        Response responsePost = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(movieToSend, MediaType.APPLICATION_JSON));
        HttpMovie created = responsePost.readEntity(HttpMovie.class);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull(created.id);
        Assert.assertEquals(created.name, movieToSend.name);

        // GET /movies
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpMovie> founds1 = response1.readEntity(new GenericType<List<HttpMovie>>(){});
        for (HttpMovie found : founds1) {
            if (found.id == created.id)
                Assert.assertEquals(found, created);
        }

        // GET /movies?name=:name
        Response response2 = target.queryParam("name", movieToSend.name).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpMovie> founds2 = response2.readEntity(new GenericType<List<HttpMovie>>(){});
        Assert.assertEquals(founds2.get(0), created);

        // GET /movies/:id
        WebTarget targetWithId = client.target(HTTP_HOST).path(URI_PATH + "/" + created.id);
        Response response3 = targetWithId.request().accept(MediaType.APPLICATION_JSON).get();
        HttpMovie found3 = response3.readEntity(HttpMovie.class);
        Assert.assertEquals(found3, created);
    }
}

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

public class TestTheaterResource {
    private static final String HTTP_HOST = "http://localhost:8081";
    private static final String URI_PATH = "/movies/theaters";

    private Client client = ClientBuilder.newClient();
    private WebTarget target;

    @Before
    public void init(){
        target = client.target(HTTP_HOST).path(URI_PATH);
    }

    @Test
    public void testCreateTheatersNoParamsXml() {
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity("<theater/>", MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateTheatersNoParamsEntityXml() {
        HttpTheater theaterToSend = new HttpTheater();
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(theaterToSend, MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateTheatersNoParamsJson() {
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("{theater:{}}", MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    @Test
    public void testCreateTheatersNoParamsEntityJson() {
        HttpTheater theaterToSend = new HttpTheater();
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(theaterToSend, MediaType.APPLICATION_JSON));

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
    public void testCreateAndGetTheater() {
        HttpTheater theaterToSend = new HttpTheater();
        theaterToSend.name = "foo" + new Random().nextInt(99999);

        // POST /theaters
        Response responsePost = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(theaterToSend, MediaType.APPLICATION_JSON));
        HttpTheater created = responsePost.readEntity(HttpTheater.class);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull(created.id);
        Assert.assertEquals(created.name, theaterToSend.name);

        // GET /theaters
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpTheater> founds1 = response1.readEntity(new GenericType<List<HttpTheater>>(){});
        for (HttpTheater found : founds1) {
            if (found.id == created.id)
                Assert.assertEquals(found, created);
        }

        // GET /theaters?name=:name
        Response response2 = target.queryParam("name", theaterToSend.name).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpTheater> founds2 = response2.readEntity(new GenericType<List<HttpTheater>>(){});
        Assert.assertEquals(founds2.get(0), created);

        // GET /theaters/:id
        WebTarget targetWithId = client.target(HTTP_HOST).path(URI_PATH + "/" + created.id);
        Response response3 = targetWithId.request().accept(MediaType.APPLICATION_JSON).get();
        HttpTheater found3 = response3.readEntity(HttpTheater.class);
        Assert.assertEquals(found3, created);
    }
}

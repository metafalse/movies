package org.weatherbreak.movies.http;

import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserResource {
    private static final String HTTP_HOST = "http://localhost:8081";
    private static final String URI_PATH = "/movies/users";

    private Client client = ClientBuilder.newClient();
    private WebTarget target;

    @Before
    public void init(){
        target = client.target(HTTP_HOST).path(URI_PATH);
    }

    @Test
    public void testCreateUsersNoParamsXml() {
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity("<user/>", MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateUsersNoParamsEntityXml() {
        HttpUser userToSend = new HttpUser();
        Response response =	target.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(userToSend, MediaType.APPLICATION_XML));

        verifyInvalid(response);
    }

    @Test
    public void testCreateUsersNoParamsJson() {
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("{user:{}}", MediaType.APPLICATION_JSON));

        verifyInvalid(response);
    }

    @Test
    public void testCreateUsersNoParamsEntityJson() {
        HttpUser userToSend = new HttpUser();
        Response response =	target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userToSend, MediaType.APPLICATION_JSON));

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
    public void testInvalidMessage() {
        HttpUser nameIsMissing = new HttpUser();
        nameIsMissing.password = "12345";
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(nameIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("name is invalid", response1.readEntity(HttpError.class).message);

        HttpUser passwordIsMissing = new HttpUser();
        passwordIsMissing.name = "foo" + new Random().nextInt(99999);
        Response response2 = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(passwordIsMissing, MediaType.APPLICATION_JSON));

        Assert.assertEquals("password is invalid", response2.readEntity(HttpError.class).message);
    }

    @Test
    public void testCreateAndGetUser() {
        HttpUser userToSend = new HttpUser();
        userToSend.name = "foo" + new Random().nextInt(99999);
        userToSend.password = "12345";

        // POST /users
        Response responsePost = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(userToSend, MediaType.APPLICATION_JSON));
        HttpUser created = responsePost.readEntity(HttpUser.class);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull(created.id);
        Assert.assertEquals(created.name, userToSend.name);
        Assert.assertNull(created.password);

        // GET /users
        Response response1 = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpUser> founds1 = response1.readEntity(new GenericType<List<HttpUser>>(){});
        for (HttpUser found : founds1) {
            if (found.id == created.id)
                Assert.assertEquals(found, created);
        }

        // GET /users?name=:name
        Response response2 = target.queryParam("name", userToSend.name).request().accept(MediaType.APPLICATION_JSON).get();
        List<HttpUser> founds2 = response2.readEntity(new GenericType<List<HttpUser>>(){});
        Assert.assertEquals(founds2.get(0), created);

        // GET /users/:id
        WebTarget targetWithId = client.target(HTTP_HOST).path(URI_PATH + "/" + created.id);
        Response response3 = targetWithId.request().accept(MediaType.APPLICATION_JSON).get();
        HttpUser found3 = response3.readEntity(HttpUser.class);
        Assert.assertEquals(found3, created);
    }
}

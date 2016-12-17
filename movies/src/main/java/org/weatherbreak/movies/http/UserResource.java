package org.weatherbreak.movies.http;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.UserImpl;
import org.weatherbreak.movies.http.entity.HttpUser;
import org.weatherbreak.movies.service.UserService;
import org.weatherbreak.movies.service.exception.MoviesException;

@Path("/users")
@Component
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class UserResource {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/")
    public Response addUser(HttpUser newUser) {
        User userToCreate = convert(newUser);
        User added = userService.addUser(userToCreate);
        return Response.status(Status.CREATED).header("Location", "/users/" + added.getId()).entity(new HttpUser(added)).build();
    }

    @GET
    @Path("/")
    @Wrapped(element="users")
    public List<HttpUser> getUsers(@QueryParam("name") String name) throws MoviesException {
        List<HttpUser> returnList;
        List<User> founds;

        if (name != null) {
            logger.info("user search name = " + name);
            founds = userService.getUsersByName(name);
        }
        else {
            logger.info("getting all users");
            founds = userService.getUsers();
        }

        returnList = new ArrayList<>(founds.size());
        for (User found : founds) {
            returnList.add(new HttpUser(found));
        }

        return returnList;
    }

    @GET
    @Path("/{userId}")
    public HttpUser getUser(@PathParam("userId") long userId) {
        logger.info("getting user by id : " + userId);
        User user = userService.getUser(userId);
        return new HttpUser(user);
    }

    private User convert(HttpUser httpUser) {
        UserImpl user = new UserImpl();
        user.setName(httpUser.name);
        user.setPassword(httpUser.password);
        return user;
    }
}

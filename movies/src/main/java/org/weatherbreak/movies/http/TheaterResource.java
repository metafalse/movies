package org.weatherbreak.movies.http;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.entity.impl.TheaterImpl;
import org.weatherbreak.movies.http.entity.HttpTheater;
import org.weatherbreak.movies.service.TheaterService;
import org.weatherbreak.movies.service.exception.MoviesException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/theaters")
@Component
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class TheaterResource {
    @Autowired
    private TheaterService theaterService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/")
    public Response addTheater(HttpTheater newTheater) {
        Theater theaterToCreate = convert(newTheater);
        Theater added = theaterService.addTheater(theaterToCreate);
        return Response.status(Status.CREATED).header("Location", "/theaters/" + added.getId()).entity(new HttpTheater(added)).build();
    }

    @GET
    @Path("/")
    @Wrapped(element="theaters")
    public List<HttpTheater> getTheaters(@QueryParam("name") String name, @QueryParam("movieId") long movieId) throws MoviesException {
        List<HttpTheater> returnList;
        List<Theater> founds;

        if (name != null) {
            logger.info("theater search name = " + name);
            founds = theaterService.getTheatersByName(name);
        }
        else if (movieId != 0) {
            logger.info("getting theaters by movieId : " + movieId);
            founds = theaterService.getTheatersByMovieId(movieId);
        }
        else {
            logger.info("getting all theaters");
            founds = theaterService.getTheaters();
        }

        returnList = new ArrayList<>(founds.size());
        for (Theater found : founds) {
            returnList.add(new HttpTheater(found));
        }

        return returnList;
    }

    @GET
    @Path("/{theaterId}")
    public HttpTheater getTheater(@PathParam("theaterId") long theaterId) {
        logger.info("getting theater by id : " + theaterId);
        Theater theater = theaterService.getTheater(theaterId);
        return new HttpTheater(theater);
    }

    private Theater convert(HttpTheater httpTheater) {
        TheaterImpl theater = new TheaterImpl();
        theater.setName(httpTheater.name);
        return theater;
    }
}

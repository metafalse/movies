package org.weatherbreak.movies.http;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.impl.ScreeningImpl;
import org.weatherbreak.movies.http.entity.HttpScreening;
import org.weatherbreak.movies.service.MovieService;
import org.weatherbreak.movies.service.ScreeningService;
import org.weatherbreak.movies.service.TheaterService;
import org.weatherbreak.movies.service.exception.MoviesException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/screenings")
@Component
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ScreeningResource {
    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterService theaterService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/")
    public Response addScreening(HttpScreening newScreening) {
        Screening screeningToCreate = convert(newScreening);
        Screening added = screeningService.addScreening(screeningToCreate);
        return Response.status(Status.CREATED).header("Location", "/screenings/" + added.getId()).entity(new HttpScreening(added)).build();
    }

    @GET
    @Path("/")
    @Wrapped(element="screenings")
    public List<HttpScreening> getScreenings(@QueryParam("movieId") long movieId, @QueryParam("theaterId") long theaterId) throws MoviesException {
        List<HttpScreening> returnList;
        List<Screening> founds;

        if (movieId != 0 && theaterId != 0) {
            logger.info("getting screenings by movieId : " + movieId + " and theaterId : " + theaterId);
            founds = screeningService.getScreeningsByMovieIdAndTheaterId(movieId, theaterId);
        }
        else if (movieId != 0) {
            logger.info("getting screenings by movieId : " + movieId);
            founds = screeningService.getScreeningsByMovieId(movieId);
        }
        else if (theaterId != 0) {
            logger.info("getting screenings by theaterId : " + theaterId);
            founds = screeningService.getScreeningsByTheaterId(theaterId);
        }
        else {
            logger.info("getting all screenings");
            founds = screeningService.getScreenings();
        }

        returnList = new ArrayList<>(founds.size());
        for (Screening found : founds) {
            returnList.add(new HttpScreening(found));
        }

        return returnList;
    }

    @GET
    @Path("/{screeningId}")
    public HttpScreening getScreening(@PathParam("screeningId") long screeningId) {
        logger.info("getting screening by id : " + screeningId);
        Screening screening = screeningService.getScreening(screeningId);
        return new HttpScreening(screening);
    }

    private Screening convert(HttpScreening httpScreening) {
        ScreeningImpl screening = new ScreeningImpl();
        screening.setMovie(movieService.getMovie(httpScreening.movieId));
        screening.setTheater(theaterService.getTheater(httpScreening.theaterId));
        screening.setShowtime(httpScreening.showtime);
        return screening;
    }
}

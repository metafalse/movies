package org.weatherbreak.movies.http;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weatherbreak.movies.entity.Movie;
import org.weatherbreak.movies.entity.impl.MovieImpl;
import org.weatherbreak.movies.http.entity.HttpMovie;
import org.weatherbreak.movies.service.MovieService;
import org.weatherbreak.movies.service.exception.MoviesException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/movies")
@Component
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MovieResource {
    @Autowired
    private MovieService movieService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/")
    public Response addMovie(HttpMovie newMovie) {
        Movie movieToCreate = convert(newMovie);
        Movie added = movieService.addMovie(movieToCreate);
        return Response.status(Status.CREATED).header("Location", "/movies/" + added.getId()).entity(new HttpMovie(added)).build();
    }

    @GET
    @Path("/")
    @Wrapped(element="movies")
    public List<HttpMovie> getMovies(@QueryParam("name") String name, @QueryParam("theaterId") long theaterId) throws MoviesException {
        List<HttpMovie> returnList;
        List<Movie> founds;

        if (name != null) {
            logger.info("movie search name = " + name);
            founds = movieService.getMoviesByName(name);
        }
        else if (theaterId != 0) {
            logger.info("getting movies by theaterId : " + theaterId);
            founds = movieService.getMoviesByTheaterId(theaterId);
        }
        else {
            logger.info("getting all movies");
            founds = movieService.getMovies();
        }

        returnList = new ArrayList<>(founds.size());
        for (Movie found : founds) {
            returnList.add(new HttpMovie(found));
        }

        return returnList;
    }

    @GET
    @Path("/{movieId}")
    public HttpMovie getMovie(@PathParam("movieId") long movieId) {
        logger.info("getting movie by id : " + movieId);
        Movie movie = movieService.getMovie(movieId);
        return new HttpMovie(movie);
    }

    private Movie convert(HttpMovie httpMovie) {
        MovieImpl movie = new MovieImpl();
        movie.setName(httpMovie.name);
        return movie;
    }
}

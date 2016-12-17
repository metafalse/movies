package org.weatherbreak.movies.http;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weatherbreak.movies.entity.Purchase;
import org.weatherbreak.movies.entity.impl.PurchaseImpl;
import org.weatherbreak.movies.http.entity.HttpPurchase;
import org.weatherbreak.movies.service.PurchaseService;
import org.weatherbreak.movies.service.ScreeningService;
import org.weatherbreak.movies.service.UserService;
import org.weatherbreak.movies.service.exception.MoviesException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/purchases")
@Component
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class PurchaseResource {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScreeningService screeningService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/")
    public Response addPurchase(HttpPurchase newPurchase) {
        Purchase purchaseToCreate = convert(newPurchase);
        Purchase added = purchaseService.addPurchase(purchaseToCreate);
        return Response.status(Status.CREATED).header("Location", "/purchases/" + added.getId()).entity(new HttpPurchase(added)).build();
    }

    @GET
    @Path("/")
    @Wrapped(element="purchases")
    public List<HttpPurchase> getPurchases(@QueryParam("userId") long userId) throws MoviesException {
        List<HttpPurchase> returnList;
        List<Purchase> founds;

        if (userId != 0) {
            logger.info("getting purchases by userId : " + userId);
            founds = purchaseService.getPurchasesByUserId(userId);
        }
        else {
            logger.info("getting all purchases");
            founds = purchaseService.getPurchases();
        }

        returnList = new ArrayList<>(founds.size());
        for (Purchase found : founds) {
            returnList.add(new HttpPurchase(found));
        }

        return returnList;
    }

    @GET
    @Path("/{purchaseId}")
    public HttpPurchase getPurchase(@PathParam("purchaseId") long purchaseId) {
        logger.info("getting purchase by id : " + purchaseId);
        Purchase purchase = purchaseService.getPurchase(purchaseId);
        return new HttpPurchase(purchase);
    }

    private Purchase convert(HttpPurchase httpPurchase) {
        PurchaseImpl purchase = new PurchaseImpl();
        purchase.setUser(userService.getUser(httpPurchase.userId));
        purchase.setScreening(screeningService.getScreening(httpPurchase.screeningId));
        purchase.setNumber(httpPurchase.number);
        return purchase;
    }
}

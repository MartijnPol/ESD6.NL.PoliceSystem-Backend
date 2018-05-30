package rest;

import domain.StolenCar;
import service.StolenCarService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Path("StolenCars")
@Stateless
public class StolenCarResource {

    @Inject
    private StolenCarService stolenCarService;

    /**
     * Resource to get all StolenCars from the database
     *
     * @return a (JSON) list of all StolenCars
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<StolenCar> stolenCarList = stolenCarService.findAll();

        if (stolenCarList.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(stolenCarService.convertToJson(stolenCarList)).build();
    }

    /**
     * Resource to find a specific StolenCar by it's id
     *
     * @param id is the id of the StolenCar
     * @return a StolenCar, if found
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        StolenCar stolenCar = stolenCarService.findById(id);

        if (stolenCar == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(stolenCar.toJson()).build();
    }

    /**
     * Resource to create a new StolenCar
     *
     * @param stolenCar is the new StolenCar object
     * @return returns a link created with the new id
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(StolenCar stolenCar) {
        if (stolenCar == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        stolenCarService.create(stolenCar);
        URI id = URI.create(stolenCar.getId().toString());
        return Response.created(id).build();
    }
}

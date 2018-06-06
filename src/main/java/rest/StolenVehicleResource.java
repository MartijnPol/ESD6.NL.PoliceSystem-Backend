package rest;

import domain.StolenVehicle;
import service.StolenVehicleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
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
public class StolenVehicleResource {

    @Inject
    private StolenVehicleService stolenCarService;

    /**
     * Resource to get all StolenCars from the database
     *
     * @return a (JSON) list of all StolenCars
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<StolenVehicle> stolenCarList = stolenCarService.findAll();

        if (stolenCarList.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(stolenCarService.convertToJson(stolenCarList)).build();
    }

    /**
     * Resource to find a specific StolenVehicle by it's id
     *
     * @param id is the id of the StolenVehicle
     * @return a StolenVehicle, if found
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        StolenVehicle stolenCar = stolenCarService.findById(id);

        if (stolenCar == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(stolenCar.toJson()).build();
    }

    /**
     * Resource to create a new StolenVehicle
     *
     * @param json is the new StolenVehicle object in a JSONObject
     * @return returns a link created with the new id
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject json) {
        if (json == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        StolenVehicle stolenCar = new StolenVehicle(json.getString("licensePlate"), json.getBoolean("isStolen"));
        stolenCar = stolenCarService.create(stolenCar);
        URI id = URI.create(stolenCar.getId().toString());
        return Response.created(id).build();
    }
}

package rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import domain.StolenVehicle;
import helpers.RestHelper;
import jwt.SecuredWithJWT;
import service.StolenVehicleService;
import websocket.WebSocketServer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Path("stolen-vehicles")
@Stateless
@SecuredWithJWT
public class StolenVehicleResource {

    @Inject
    private StolenVehicleService stolenVehicleService;

    /**
     * Find all stolen vehicles based on given isStolen param
     *
     * @param isStolen whether the vehicles are stolen or not
     * @return a List containing all requested type of vehicles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("isStolen") Boolean isStolen) {

        List<StolenVehicle> stolenCarList;

        if (isStolen == null) {
            stolenCarList = stolenVehicleService.findAll();
        } else {
            stolenCarList = stolenVehicleService.findAll(isStolen);
        }

        return Response.ok(stolenVehicleService.convertToJson(stolenCarList)).build();
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
        StolenVehicle stolenCar = stolenVehicleService.findById(id);

        if (stolenCar == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(stolenCar.toJson()).build();
    }

    @GET
    @Path("/find/owners")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByLicensePlate(@QueryParam("licensePlate") String licensePlate) {
        try {
            HttpResponse<JsonNode> response = Unirest.get("http://192.168.25.122:8080/AccountAdministrationSystem/api/cars/find/" + licensePlate)
                    .header("Authorization", "Bearer" + RestHelper.getAasJwtToken())
                    .asJson();
            return Response.ok(response.getBody().toString()).build();
        } catch (UnirestException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Resource to create a new StolenVehicle
     *
     * @param json is the new StolenVehicle object in a JSONObject
     * @return status
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject json) {
        if (json == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        String licensePlate = json.getString("licensePlate");
        boolean isStolen = json.getBoolean("isStolen");

        // Check if vehicle is stolen before (no duplicate entries in PoliceSystem database)
        StolenVehicle foundVehicle = this.stolenVehicleService.findByLicensePlate(licensePlate);
        if (foundVehicle != null) {
            if (!foundVehicle.isStolen()) {
                foundVehicle.setStolen(isStolen);
                stolenVehicleService.update(foundVehicle);
                WebSocketServer.broadcastMessage("reload");
            }
        } else {
            StolenVehicle stolenCar = new StolenVehicle(licensePlate, isStolen);
            stolenVehicleService.create(stolenCar);
            WebSocketServer.broadcastMessage("reload");
        }

        return Response.ok().build();
    }

    /**
     * Resource to update an existing StolenVehicle
     *
     * @param json is the updated StolenVehicle
     * @return updated StolenVehicle
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject json) {
        if (json == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        StolenVehicle updatedVehicle = stolenVehicleService.findById(new Long(json.getJsonNumber("id").toString()));
        if (updatedVehicle == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        updatedVehicle.setStolen(Boolean.valueOf(json.getString("isStolen")));
        stolenVehicleService.update(updatedVehicle);
        WebSocketServer.broadcastMessage("reload");
        return Response.ok(updatedVehicle.toJson()).build();
    }
}

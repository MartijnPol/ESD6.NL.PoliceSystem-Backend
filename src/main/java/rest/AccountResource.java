package rest;

import domain.Account;
import service.AccountService;
import utils.TokenHelper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/
@Path("accounts")
@Stateless
public class AccountResource {

    @Inject
    private AccountService accountService;

    /**
     * Resource to get a valid jwt token
     *
     * @param credentials are the credentials containing the username and password
     * @return a valid jwt token if credentials are correct, otherwise null
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(JsonObject credentials) {

        Account foundAccount = accountService.findByUsername(credentials.getString("username"));
        if (foundAccount == null) {
            return Response.ok(Json.createObjectBuilder().add("isValid", false).build()).header("Access-Control-Allow-Origin", "*").build();
        }

        // Check if credentials are correct / valid
        if (foundAccount.getUsername().equals(credentials.getString("username")) && foundAccount.getPassword().equals(credentials.getString("password"))) {
            return Response.ok(Json.createObjectBuilder().add("isValid", true)
                    .add("token", TokenHelper.EncodeToken(foundAccount.getUsername()))
                    .add("username", foundAccount.getUsername()).build()).header("Access-Control-Allow-Origin", "*").build();

        }

        return Response.ok(Json.createObjectBuilder().add("isValid", false).build()).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Resource to verify the given token
     *
     * @param token is the token that needs to be verified
     * @return response
     */
    @POST
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verify(String token) {
        if (TokenHelper.CheckIfTokenIsTrusted(token)) {
            return Response.ok(Json.createObjectBuilder().add("isValid", true).build()).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Json.createObjectBuilder().add("isValid", false).build()).header("Access-Control-Allow-Origin", "*").build();
    }

}

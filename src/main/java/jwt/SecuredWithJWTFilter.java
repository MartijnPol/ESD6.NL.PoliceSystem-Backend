package jwt;

import utils.TokenHelper;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/

@Provider
@SecuredWithJWT
@Priority(Priorities.AUTHENTICATION)
public class SecuredWithJWTFilter implements ContainerRequestFilter {

    public void filter(ContainerRequestContext requestContext) {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            TokenHelper.CheckIfTokenIsTrusted(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build());
        }
    }

}

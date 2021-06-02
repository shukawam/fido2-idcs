
package me.shukawam.example.fido;

import java.util.Collections;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.helidon.security.abac.scope.ScopeValidator;
import io.helidon.security.annotations.Authenticated;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class GreetResource {
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private final String message;

    @Inject
    public GreetResource(@ConfigProperty(name = "app.greeting") String message) {
        this.message = message;
    }

    // only authentication
    @GET
    @Path("greet1")
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject greet1() {
        return defaultMessage();
    }

    // authentication and required scope.
    @GET
    @Path("greet2")
    @Authenticated
    @ScopeValidator.Scope("first_scope")
    @ScopeValidator.Scope("second_scope")
    public JsonObject greet2() {
        return defaultMessage();
    }

    // Authentication and rbac
    @GET
    @Path("greet3")
    @Authenticated
    @RolesAllowed({"Admin", "Guest"})
    public JsonObject greet3() {
        return defaultMessage();
    }

    // Invalid role required
    @GET
    @Path("greet4")
    @Authenticated
    @RolesAllowed("Dummy")
    public JsonObject greet4() {
        return defaultMessage();
    }

    // Invalid scope
    @GET
    @Path("greet5")
    @Authenticated
    @ScopeValidator.Scope("dummy_scope")
    public JsonObject greet5() {
        return defaultMessage();
    }

    private JsonObject defaultMessage() {
        String msg = String.format("%s %s!", message, "World");
        return JSON.createObjectBuilder()
                .add("message", msg)
                .build();
    }

}

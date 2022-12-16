package com.dokleameci.profiles;

import com.dokleameci.promotions.Promotion;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {
    @Inject
    ProfileService service;

    @GET
    @Path("{id}")
    public Uni<Response> getProfile(@PathParam("id") Long id) {
        return service.findProfileById(id)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build);
    }

    @GET
    public Uni<Response> profiles() {
        return service.allProfiles()
                .map(Response::ok)
                .map(Response.ResponseBuilder::build);
    }

    @POST
    @Transactional
    public Uni<Response> create(Profile profile) {
        return Uni.createFrom()
                .item(profile)
                .onItem()
                .invoke(() -> service.save(profile))
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .onFailure()
                .recoverWithItem(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Profile already exists in db")
                        .build());
    }

}

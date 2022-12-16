package com.dokleameci.promotions;

import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionResource {
    @Inject
    PromotionService service;

    @POST
    @Path("/redeem/{profileId}/{bonusType}/{numberOfGifts}")
    @Transactional
    public Uni<Response> redeemDailyGifts(@PathParam("profileId") Long profileId,
                                          @PathParam("bonusType") BonusType bonusType,
                                          @PathParam("numberOfGifts") Integer numberOfPromotions) {
        return Uni.createFrom()
                .item(profileId)
                .onItem()
                .invoke(() -> service.profileRedeemDailyGifts(profileId, bonusType, numberOfPromotions))
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .onFailure()
                .recoverWithItem(Response.status(Response.Status.BAD_GATEWAY)
                        .entity("Couldn't save new entry on Promotion table")
                        .build());
    }

    @GET
    @Path("{profileId}/{status}/")
    public Uni<Response> getPromotionsByProfileIdAndStatus(@PathParam("profileId") Long profileId,
                                                           @PathParam("status") Status status) {
        return service.findPromotionsByProfileIdAndStatus(profileId, status)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build);
    }

    @POST
    @Transactional
    public Uni<Response> create(Promotion promotion) {
        return Uni.createFrom()
                .item(promotion)
                .onItem()
                .invoke(() -> service.save(promotion))
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .onFailure()
                .recoverWithItem(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Promotion already exists in database")
                        .build());
    }

    @GET
    @Path("{id}")
    public Uni<Response> getPromotion(@PathParam("id") Long id) {
        return service.findPromotionById(id)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build);
    }
}
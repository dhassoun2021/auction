package com.david.auction.rest;



import com.david.auction.dto.AuctionHouseRequest;
import com.david.auction.dto.AuctionRequest;

import com.david.auction.handler.AuctionsHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;


@Path("/b2b")
public class AuctionResource {

    private static final String HEADER_USER = "USER-ID";


    private AuctionsHandler auctionsHandler;

    public AuctionResource(AuctionsHandler auctionsHandler) {
        this.auctionsHandler = auctionsHandler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auctions-house")
    public Response createAuctionHouse(@Valid AuctionHouseRequest auctionHouseRequest) {
        return auctionsHandler.createAuctionHouse(auctionHouseRequest);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auctions-house")
    public Response getAuctionsHouse() {
        return auctionsHandler.getAllAuctionHouse();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auctions-house/{idAuctionHouse}/auctions")
    public Response createAuction(@PathParam("idAuctionHouse") String idAuctionHouse, AuctionRequest auctionRequest) {
        if (auctionRequest.getStartingTime().isBefore(LocalDateTime.now())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Starting time should be after date of day").build();
        }
        if (auctionRequest.getEndingTime().isBefore(auctionRequest.getStartingTime())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Ending time should be after starting time").build();
        }
        return auctionsHandler.createAuction(idAuctionHouse, auctionRequest);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auctions-house/{idAuctionHouse}/auctions")
    public Response getAuctions(@PathParam("idAuctionHouse") String idAuctionHouse) {
        return auctionsHandler.findAuctions(idAuctionHouse);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auctions-house/{idAuctionHouse}/auctions/{idAuction}")
    public Response bidAuction (@PathParam("idAuction")String idAuction, AuctionRequest auctionRequest, @Context HttpServletRequest httpServletRequest) {
        String idUser = httpServletRequest.getHeader(HEADER_USER);
        if (!isUser(idUser)) {
           return Response.status(Response.Status.UNAUTHORIZED).build();
       }
        return auctionsHandler.bidAuction(idAuction,idUser,auctionRequest);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auctions/{idAuction}/user")
    public Response getWinner(@PathParam("idAuction") String idAuction) {
        return auctionsHandler.showWinner(idAuction);
    }

    private boolean isUser (String idUser) {
        if (idUser == null || idUser.trim().length() == 0) {
            return false;
        }
        return true;
    }
}



package com.david.auction.handler;

import com.david.auction.dto.*;
import com.david.auction.exceptions.AuctionException;
import com.david.auction.exceptions.ErrorType;
import com.david.auction.model.AuctionHouse;
import com.david.auction.model.User;
import com.david.auction.services.IAuctionService;
import com.david.auction.services.IUserService;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class AuctionsHandler {

    private IUserService userService;

    private IAuctionService auctionService;
    public AuctionsHandler (IAuctionService auctionService, IUserService userService) {
        this.auctionService = auctionService;
        this.userService = userService;
    }

    public Response createAuctionHouse (AuctionHouseRequest auctionHouseRequest) {
        try {
            AuctionHouseResponse response = auctionService.createAuctionHouse(auctionHouseRequest);
            return Response.ok(response).build();
        } catch (AuctionException ex) {
            switch (ex.getErrorType()) {
                case AUCTION_HOUSE_EXISTS:
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_HOUSE_EXISTS));
                    return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();
            }
            return Response.serverError().build();
        }

    }

    public Response createAuction (String idAuctionHouse, AuctionRequest auctionRequest) {
        try {
            AuctionResponse response = auctionService.createAuction(idAuctionHouse, auctionRequest);
            return Response.ok(response).build();
        } catch (AuctionException ex) {
            switch (ex.getErrorType()) {
                case AUCTION_EXISTS:
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_EXISTS));
                    return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();
                case AUCTION_HOUSE_NOT_FOUND:
                    return Response.status(Response.Status.FORBIDDEN).build();

            }
            return Response.serverError().build();
        }
    }

        public Response findAuctions (String idAuctionHouse) {
            try {
                AuctionsResponse response = auctionService.findAuctions(idAuctionHouse);
                return Response.ok(response).build();
            } catch (AuctionException ex) {
                switch (ex.getErrorType()) {
                    case AUCTION_NOT_FOUND:
                        ErrorResponse errorResponse = new ErrorResponse();
                        errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_NOT_FOUND));
                        return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();

                }
                return Response.serverError().build();
            }

    }

    public Response getAllAuctionHouse () {
        List<AuctionHouse> auctionHouseList = auctionService.getAllAuctionsHouses();
        return Response.ok(new AuctionHouseListResponse(auctionHouseList)).build();
    }


    public Response bidAuction (String idAuction, String idUSer, AuctionRequest auctionRequest) {
        BidRequest bidRequest = new BidRequest(auctionRequest.getPrice(), idAuction, idUSer);
        Optional<User> optionalUser = userService.findUser(idUSer);
        if (optionalUser.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            BidResponse response = auctionService.bidAuction(idAuction, bidRequest, optionalUser.get());
            return Response.ok(response).build();
        } catch (AuctionException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            switch (ex.getErrorType()) {
                case AUCTION_NOT_RUNNING:
                    errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_NOT_RUNNING));
                    return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();

                case AUCTION_NOT_ACCEPTED:
                    errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_NOT_ACCEPTED));
                    return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();
            }

        }
        return Response.serverError().build();
    }

    public Response showWinner (String idAuction) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            Optional<User> optionalUser = auctionService.showWinnerAuction(idAuction);
            UserResponse response = new UserResponse();
            if (optionalUser.isPresent()) {
                response.setUser(optionalUser.get());
            }
            return Response.ok(response).build();

        } catch (AuctionException ex) {
            switch (ex.getErrorType()) {
                case AUCTION_NOT_FOUND:
                    errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_NOT_FOUND));
                    return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                case AUCTION_NOT_TERMINATED:
                    errorResponse.getErrors().add(new ErrorCode(ErrorType.AUCTION_NOT_TERMINATED));
                    return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();
            }
        }
        return Response.serverError().build();
    }


}

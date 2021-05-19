package com.david.auction.adapters;


import com.david.auction.dto.AuctionRequest;
import com.david.auction.dto.AuctionResponse;
import com.david.auction.model.Auction;

import com.david.auction.model.AuctionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuctionAdapter {

    public static AuctionResponse modelToResponse (Auction auction) {
        AuctionResponse auctionResponse = new AuctionResponse();
        auctionResponse.setIdAuction(auction.getId());
        auctionResponse.setName(auction.getName());
        auctionResponse.setAuctionHouseName(auction.getAuctionHouse().getName());
       /* LocalDateTime now = LocalDateTime.now();
        if (auction.getStartingTime().isAfter(now)) {
            auctionResponse.setAuctionStatus(AuctionStatus.NOT_STARTED);
        } else if (auction.getEndingTime().isAfter(now)) {
            auctionResponse.setAuctionStatus(AuctionStatus.RUNNING);
        }*/
        AuctionStatus auctionStatus = auction.computeStatus();
        auctionResponse.setAuctionStatus(auctionStatus);
        auctionResponse.setPrice(auction.getPrice());
        return auctionResponse;
    }

    public static List<AuctionResponse> modelsToResponses (List<Auction> auctions) {
      return auctions.stream().map(a->modelToResponse(a)).collect(Collectors.toList());
    }

    public static Auction requestToModel (AuctionRequest auctionRequest) {
        String id = UUID.randomUUID().toString();
        Auction auction = new Auction(id,auctionRequest.getName(),auctionRequest.getStartingTime(),auctionRequest.getEndingTime(),auctionRequest.getPrice());
        auction.setDescription(auctionRequest.getDescription());
        return auction;
    }
}

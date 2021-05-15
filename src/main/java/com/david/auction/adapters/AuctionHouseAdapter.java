package com.david.auction.adapters;

import com.david.auction.dto.AuctionHouseRequest;
import com.david.auction.dto.AuctionHouseResponse;
import com.david.auction.model.AuctionHouse;

public final class AuctionHouseAdapter {


    public static AuctionHouseResponse modelToResponse (AuctionHouse auctionHouse) {
        AuctionHouseResponse auctionHouseResponse = new AuctionHouseResponse(auctionHouse.getId(),auctionHouse.getName());
        return auctionHouseResponse;
    }

    public static AuctionHouse requestToModel (AuctionHouseRequest auctionHouseRequest) {
        AuctionHouse auctionHouse = new AuctionHouse(auctionHouseRequest.getName());
        return auctionHouse;
    }
}

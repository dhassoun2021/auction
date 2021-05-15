package com.david.auction.dto;

import com.david.auction.model.AuctionHouse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouseListResponse {

    @JsonProperty("auctionsHouses")
    private  List<AuctionHouse> auctionHouses = new ArrayList<>();

    public AuctionHouseListResponse(List<AuctionHouse> auctionHouses) {
        this.auctionHouses = auctionHouses;
    }

    public List<AuctionHouse> getAuctionHouses() {
        return auctionHouses;
    }
}

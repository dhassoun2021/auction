package com.david.auction.dto;

import java.util.List;

public class AuctionsResponse {

    private List<AuctionResponse> auctionsResponse;

    public List<AuctionResponse> getAuctionsResponse() {
        return auctionsResponse;
    }

    public void setAuctionsResponse(List<AuctionResponse> auctionsResponse) {
        this.auctionsResponse = auctionsResponse;
    }
}

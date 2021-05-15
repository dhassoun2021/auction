package com.david.auction.adapters;

import com.david.auction.dto.BidResponse;
import com.david.auction.model.Bid;

public final class BidAdapter {

    public static BidResponse modelToResponse (Bid bid) {
        BidResponse response = new BidResponse();
        response.setId(bid.getId());
        response.setNewPrice(bid.getAuction().getPrice());
        response.setIdUser(bid.getUser().getId());
        return response;
    }
}

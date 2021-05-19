package com.david.auction.dao.memory;

import com.david.auction.model.Auction;
import com.david.auction.model.AuctionHouse;
import com.david.auction.model.Bid;

import java.util.HashMap;
import java.util.Map;

public interface Datas {

    public static final Map<String, AuctionHouse> AUCTION_HOUSE = new HashMap<>();
    public static final Map<String, Auction> AUCTION = new HashMap<>();
    public static final Map<String, Bid> BID = new HashMap<>();
}

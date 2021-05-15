package com.david.auction.dao;

import com.david.auction.model.Bid;

import java.util.List;


public interface IBidDao {

   Bid create (Bid bid);

   List<Bid> findBidsByAuction (String idAuction);
}

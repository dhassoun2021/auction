package com.david.auction.dao;

import com.david.auction.model.Auction;

import java.util.List;
import java.util.Optional;


public interface IAuctionDao {

     Auction saveOrUpdateAuction (Auction auction) ;

     List<Auction> findAuctions (String auctionHouseName) ;

     boolean deleteAuction (String idAuction) ;

     Optional<Auction> findByName (String auctionName);

     Optional <Auction> findById (String idAuction);

     Auction updatePrice (Auction auction, float price);
}

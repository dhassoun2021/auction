package com.david.auction.dao;

import com.david.auction.model.AuctionHouse;

import java.util.List;
import java.util.Optional;

public interface IAuctionHouseDao {

    AuctionHouse createAuctionHouse (AuctionHouse auctionHouse);

    boolean deleteAuctionHouse (String idActionHouse);

    List<AuctionHouse> getAllAuctionsHouses ();

    Optional<AuctionHouse> findByName (String name);

    Optional <AuctionHouse> findById (String idAuctionHouse);
}

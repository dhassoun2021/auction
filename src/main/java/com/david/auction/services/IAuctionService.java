package com.david.auction.services;

import com.david.auction.dto.*;
import com.david.auction.exceptions.AuctionException;
import com.david.auction.model.AuctionHouse;
import com.david.auction.model.User;

import java.util.List;
import java.util.Optional;

public interface IAuctionService {

    AuctionHouseResponse createAuctionHouse (AuctionHouseRequest auctionHouseRequest) throws AuctionException;

    List<AuctionHouse> getAllAuctionsHouses () ;

    void deleteAuctionHouse (String idAuctionHouse) throws AuctionException;

    AuctionResponse createAuction(String idAuctionHouse, AuctionRequest auctionRequest) throws AuctionException ;

    void deleteAuction(String idAuction) throws AuctionException ;

    AuctionsResponse findAuctions (String idAuctionHouse) throws AuctionException ;

    Optional<User> showWinnerAuction (String idAuction) throws AuctionException;
;
    BidResponse bidAuction (String idAuction,BidRequest bidRequest, User user) throws AuctionException;
}

package com.david.auction.services;

import com.david.auction.adapters.AuctionAdapter;
import com.david.auction.adapters.AuctionHouseAdapter;
import com.david.auction.adapters.BidAdapter;
import com.david.auction.dao.IAuctionDao;
import com.david.auction.dao.IAuctionHouseDao;
import com.david.auction.dao.IBidDao;
import com.david.auction.dto.*;
import com.david.auction.exceptions.AuctionException;
import com.david.auction.exceptions.ErrorType;
import com.david.auction.model.*;

import java.util.List;
import java.util.Optional;

public class AuctionService implements IAuctionService{

    private IAuctionHouseDao auctionHouseDao;
    private IAuctionDao auctionDao;
    private IBidDao bidDao;

    public AuctionService(IAuctionHouseDao auctionHouseDao,IAuctionDao auctionDao, IBidDao bidDao) {
        this.auctionHouseDao = auctionHouseDao;
        this.auctionDao = auctionDao;
        this.bidDao = bidDao;
    }

    @Override
    public AuctionHouseResponse createAuctionHouse(AuctionHouseRequest auctionHouseRequest) throws AuctionException {
        if (isAuctionHouseWithSameNameExists(auctionHouseRequest.getName())) {
            throw new AuctionException(ErrorType.AUCTION_HOUSE_EXISTS);
        }
        AuctionHouse auctionHouse = AuctionHouseAdapter.requestToModel(auctionHouseRequest);
        auctionHouse = auctionHouseDao.createAuctionHouse(auctionHouse);
        return AuctionHouseAdapter.modelToResponse(auctionHouse);
    }

    private boolean isAuctionHouseWithSameNameExists (String name) {
        return auctionHouseDao.findByName(name).isPresent();
    }

    private boolean isAuctionWithSameNameExists (String name) {
        return auctionDao.findByName(name).isPresent();
    }

    @Override
    public List<AuctionHouse> getAllAuctionsHouses() {
        return auctionHouseDao.getAllAuctionsHouses();
    }

    @Override
    public void deleteAuctionHouse(String idAuctionHouse) throws AuctionException {
       boolean deleted = auctionHouseDao.deleteAuctionHouse(idAuctionHouse);
       if (!deleted) {
           throw new AuctionException(ErrorType.AUCTION_HOUSE_NOT_FOUND);
       }
    }

    @Override
    public AuctionResponse createAuction(String idAuctionHouse, AuctionRequest auctionRequest) throws AuctionException {
       Optional <AuctionHouse> optionalAuctionHouse = auctionHouseDao.findById(idAuctionHouse);
       if (optionalAuctionHouse.isEmpty()) {
           throw new AuctionException(ErrorType.AUCTION_HOUSE_NOT_FOUND);
       }
        if (isAuctionWithSameNameExists(auctionRequest.getName())) {
            throw new AuctionException(ErrorType.AUCTION_EXISTS);
        }
        Auction auction = AuctionAdapter.requestToModel(auctionRequest);
        auction.setAuctionHouse(optionalAuctionHouse.get());
        auction = auctionDao.saveOrUpdateAuction(auction);
        return AuctionAdapter.modelToResponse(auction);
    }

    @Override
    public void deleteAuction(String idAuction) throws AuctionException {
       boolean isDeleted = auctionDao.deleteAuction(idAuction);
       if (!isDeleted) {
           throw new AuctionException(ErrorType.AUCTION_NOT_FOUND);
       }
    }

    @Override
    public AuctionsResponse findAuctions (String idAuctionHouse) throws AuctionException {
      List <Auction> auctions =  auctionDao.findAuctions(idAuctionHouse);
      if (auctions == null || auctions.isEmpty()) {
          throw new AuctionException(ErrorType.AUCTION_NOT_FOUND);
      }
      AuctionsResponse auctionsResponse = new AuctionsResponse();
      auctionsResponse.setAuctionsResponse(AuctionAdapter.modelsToResponses(auctions));
      return  auctionsResponse;
    }

    @Override
    public BidResponse bidAuction (String idAuction,BidRequest bidRequest, User user) throws AuctionException {
       Optional <Auction> optionalAuction = auctionDao.findById(idAuction);
       if (optionalAuction.isEmpty()) {
           throw new AuctionException(ErrorType.AUCTION_NOT_FOUND);
       }
       Auction auction = optionalAuction.get();
       if (!isAuctionRunning(auction)) {
           throw new AuctionException(ErrorType.AUCTION_NOT_RUNNING);
       }
       if (bidRequest.getPrice() <= auction.getPrice()) {
           throw new AuctionException(ErrorType.AUCTION_NOT_ACCEPTED);
       }

       //update price auction
        updateAuctionPrice(bidRequest.getPrice(), auction);

        //create Bid
       Bid bid = createBid(bidRequest, user, auction);
       return BidAdapter.modelToResponse(bid);

    }

    @Override
    public Optional<User> showWinnerAuction (String idAuction) throws AuctionException{
        Optional<Auction>  optionalAuction = auctionDao.findById(idAuction);
        if (optionalAuction.isEmpty()) {
           throw new AuctionException(ErrorType.AUCTION_NOT_FOUND);
       }
       Auction auction = optionalAuction.get();
       if (!isAuctionTerminated(auction)) {
           throw new AuctionException(ErrorType.AUCTION_NOT_TERMINATED);
       }
       List <Bid> bids = bidDao.findBidsByAuction(idAuction);
       if (bids == null || bids.isEmpty()) {
           return Optional.empty();
       }
       return Optional.of(bids.get(0).getUser());
    }

    private void updateAuctionPrice(float newPrice, Auction auction) {
        auction.setPrice(newPrice);
        auctionDao.saveOrUpdateAuction(auction);
    }

    private Bid createBid(BidRequest bidRequest, User user, Auction auction) {
        Bid bid = new Bid(bidRequest.getPrice(), auction, user);
        bid = bidDao.create(bid);
        return bid;
    }

    private boolean isAuctionRunning(Auction auction) {
      AuctionStatus status = auction.computeStatus();
      return (status == AuctionStatus.RUNNING);
    }

    private boolean isAuctionTerminated(Auction auction) {
        AuctionStatus status = auction.computeStatus();
        return (status == AuctionStatus.TERMINATED);
    }


}

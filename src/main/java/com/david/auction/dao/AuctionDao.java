package com.david.auction.dao;

import com.david.auction.model.Auction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.david.auction.dao.memory.Datas.AUCTION;

public class AuctionDao implements IAuctionDao{


    public Auction saveOrUpdateAuction (Auction auction) {
        if (auction.getId() == null) {
            String id = UUID.randomUUID().toString();
            auction.setId(id);
            AUCTION.put(id,auction);
        } else {
            Auction oldAuction = AUCTION.get(auction.getId());
            oldAuction.setPrice(auction.getPrice());
        }
        return auction;
    }

    public List<Auction> findAuctions (String idAuctionHouseName) {
      return AUCTION.values().stream().filter(a->a.getAuctionHouse() != null && idAuctionHouseName.equals(a.getAuctionHouse().getId())).collect(Collectors.toList());
    }

    public Optional <Auction> findByName (String auctionName) {
       return AUCTION.values().stream().filter(a->auctionName.equals(a.getName())).findFirst();
    }

    public Optional <Auction> findById (String idAuction) {
        return AUCTION.values().stream().filter(a->idAuction.equals(a.getId())).findFirst();
    }

    public Auction updatePrice (Auction auction, float price) {
        auction.setPrice(price);
        AUCTION.put(auction.getId(),auction);
        return auction;
    }

    public boolean deleteAuction (String idAuction) {
       Auction auction = AUCTION.get(idAuction);
       if (auction == null) {
           return false;
       }
       auction.setDeleted(true);
       return true;
    }
}

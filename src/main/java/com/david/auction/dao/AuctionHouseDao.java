package com.david.auction.dao;

import static com.david.auction.dao.memory.Datas.AUCTION_HOUSE;
import com.david.auction.model.AuctionHouse;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuctionHouseDao implements IAuctionHouseDao {

    private static AuctionHouseDao _instance = new AuctionHouseDao();

    private AuctionHouseDao () {

    }

    public AuctionHouse createAuctionHouse (AuctionHouse auctionHouse) {
        String id = UUID.randomUUID().toString();
        auctionHouse.setId(id);
        AUCTION_HOUSE.put(id,auctionHouse);
        return auctionHouse;
    }

    public List<AuctionHouse> getAllAuctionsHouses () {
           return AUCTION_HOUSE.values().stream().collect(Collectors.toList());
    }

    public boolean deleteAuctionHouse (String idActionHouse) {
        AuctionHouse auctionHouse = AUCTION_HOUSE.remove(idActionHouse);
        return (auctionHouse != null);
    }

    public Optional <AuctionHouse> findByName (String name) {
       return AUCTION_HOUSE.values().stream().filter(a->a.getName().equals(name)).findFirst();
    }

    public Optional <AuctionHouse> findById (String idAuctionHouse) {
       AuctionHouse auctionHouse = AUCTION_HOUSE.get(idAuctionHouse);
       if (auctionHouse == null) {
           return Optional.empty();
       }
       return Optional.of(auctionHouse);
    }

    public static AuctionHouseDao getInstance() {
        return _instance;
    }
}

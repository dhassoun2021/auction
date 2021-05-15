package com.david.auction.dao;

import com.david.auction.model.Bid;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.david.auction.dao.memory.Datas.BID;


public class BidDao implements IBidDao {
    public Bid create (Bid bid) {
        String id = UUID.randomUUID().toString();
        bid.setId(id);
        BID.put(id,bid);
        return bid;
    }

    public List<Bid> findBidsByAuction (String idAuction) {
        List <Bid> bids = BID.values().stream().filter(b->idAuction.equals(b.getAuction().getId())).collect(Collectors.toList());
        bids.sort(Comparator.comparing(Bid::getCreatedTime).reversed());
        return bids;
    }
}

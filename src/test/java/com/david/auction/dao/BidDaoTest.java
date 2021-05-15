package com.david.auction.dao;

import com.david.auction.model.Auction;
import com.david.auction.model.Bid;
import com.david.auction.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class BidDaoTest {

    private static IBidDao bidDao = new BidDao();

    public static Bid buildBid (LocalDateTime startingTime, LocalDateTime endingTime, float price, float newPrice, String idAuction) {
        User user = new User("1","DUPONT");
        Auction auction = new Auction("auction",startingTime,endingTime, price);
        auction.setId(idAuction);
        Bid bid = new Bid(newPrice,auction,user) ;
        return bid;
    }

    public static Bid buildBid (Auction auction, float newPrice, User user) {
        Bid bid = new Bid(newPrice,auction,user) ;
        return bid;
    }



    @Test
    public void saveBid() {
        Auction auction = AuctionDaoTest.buildAuction("idAuction","auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2021,5,4,12,34),10f);
        User user = new User("1","DUPONT");
        Bid bid = buildBid(auction,20f,user);
        bid = bidDao.create(bid);
        Assert.assertNotNull(bid);
        Assert.assertNotNull(bid.getId());
    }

    @Test
    public void findBidByAuction () throws Exception {
        String idAuction = "idAuction";
        Auction auction = AuctionDaoTest.buildAuction(idAuction,"auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2021,5,4,12,34),10f);
        auction.setId(idAuction);
        User user = new User("1","DUPONT");
        Bid bid = buildBid(auction,20f,user);
        bid = bidDao.create(bid);
        Thread.sleep(1000);
        Bid bid2 = buildBid(auction,20f,user);
        bid2 = bidDao.create(bid2);
        Thread.sleep(1000);
        Bid bid3 = buildBid(auction,20f,user);
        bid3 = bidDao.create(bid3);
        List<Bid> bids = bidDao.findBidsByAuction(idAuction);
        Assert.assertNotNull(bids);
        Assert.assertEquals(3,bids.size());
        Assert.assertEquals(bid3,bids.get(0));
    }

}

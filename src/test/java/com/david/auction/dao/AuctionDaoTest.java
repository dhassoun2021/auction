package com.david.auction.dao;

import com.david.auction.dao.memory.Datas;
import com.david.auction.model.Auction;
import com.david.auction.model.AuctionHouse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AuctionDaoTest {

    private static IAuctionDao auctionDao = new AuctionDao();

    public static Auction buildAuction () {
        Auction auction = new Auction("auction", LocalDateTime.of(2021,04,28,14,20),LocalDateTime.of(2023,05,28,14,20),50);
        auction.setDescription("description");
        AuctionHouse auctionHouse = new AuctionHouse("house");
        auctionHouse.setId("idAuctionHouse");
        auction.setAuctionHouse(auctionHouse);
        return auction;
    }

    public static Auction buildAuction (String idAuction,String name, LocalDateTime startingTime,LocalDateTime endingTime, float startPrice) {
        Auction auction = new Auction(name, startingTime,endingTime,startPrice);
        auction.setDescription("description");
        AuctionHouse auctionHouse = new AuctionHouse("house");
        auctionHouse.setId(idAuction);
        auction.setAuctionHouse(auctionHouse);
        return auction;
    }

    @Test
    public void createAuction() {
        Auction auction = buildAuction();
        auction = auctionDao.saveOrUpdateAuction(auction);
        Assert.assertNotNull(auction.getId());
    }

    @Test
    public void findAuctionByIdAuctionHouseShoudReturnOneResultWhenAuctionHouseExists() {
       List<Auction> auctions = auctionDao.findAuctions("idAuctionHouse");
       Assert.assertNotNull(auctions);
       Assert.assertEquals(1,auctions.size());
       Assert.assertEquals("idAuction", auctions.get(0).getId());
    }

    @Test
    public void findAuctionByIdAuctionHouseShoudReturnEmptyResultWhenAuctionHouseNotExists() {
        List<Auction> auctions = auctionDao.findAuctions("idAuctionHouse2");
        Assert.assertNotNull(auctions);
        Assert.assertEquals(0,auctions.size());
    }

    @Test
    public void deleteAuctionWhenItExists() {
       boolean deleted = auctionDao.deleteAuction("idAuction");
       Assert.assertTrue(deleted);
    }

    @Test
    public void deleteAuctionWhenItDoesNotExists() {
        boolean deleted = auctionDao.deleteAuction("idAuction2");
        Assert.assertFalse(deleted);
    }

    @Test
    public void findAuctionByNameShouldReturnsOneElementWhenItExists() {
       Optional<Auction> optionalAuction = auctionDao.findByName("auction");
       Assert.assertTrue(optionalAuction.isPresent());
    }

    @Test
    public void findAuctionByNameShouldReturnsNoElementWhenItDoesNotExists() {
        Optional<Auction> optionalAuction = auctionDao.findByName("auction2");
        Assert.assertFalse(optionalAuction.isPresent());
    }

    @After
    public void clearDatas () {
        Datas.AUCTION.clear();
    }

    @Before
    public void init () {
        Auction auction = buildAuction();
        String id = "idAuction";
        auction.setId(id);
        Datas.AUCTION.put(id,auction);
    }
}

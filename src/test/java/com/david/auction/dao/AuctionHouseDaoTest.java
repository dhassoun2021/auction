package com.david.auction.dao;

import com.david.auction.dao.memory.Datas;
import com.david.auction.model.AuctionHouse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;


import java.util.List;

public class AuctionHouseDaoTest {

    private static IAuctionHouseDao auctionHouseDao = AuctionHouseDao.getInstance();

    @Test
    public void createAuctionHouse () {
        AuctionHouse auctionHouse = buildAndCreateAuctionHouse();
        Assert.assertNotNull(auctionHouse.getId());
    }

    @Test
    public void getAllAuctionsHousesShouldReturnsEmptyListWhenDatabaseIsEmpty () {
       List <AuctionHouse> auctionHouses =  auctionHouseDao.getAllAuctionsHouses();
       Assert.assertTrue(auctionHouses.isEmpty());
    }

    @Test
    public void getAllAuctionsHousesShouldReturnOneAuctionHouseWhenDatabaseContainsOneElement () {
        buildAndCreateAuctionHouse();
        List <AuctionHouse> auctionHouses =  auctionHouseDao.getAllAuctionsHouses();
        Assert.assertEquals(1,auctionHouses.size());
    }

    @Test
    public void deleteActionHouseShouldReturnFalseWhenActionHouseNotPresent () {
       boolean result = auctionHouseDao.deleteAuctionHouse("id1");
       Assert.assertEquals(false,result);
    }

    @Test
    public void deleteActionHouseShouldReturnTrueWhenActionHouseIsPresent () {
        AuctionHouse auctionHouse = buildAndCreateAuctionHouse();
        boolean result = auctionHouseDao.deleteAuctionHouse(auctionHouse.getId());
        Assert.assertEquals(true,result);
    }

    @After
    public void clearDatas () {
        Datas.AUCTION_HOUSE.clear();
    }

    public static AuctionHouse buildAndCreateAuctionHouse () {
        AuctionHouse auctionHouse = new AuctionHouse("house");
        return auctionHouseDao.createAuctionHouse(auctionHouse);
    }


}

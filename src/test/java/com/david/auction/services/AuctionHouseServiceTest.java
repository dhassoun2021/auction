package com.david.auction.services;

import com.david.auction.dao.AuctionHouseDaoTest;
import com.david.auction.dao.IAuctionDao;
import com.david.auction.dao.IAuctionHouseDao;
import com.david.auction.dto.AuctionHouseRequest;
import com.david.auction.dto.AuctionHouseResponse;
import com.david.auction.exceptions.AuctionException;
import com.david.auction.exceptions.ErrorType;
import com.david.auction.model.AuctionHouse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.ClassPathLoader;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AuctionHouseServiceTest {

    @Mock
    private IAuctionHouseDao auctionHouseDao;

    @Mock
    private IAuctionDao auctionDao;

    private static IAuctionService auctionService ;
    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        auctionService = new AuctionService(auctionHouseDao,auctionDao,null) ;

    }

    @Test
    public void shouldCreateAuctionHouseWhenItDoesNotExist () throws Exception {
        AuctionHouse auctionHouse = AuctionHouseDaoTest.buildAndCreateAuctionHouse();
        auctionHouse.setId("1");
        Mockito.when(auctionHouseDao.createAuctionHouse(Mockito.any(AuctionHouse.class))).thenReturn(auctionHouse);
        Mockito.when(auctionHouseDao.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        AuctionHouseRequest request = new AuctionHouseRequest("toto");
        AuctionHouseResponse auctionHouseResponse = auctionService.createAuctionHouse(request);
        Assert.assertNotNull(auctionHouseResponse);
        Assert.assertNotNull(auctionHouseResponse.getId());
        Assert.assertEquals("1",auctionHouse.getId());
    }

    @Test
    public void createAuctionHouseShouldThrowExceptionWhenItAlreadyExist ()  {
        AuctionHouse auctionHouse = AuctionHouseDaoTest.buildAndCreateAuctionHouse();
        auctionHouse.setId("1");
        Mockito.when(auctionHouseDao.findByName(Mockito.anyString())).thenReturn(Optional.of(auctionHouse));
        Mockito.when(auctionHouseDao.createAuctionHouse(Mockito.any(AuctionHouse.class))).thenReturn(auctionHouse);
        AuctionHouseRequest request = new AuctionHouseRequest("toto");
        try {
            AuctionHouseResponse auctionHouseResponse = auctionService.createAuctionHouse(request);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_HOUSE_EXISTS,ex.getErrorType());
        }


    }

    @Test
    public void deleteExistingAuctionHouse() throws Exception {
        Mockito.when(auctionHouseDao.deleteAuctionHouse(Mockito.anyString())).thenReturn(true);
        auctionService.deleteAuctionHouse("1");
    }

    @Test
    public void deleteNotExistingAuctionHouseShouldReturnsException()  {
        Mockito.when(auctionHouseDao.deleteAuctionHouse(Mockito.anyString())).thenReturn(false);
        try {
            auctionService.deleteAuctionHouse("1");
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_HOUSE_NOT_FOUND,ex.getErrorType());
        }
    }


    @Test
    public void getAllAuctionsHouses() throws Exception {
        AuctionHouse auctionHouse = AuctionHouseDaoTest.buildAndCreateAuctionHouse();
        auctionHouse.setId("1");
        Mockito.when(auctionHouseDao.getAllAuctionsHouses()).thenReturn(Arrays.asList(auctionHouse));
        List<AuctionHouse> auctionHouses = auctionService.getAllAuctionsHouses();
        Assert.assertNotNull(auctionHouses);
        Assert.assertFalse(auctionHouses.isEmpty());
        AuctionHouse newAuctionHouse = auctionHouses.get(0);
        Assert.assertEquals(newAuctionHouse,auctionHouse);

    }

}

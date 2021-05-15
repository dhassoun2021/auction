package com.david.auction.services;

import com.david.auction.dao.*;
import com.david.auction.dto.*;
import com.david.auction.exceptions.AuctionException;
import com.david.auction.exceptions.ErrorType;
import com.david.auction.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class AuctionServiceTest {

    @Mock
    private IAuctionHouseDao auctionHouseDao;

    @Mock
    private IAuctionDao auctionDao;

    @Mock
    private IBidDao bidDao;

    private static IAuctionService auctionService ;
    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        auctionService = new AuctionService(auctionHouseDao,auctionDao,bidDao) ;

    }

    @Test
    public void createAuctionRunningWhenItDoesNotExist () throws Exception {
       Auction auction = AuctionDaoTest.buildAuction();
       auction.setId("idAuction");
       Mockito.when(auctionHouseDao.findById(Mockito.anyString())).thenReturn(Optional.of(new AuctionHouse("auctionHouse")));
       Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
       Mockito.when(auctionDao.findByName(Mockito.anyString())).thenReturn(Optional.empty());
       AuctionRequest auctionRequest = new AuctionRequest("auction", LocalDateTime.now(),LocalDateTime.now(),1f);
       AuctionResponse auctionResponse = auctionService.createAuction("idAuctionHouse",auctionRequest);
       Assert.assertNotNull(auctionResponse);
       Assert.assertEquals("idAuction",auctionResponse.getIdAuction());
       Assert.assertEquals(AuctionStatus.RUNNING,auctionResponse.getAuctionStatus());
    }

    @Test
    public void createAuctionNotStartedWhenItDoesNotExist () throws Exception {
        Auction auction = AuctionDaoTest.buildAuction();
        LocalDateTime startingTime = auction.getStartingTime();
        LocalDateTime endingTime = auction.getEndingTime();
        startingTime = startingTime.withYear(2022);
        endingTime = endingTime.withYear(2023);
        auction.setStartingTime(startingTime);
        auction.setEndingTime(endingTime);
        auction.setId("idAuction");
        Mockito.when(auctionHouseDao.findById(Mockito.anyString())).thenReturn(Optional.of(new AuctionHouse("auctionHouse")));
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Mockito.when(auctionDao.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        AuctionRequest auctionRequest = new AuctionRequest("auction", LocalDateTime.now(),LocalDateTime.now(),1f);
        AuctionResponse auctionResponse = auctionService.createAuction("idAuctionHouse",auctionRequest);
        Assert.assertNotNull(auctionResponse);
        Assert.assertEquals("idAuction",auctionResponse.getIdAuction());
        Assert.assertEquals(AuctionStatus.NOT_STARTED,auctionResponse.getAuctionStatus());
    }

    @Test
    public void createAuctionShouldThrowsExceptionWhenItExists ()  {
        Auction auction = AuctionDaoTest.buildAuction();
        auction.setId("idAuction");
        Mockito.when(auctionHouseDao.findById(Mockito.anyString())).thenReturn(Optional.of(new AuctionHouse("auctionHouse")));
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Mockito.when(auctionDao.findByName(Mockito.anyString())).thenReturn(Optional.of(auction));
        AuctionRequest auctionRequest = new AuctionRequest("auction", LocalDateTime.now(),LocalDateTime.now(),1f);
        try {
            auctionService.createAuction("idAuctionHouse", auctionRequest);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_EXISTS,ex.getErrorType());
        }

    }

    @Test
    public void deleteAuctionShouldThrowExceptionWhenItDoesNotExists ()  {

        Mockito.when(auctionDao.deleteAuction(Mockito.anyString())).thenReturn(false);

        try {
            auctionService.deleteAuction("id");
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_FOUND,ex.getErrorType());
        }

    }

    @Test
    public void deleteAuction() throws Exception {
        Mockito.when(auctionDao.deleteAuction(Mockito.anyString())).thenReturn(true);
        auctionService.deleteAuction("id");
    }

    @Test
    public void findAuctions() throws Exception {
        Auction auction = AuctionDaoTest.buildAuction();
        auction.setId("idAuction");
        Mockito.when(auctionDao.findAuctions(Mockito.anyString())).thenReturn(Arrays.asList(auction));
        AuctionsResponse auctionResponse = auctionService.findAuctions("id");
        Assert.assertNotNull(auctionResponse.getAuctionsResponse());
        Assert.assertFalse(auctionResponse.getAuctionsResponse().isEmpty());
        AuctionResponse response = auctionResponse.getAuctionsResponse().get(0);
        Assert.assertNotNull(response);
        Assert.assertEquals("idAuction",response.getIdAuction());
    }

    @Test
    public void findAuctionsShouldThrowExceptionWhenItDoesNotExists()  {
        Auction auction = AuctionDaoTest.buildAuction();
        auction.setId("idAuction");
        Mockito.when(auctionDao.findAuctions(Mockito.anyString())).thenReturn(new ArrayList<>());
        try {
            AuctionsResponse auctionResponse = auctionService.findAuctions("id");
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_FOUND,ex.getErrorType());
        }

    }

    @Test
    public void bidAuction() throws Exception {
        Auction auction = AuctionDaoTest.buildAuction("idAuction","auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2024,5,4,12,34),10f);
        BidRequest request = new BidRequest(20f,"idAuction","idUser");
        User user = buildUser("idUser","DUPONT");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Bid bid = BidDaoTest.buildBid(auction,20f,user);
        bid.setId("idBid");
        Mockito.when(bidDao.create(Mockito.any(Bid.class))).thenReturn(bid);
        BidResponse response = auctionService.bidAuction("idAuction",request,user);
        Assert.assertNotNull(response);
        Assert.assertEquals("idBid",response.getId());
        Assert.assertTrue(20f == response.getNewPrice());
    }

    @Test
    public void bidAuctionShouldThrowExceptionWhenPriceIsNotGreaterThanCurrentPrice()  {
        Auction auction = AuctionDaoTest.buildAuction("idAuction","auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2024,5,4,12,34),10f);
        BidRequest request = new BidRequest(20f,"idAuction","idUser");
        User user = buildUser("idUser","DUPONT");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Bid bid = BidDaoTest.buildBid(auction,5f,user);
        bid.setId("idBid");
        Mockito.when(bidDao.create(Mockito.any(Bid.class))).thenReturn(bid);
        try {
            BidResponse response = auctionService.bidAuction("idAuction", request, user);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_ACCEPTED,ex.getErrorType());
        }

    }

    @Test
    public void bidAuctionShouldThrowExceptionWhenAuctionIsNotFound()  {
        Auction auction = AuctionDaoTest.buildAuction("idAuction","auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2024,5,4,12,34),10f);
        BidRequest request = new BidRequest(20f,"idAuction","idUser");
        User user = buildUser("idUser","DUPONT");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Bid bid = BidDaoTest.buildBid(auction,5f,user);
        bid.setId("idBid");
        Mockito.when(bidDao.create(Mockito.any(Bid.class))).thenReturn(bid);
        try {
            BidResponse response = auctionService.bidAuction("idAuction", request, user);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_FOUND,ex.getErrorType());
        }

    }

    @Test
    public void bidAuctionShouldThrowExceptionWhenAuctionHasNotYetStarted()  {
        Auction auction = AuctionDaoTest.buildAuction("idAuction","auction",LocalDateTime.of(2022,4,24,12,34),LocalDateTime.of(2024,5,4,12,34),10f);
        BidRequest request = new BidRequest(20f,"idAuction","idUser");
        User user = buildUser("idUser","DUPONT");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Bid bid = BidDaoTest.buildBid(auction,5f,user);
        bid.setId("idBid");
        Mockito.when(bidDao.create(Mockito.any(Bid.class))).thenReturn(bid);
        try {
            BidResponse response = auctionService.bidAuction("idAuction", request, user);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_RUNNING,ex.getErrorType());
        }
    }

    @Test
    public void bidAuctionShouldThrowExceptionWhenAuctionIsTerminated()  {
        Auction auction = AuctionDaoTest.buildAuction("idAuction","auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2021,5,25,12,34),10f);
        BidRequest request = new BidRequest(20f,"idAuction","idUser");
        User user = buildUser("idUser","DUPONT");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(auctionDao.saveOrUpdateAuction(Mockito.any(Auction.class))).thenReturn(auction);
        Bid bid = BidDaoTest.buildBid(auction,5f,user);
        bid.setId("idBid");
        Mockito.when(bidDao.create(Mockito.any(Bid.class))).thenReturn(bid);
        try {
            BidResponse response = auctionService.bidAuction("idAuction", request, user);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_TERMINATED,ex.getErrorType());
        }
    }

    @Test
    public void showWinner () throws Exception {
        User user = buildUser("1","DUPONT");
        User user2 = buildUser("2","DUPUIS");
        User user3 = buildUser("1","MARTIN");
        String idAuction = "idAuction";
        Auction auction = AuctionDaoTest.buildAuction(idAuction,"auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2021,5,1,12,34),10f);
        Bid bid = BidDaoTest.buildBid(auction,20f,user);
        bid.setId("1");
        Bid bid2 = BidDaoTest.buildBid(auction,30f,user2);
        bid2.setId("2");
        Bid bid3 = BidDaoTest.buildBid(auction,40f,user3);
        bid3.setId("3");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(bidDao.findBidsByAuction(Mockito.anyString())).thenReturn(Arrays.asList(bid3,bid2,bid));
        Optional<User> optionalUser = auctionService.showWinnerAuction(idAuction);
        Assert.assertTrue(optionalUser.isPresent());
        Assert.assertEquals(user3,optionalUser.get());
    }

    @Test
    public void showWinnerShouldThrowExceptionWhenAuctionIsNotTerminated ()  {
        User user = buildUser("1","DUPONT");
        User user2 = buildUser("2","DUPUIS");
        User user3 = buildUser("1","MARTIN");
        String idAuction = "idAuction";
        Auction auction = AuctionDaoTest.buildAuction(idAuction,"auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2099,5,1,12,34),10f);
        Bid bid = BidDaoTest.buildBid(auction,20f,user);
        bid.setId("1");
        Bid bid2 = BidDaoTest.buildBid(auction,30f,user2);
        bid2.setId("2");
        Bid bid3 = BidDaoTest.buildBid(auction,40f,user3);
        bid3.setId("3");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(bidDao.findBidsByAuction(Mockito.anyString())).thenReturn(Arrays.asList(bid3,bid2,bid));
        try {
            auctionService.showWinnerAuction(idAuction);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_TERMINATED,ex.getErrorType());
        }

    }

    @Test
    public void showWinnerShouldThrowExceptionWhenAuctionIsNotFound()  {
        User user = buildUser("1","DUPONT");
        User user2 = buildUser("2","DUPUIS");
        User user3 = buildUser("1","MARTIN");
        String idAuction = "idAuction";
        Auction auction = AuctionDaoTest.buildAuction(idAuction,"auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2099,5,1,12,34),10f);
        Bid bid = BidDaoTest.buildBid(auction,20f,user);
        bid.setId("1");
        Bid bid2 = BidDaoTest.buildBid(auction,30f,user2);
        bid2.setId("2");
        Bid bid3 = BidDaoTest.buildBid(auction,40f,user3);
        bid3.setId("3");
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(bidDao.findBidsByAuction(Mockito.anyString())).thenReturn(Arrays.asList(bid3,bid2,bid));
        try {
            auctionService.showWinnerAuction(idAuction);
        } catch (AuctionException ex) {
            Assert.assertEquals(ErrorType.AUCTION_NOT_FOUND,ex.getErrorType());
        }

    }

    @Test
    public void showWinnerShouldReturnEmptyUserWhenNoBid () throws Exception {
        String idAuction = "idAuction";
        Auction auction = AuctionDaoTest.buildAuction(idAuction,"auction",LocalDateTime.of(2021,4,24,12,34),LocalDateTime.of(2021,5,1,12,34),10f);
        Mockito.when(auctionDao.findById(Mockito.anyString())).thenReturn(Optional.of(auction));
        Mockito.when(bidDao.findBidsByAuction(Mockito.anyString())).thenReturn(new ArrayList<>());
        Optional<User> optionalUser = auctionService.showWinnerAuction(idAuction);
        Assert.assertFalse(optionalUser.isPresent());
    }

    private User buildUser (String idUser,String name) {
        User user = new User (idUser, name);
        return user;
    }

    private BidRequest buildBidRequest(float price,String idAuction, String idUser) {
        BidRequest bidRequest = new BidRequest(price,idAuction,idUser);
        return bidRequest;
    }
}

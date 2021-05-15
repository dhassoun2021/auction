package com.david.auction.app;

import com.david.auction.dao.*;
import com.david.auction.handler.AuctionsHandler;
import com.david.auction.rest.AuctionResource;
import com.david.auction.services.AuctionService;
import com.david.auction.services.IUserService;
import com.david.auction.services.UserService;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AuctionApp extends Application<AuctionConfiguration> {

    public static void main(String[] args) throws Exception {
        new AuctionApp().run(args);
    }

    @Override
    public void run(AuctionConfiguration configuration, Environment environment) {
        IAuctionHouseDao auctionHouseDao = AuctionHouseDao.getInstance();
        IAuctionDao auctionDao = new AuctionDao();
        IBidDao bidDao = new BidDao();
        IUserService userService = new UserService();
        AuctionService auctionService = new AuctionService(auctionHouseDao,auctionDao,bidDao);
        AuctionsHandler auctionsHandler = new AuctionsHandler(auctionService,userService);
        AuctionResource auctionResource = new AuctionResource(auctionsHandler);
        environment
                .jersey()
                .register(auctionResource);
    }

    @Override
    public void initialize(Bootstrap<AuctionConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }


}

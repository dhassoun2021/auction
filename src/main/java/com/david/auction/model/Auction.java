package com.david.auction.model;

import java.time.LocalDateTime;

public class Auction {

    private String id;

    private String name;

    private String description;

    private LocalDateTime startingTime;

    private LocalDateTime endingTime;

    private AuctionHouse auctionHouse;

    private boolean deleted;

    private float price;

    public Auction(String name, LocalDateTime startingTime, LocalDateTime endingTime, float price) {
        this.name = name;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public AuctionHouse getAuctionHouse() {
        return auctionHouse;
    }

    public void setAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public AuctionStatus computeStatus() {
        AuctionStatus status = AuctionStatus.NOT_STARTED;
        if (isDeleted()) {
            status = AuctionStatus.DELETED;
        }
        LocalDateTime now = LocalDateTime.now();
        if (startingTime.isAfter(now)) {
            status = AuctionStatus.NOT_STARTED;
        } else if (endingTime.isAfter(now)) {
            status = AuctionStatus.RUNNING;
        } else {
            status = AuctionStatus.TERMINATED;
        }
        return status;
    }


}

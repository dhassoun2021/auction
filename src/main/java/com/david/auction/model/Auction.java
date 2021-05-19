package com.david.auction.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Auction {

    private  String id;

    private final String name;

    private String description;

    private  LocalDateTime startingTime;

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

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return deleted == auction.deleted && Float.compare(auction.price, price) == 0 && Objects.equals(id, auction.id) && Objects.equals(name, auction.name) && Objects.equals(description, auction.description) && Objects.equals(startingTime, auction.startingTime) && Objects.equals(endingTime, auction.endingTime) && Objects.equals(auctionHouse, auction.auctionHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startingTime, endingTime, auctionHouse, deleted, price);
    }
}

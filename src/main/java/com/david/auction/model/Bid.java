package com.david.auction.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {

    private String id;
    private float price;
    private Auction auction;
    private User user;
    private LocalDateTime createdTime;

    public Bid(float price, Auction auction, User user) {
        this.price = price;
        this.auction = auction;
        this.user = user;
        createdTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public Auction getAuction() {
        return auction;
    }

    public User getUser() {
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return Float.compare(bid.price, price) == 0 && Objects.equals(id, bid.id) && Objects.equals(auction, bid.auction) && Objects.equals(user, bid.user) && Objects.equals(createdTime, bid.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, auction, user, createdTime);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", auction=" + auction +
                ", user=" + user +
                ", createdTime=" + createdTime +
                '}';
    }
}

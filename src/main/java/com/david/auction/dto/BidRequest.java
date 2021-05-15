package com.david.auction.dto;

public class BidRequest {

    private float price;
    private String idAuction;
    private String idUser;

    public BidRequest(float price, String idAuction, String idUser) {
        this.price = price;
        this.idAuction = idAuction;
        this.idUser = idUser;
    }

    public float getPrice() {
        return price;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public String getIdUser() {
        return idUser;
    }
}

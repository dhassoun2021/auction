package com.david.auction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuctionHouseRequest {

    @NotEmpty
    @Size(min = 3)
    private final String name;


    public AuctionHouseRequest(@JsonProperty ("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}

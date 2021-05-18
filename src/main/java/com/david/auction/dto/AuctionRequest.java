package com.david.auction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AuctionRequest {

    @JsonProperty("name")
    @NotEmpty
    @Size(min = 3, max=255)
    private  String name;

    @JsonProperty("description")
    private  String description;

    @JsonProperty("startingTime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    //@Pattern(regexp="\\d{4}-(1[012]|0[1-9])-(3[01]|[12]\\d|0[0-9]) (2[0123]|1[0-9]|0[0-9])")
    private  LocalDateTime startingTime;

    @JsonProperty("endingTime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private  LocalDateTime endingTime;

    @JsonProperty("price")
    @NotEmpty
    private  float price;

    public AuctionRequest () {

    }
    public AuctionRequest(String name, LocalDateTime startingTime, LocalDateTime endingTime, float price) {
        this.name = name;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.price = price;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

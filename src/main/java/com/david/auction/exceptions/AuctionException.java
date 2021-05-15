package com.david.auction.exceptions;

public class AuctionException extends Exception {

    private ErrorType errorType;
    public AuctionException (ErrorType errorType) {
        this.errorType =errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }


}

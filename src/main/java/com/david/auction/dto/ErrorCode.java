package com.david.auction.dto;

import com.david.auction.exceptions.ErrorType;

public class ErrorCode {

    private final ErrorType errorCode;
    private final String libelle;

    public ErrorCode(ErrorType errorCode) {
        this.errorCode = errorCode;
        this.libelle = null;
    }

    public ErrorCode(ErrorType errorCode, String libelle) {
        this.errorCode = errorCode;
        this.libelle = libelle;
    }

    public ErrorType getErrorCode() {
        return errorCode;
    }

    public String getLibelle() {
        return libelle;
    }
}

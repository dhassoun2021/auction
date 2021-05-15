package com.david.auction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Set;

public class BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<ErrorCode> errors = new HashSet<>();

    public Set<ErrorCode> getErrors() {
        return errors;
    }

    public void setErrors(Set<ErrorCode> errors) {
        this.errors = errors;
    }
}

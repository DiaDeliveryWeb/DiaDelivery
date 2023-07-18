package com.dia.delivery.common.Exception;

import lombok.Getter;

@Getter
public class DiaErrorCode {

    private int httpStatus;
    private String errorMessage;

    DiaErrorCode(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}

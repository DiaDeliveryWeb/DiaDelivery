package com.dia.delivery.common.Exception;

public class DiaException extends RuntimeException{

    private final DiaErrorCode errorCode;

    public DiaException(DiaErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause, false, false);
        this.errorCode = errorCode;
    }

    public DiaErrorCode getErrorCode() {
        return this.errorCode;
    }
}

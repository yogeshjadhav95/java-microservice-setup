package com.prime.common.enums;

public enum ErrorCode {

    GLOBAL(2), AUTHENTICATION(10), JWT_TOKEN_EXPIRED(498),INTERNAL_SERVER_ERROR(500);

    private final int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

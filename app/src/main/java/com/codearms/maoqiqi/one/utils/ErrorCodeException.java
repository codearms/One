package com.codearms.maoqiqi.one.utils;

public class ErrorCodeException extends Exception {

    private int errorCode;
    private String errorMsg;

    public ErrorCodeException(int errorCode, String errorMsg) {
        super("errorCode:" + errorCode + ",errorMsg:" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
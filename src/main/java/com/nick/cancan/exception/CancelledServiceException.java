package com.nick.cancan.exception;

public class CancelledServiceException extends Exception {

    public CancelledServiceException() { super(); }

    public CancelledServiceException(String message) { super(message); }

    public CancelledServiceException(Throwable throwable) { super(throwable); }

    public CancelledServiceException(String message, Throwable throwable) { super(message, throwable); }

}

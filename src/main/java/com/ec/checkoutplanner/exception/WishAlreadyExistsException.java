package com.ec.checkoutplanner.exception;

public class WishAlreadyExistsException extends RuntimeException {
    public WishAlreadyExistsException(String message) {
        super(message);
    }
}

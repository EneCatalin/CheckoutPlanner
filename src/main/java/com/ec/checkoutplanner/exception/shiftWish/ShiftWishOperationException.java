package com.ec.checkoutplanner.exception.shiftWish;

public class ShiftWishOperationException extends RuntimeException {
    public ShiftWishOperationException(String message) {
        super(message);
    }

    public ShiftWishOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
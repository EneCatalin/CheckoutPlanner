package com.ec.checkoutplanner.exception.shiftWish;


public class ShiftWishCreationException extends ShiftWishOperationException {
    public ShiftWishCreationException(String message) {
        super(message);
    }

    public ShiftWishCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
package com.ec.checkoutplanner.exception.shiftWish;

public class WishAlreadyExistsException extends ShiftWishOperationException {
    public WishAlreadyExistsException(String message) {
        super(message);
    }

    public WishAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

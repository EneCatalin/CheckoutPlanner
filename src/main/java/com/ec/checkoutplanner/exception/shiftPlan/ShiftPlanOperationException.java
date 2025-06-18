package com.ec.checkoutplanner.exception.shiftPlan;

public class ShiftPlanOperationException extends RuntimeException {
    public ShiftPlanOperationException(String message) {
        super(message);
    }

    public ShiftPlanOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
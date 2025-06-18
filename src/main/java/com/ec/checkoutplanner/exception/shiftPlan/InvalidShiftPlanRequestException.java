package com.ec.checkoutplanner.exception.shiftPlan;

public class InvalidShiftPlanRequestException extends ShiftPlanOperationException {
    public InvalidShiftPlanRequestException(String message) {
        super(message);
    }

    public InvalidShiftPlanRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
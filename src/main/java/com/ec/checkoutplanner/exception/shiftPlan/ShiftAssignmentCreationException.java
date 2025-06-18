package com.ec.checkoutplanner.exception.shiftPlan;

public class ShiftAssignmentCreationException extends ShiftPlanOperationException {
    public ShiftAssignmentCreationException(String message) {
        super(message);
    }

    public ShiftAssignmentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

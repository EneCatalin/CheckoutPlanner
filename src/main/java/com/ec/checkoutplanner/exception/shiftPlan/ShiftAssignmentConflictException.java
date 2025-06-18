package com.ec.checkoutplanner.exception.shiftPlan;

public class ShiftAssignmentConflictException extends ShiftPlanOperationException {
    public ShiftAssignmentConflictException(String message) {
        super(message);
    }

    public ShiftAssignmentConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}

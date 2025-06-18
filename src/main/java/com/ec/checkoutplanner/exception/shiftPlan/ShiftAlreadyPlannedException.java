package com.ec.checkoutplanner.exception.shiftPlan;

public class ShiftAlreadyPlannedException extends ShiftPlanOperationException {
  public ShiftAlreadyPlannedException(String message) {
    super(message);
  }

  public ShiftAlreadyPlannedException(String message, Throwable cause) {
    super(message, cause);
  }
}

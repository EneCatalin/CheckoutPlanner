package com.ec.checkoutplanner.exception.employee;

public class EmployeeCreationException extends EmployeeOperationException {
    public EmployeeCreationException(String message) {
        super(message);
    }

    public EmployeeCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ec.checkoutplanner.exception.employee;

public class EmployeeOperationException extends RuntimeException {
    public EmployeeOperationException(String message) {
        super(message);
    }

    public EmployeeOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
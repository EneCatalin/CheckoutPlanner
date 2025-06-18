package com.ec.checkoutplanner.exception.employee;

public class EmployeeNotFoundException extends EmployeeOperationException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

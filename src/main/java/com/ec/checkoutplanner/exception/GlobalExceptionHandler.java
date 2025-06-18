package com.ec.checkoutplanner.exception;

import com.ec.checkoutplanner.dto.errors.ErrorResponse;
import com.ec.checkoutplanner.exception.employee.EmployeeCreationException;
import com.ec.checkoutplanner.exception.employee.EmployeeNotFoundException;
import com.ec.checkoutplanner.exception.shiftPlan.InvalidShiftPlanRequestException;
import com.ec.checkoutplanner.exception.shiftPlan.ShiftAlreadyPlannedException;
import com.ec.checkoutplanner.exception.shiftPlan.ShiftAssignmentConflictException;
import com.ec.checkoutplanner.exception.shiftPlan.ShiftAssignmentCreationException;
import com.ec.checkoutplanner.exception.shiftWish.ShiftWishCreationException;
import com.ec.checkoutplanner.exception.shiftWish.ShiftWishRetrievalException;
import com.ec.checkoutplanner.exception.shiftWish.WishAlreadyExistsException;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // === EMPLOYEE ===

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(EmployeeCreationException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeCreation(EmployeeCreationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeAlreadyExists(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    // === SHIFT PLAN ===

    @ExceptionHandler(InvalidShiftPlanRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidShiftPlan(InvalidShiftPlanRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ShiftAlreadyPlannedException.class)
    public ResponseEntity<ErrorResponse> handleShiftAlreadyPlanned(ShiftAlreadyPlannedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ShiftAssignmentConflictException.class)
    public ResponseEntity<ErrorResponse> handleShiftConflict(ShiftAssignmentConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ShiftAssignmentCreationException.class)
    public ResponseEntity<ErrorResponse> handleShiftAssignmentError(ShiftAssignmentCreationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(ex.getMessage()));
    }

    // === SHIFT WISH ===

    @ExceptionHandler(WishAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleWishAlreadyExists(WishAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ShiftWishCreationException.class)
    public ResponseEntity<ErrorResponse> handleWishCreation(ShiftWishCreationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ShiftWishRetrievalException.class)
    public ResponseEntity<ErrorResponse> ShiftWishRetrievalException(ShiftWishRetrievalException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }

    // === GENERIC FALLBACK ===

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unhandled exception caught: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An unexpected error occurred. Please try again."));
    }
}

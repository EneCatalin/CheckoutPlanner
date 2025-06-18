package com.ec.checkoutplanner.dto;

import com.ec.checkoutplanner.constants.ShiftType;

import java.time.LocalDate;

public record CreateShiftWishRequest(Long employeeId, LocalDate date, ShiftType shiftType) {}

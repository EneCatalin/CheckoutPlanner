package com.ec.checkoutplanner.dto;

import com.ec.checkoutplanner.constants.ShiftType;

import java.time.LocalDate;
import java.util.List;

public record CreateShiftPlanRequest(LocalDate date, ShiftType shiftType, List<Long> employeeIds) {}

package com.ec.checkoutplanner.dto;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.constants.ShiftType;

import java.time.LocalDate;

public record ShiftPlanResponse(
        Long id,
        LocalDate date,
        ShiftType shiftType,
        Long employeeId,
        String employeeName,
        Role employeeRole
) {}

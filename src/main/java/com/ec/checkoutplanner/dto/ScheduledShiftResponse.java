package com.ec.checkoutplanner.dto;

import com.ec.checkoutplanner.constants.ShiftType;

import java.util.List;

public record ScheduledShiftResponse(
        ShiftType shiftType,
        List<ScheduledEmployee> employees
) {}

package com.ec.checkoutplanner.dto;
import com.ec.checkoutplanner.constants.Role;

public record ScheduledEmployee(
        Long id,
        String name,
        Role role
) {}
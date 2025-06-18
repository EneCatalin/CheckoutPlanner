package com.ec.checkoutplanner.dto;

import com.ec.checkoutplanner.constants.Role;

public record CreateEmployeeRequest(String name, Role role) {}

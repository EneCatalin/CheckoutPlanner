package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateEmployeeRequest;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees", description = "Endpoint for managing employee accounts")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new employee",
            description = "Registers a new employee or admin account in the system"
    )
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest request) {
        Employee created = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

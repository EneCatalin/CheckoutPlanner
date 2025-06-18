package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dev")
@Tag(name = "Development Utilities", description = "Endpoints for development/testing purposes only")
public class DevController {

    private final EmployeeRepository employeeRepository;

    public DevController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/seed")
    @Operation(
            summary = "Seed the database with test data",
            description = "Creates 3 employee accounts and 2 admin accounts. Only works if the employee table is empty. Use for demo/testing purposes only."
    )
    public ResponseEntity<List<Employee>> seed() {
        if (employeeRepository.count() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", Role.EMPLOYEE));
        employees.add(new Employee("Bob", Role.EMPLOYEE));
        employees.add(new Employee("Charlie", Role.EMPLOYEE));
        employees.add(new Employee("Diana", Role.ADMIN));
        employees.add(new Employee("Evan", Role.ADMIN));

        List<Employee> saved = employeeRepository.saveAll(employees);
        return ResponseEntity.ok(saved);
    }
}
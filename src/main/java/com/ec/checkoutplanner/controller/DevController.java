package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dev")
public class DevController {

    private final EmployeeRepository employeeRepository;

    public DevController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //? Bad idea in general, but this is a small demo
    //? Also it has no exception handler

    @PostMapping("/seed")
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

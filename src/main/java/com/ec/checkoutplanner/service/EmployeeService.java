package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.dto.CreateEmployeeRequest;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.exception.employee.EmployeeCreationException;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.existsByName(request.name())) {
            logger.error("Attempted to create duplicate employee: {}", request.name());
            throw new EntityExistsException("Employee already exists with name: " + request.name());
        }

        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setRole(request.role());

        try {
            Employee saved = employeeRepository.save(employee);
            logger.info("Employee created successfully: {}", saved);
            return saved;
        } catch (Exception e) {
            logger.error("Failed to save employee: {}", employee, e);
            throw new EmployeeCreationException("Failed to save employee", e);
        }
    }
}
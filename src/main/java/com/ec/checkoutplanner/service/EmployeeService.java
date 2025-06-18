package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(String name, Role role) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setRole(role);
        return employeeRepository.save(employee);
    }
}
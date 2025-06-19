package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.dto.CreateEmployeeRequest;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.exception.employee.EmployeeCreationException;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldCreateEmployeeSuccessfully() {
        // given
        CreateEmployeeRequest request = new CreateEmployeeRequest("Alice", Role.EMPLOYEE);
        when(employeeRepository.existsByName("Alice")).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(1L);
            return emp;
        });

        // when
        Employee result = employeeService.createEmployee(request);

        // then
        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals(Role.EMPLOYEE, result.getRole());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void shouldThrowIfEmployeeAlreadyExists() {
        // given
        CreateEmployeeRequest request = new CreateEmployeeRequest("Bob", Role.EMPLOYEE);
        when(employeeRepository.existsByName("Bob")).thenReturn(true);

        // expect
        assertThrows(EntityExistsException.class, () -> employeeService.createEmployee(request));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void shouldThrowOnSaveFailure() {
        // given
        CreateEmployeeRequest request = new CreateEmployeeRequest("Charlie", Role.EMPLOYEE);
        when(employeeRepository.existsByName("Charlie")).thenReturn(false);
        when(employeeRepository.save(any())).thenThrow(new RuntimeException("DB down"));

        // expect
        assertThrows(EmployeeCreationException.class, () -> employeeService.createEmployee(request));
    }
}

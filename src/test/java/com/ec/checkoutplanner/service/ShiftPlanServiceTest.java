package com.ec.checkoutplanner.service;


import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.constants.ShiftType;
import com.ec.checkoutplanner.dto.CreateShiftPlanRequest;
import com.ec.checkoutplanner.dto.ScheduledShiftResponse;
import com.ec.checkoutplanner.dto.ShiftPlanResponse;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftPlan;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import com.ec.checkoutplanner.repository.ShiftPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ShiftPlanServiceTest {

    private ShiftPlanRepository shiftPlanRepository;
    private EmployeeRepository employeeRepository;
    private ShiftPlanService shiftPlanService;

    @BeforeEach
    void setUp() {
        shiftPlanRepository = mock(ShiftPlanRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        shiftPlanService = new ShiftPlanService(shiftPlanRepository, employeeRepository);
    }

    @Test
    void createPlan_success() {
        LocalDate date = LocalDate.of(2025, 6, 20);
        ShiftType type = ShiftType.EARLY;

        CreateShiftPlanRequest request = new CreateShiftPlanRequest(date, type, List.of(1L, 2L));
        Employee e1 = new Employee("Alice", Role.EMPLOYEE);
        e1.setId(1L);
        Employee e2 = new Employee("Bob", Role.EMPLOYEE);
        e2.setId(2L);

        when(shiftPlanRepository.countByDateAndShiftType(date, type)).thenReturn(0L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(e1));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(e2));
        when(shiftPlanRepository.existsByEmployeeAndDate(any(), eq(date))).thenReturn(false);
        when(shiftPlanRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        List<ShiftPlanResponse> result = shiftPlanService.createPlan(request);

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).employeeName());
        assertEquals("Bob", result.get(1).employeeName());
    }

    @Test
    void getScheduleForDate_success() {
        LocalDate date = LocalDate.of(2025, 6, 21);

        Employee e1 = new Employee("Charlie", Role.EMPLOYEE);
        e1.setId(10L);
        ShiftPlan plan = new ShiftPlan();
        plan.setDate(date);
        plan.setShiftType(ShiftType.LATE);
        plan.setEmployee(e1);

        when(shiftPlanRepository.findByDate(date)).thenReturn(List.of(plan));

        List<ScheduledShiftResponse> result = shiftPlanService.getScheduleForDate(date);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.shiftType().equals(ShiftType.LATE)));
        assertTrue(result.stream().anyMatch(s -> s.shiftType().equals(ShiftType.EARLY))); // should exist even if empty
    }
}

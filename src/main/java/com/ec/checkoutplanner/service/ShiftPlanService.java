package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.constants.ShiftType;
import com.ec.checkoutplanner.dto.CreateShiftPlanRequest;
import com.ec.checkoutplanner.dto.ScheduledEmployee;
import com.ec.checkoutplanner.dto.ScheduledShiftResponse;
import com.ec.checkoutplanner.dto.ShiftPlanResponse;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftPlan;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import com.ec.checkoutplanner.repository.ShiftPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShiftPlanService {

    private final ShiftPlanRepository shiftPlanRepository;
    private final EmployeeRepository employeeRepository;

    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, EmployeeRepository employeeRepository) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<ShiftPlanResponse> createPlan(CreateShiftPlanRequest request) {
        if (request.employeeIds().size() != 2) {
            throw new IllegalArgumentException("Each shift must have exactly two employees.");
        }

        long existingCount = shiftPlanRepository.countByDateAndShiftType(request.date(), request.shiftType());
        if (existingCount > 0) {
            throw new IllegalStateException("This shift has already been assigned.");
        }

        List<ShiftPlanResponse> responses = new ArrayList<>();

        for (Long employeeId : request.employeeIds()) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));

            boolean alreadyAssigned = shiftPlanRepository.existsByEmployeeAndDate(employee, request.date());
            if (alreadyAssigned) {
                throw new IllegalStateException("Employee " + employee.getName() + " is already assigned on this date.");
            }

            ShiftPlan plan = new ShiftPlan();
            plan.setDate(request.date());
            plan.setShiftType(request.shiftType());
            plan.setEmployee(employee);
            ShiftPlan saved = shiftPlanRepository.save(plan);

            responses.add(mapToResponse(saved));
        }

        return responses;
    }
    public List<ScheduledShiftResponse> getScheduleForDate(LocalDate date) {
        List<ShiftPlan> plans = shiftPlanRepository.findByDate(date);

        Map<ShiftType, List<ScheduledEmployee>> grouped = plans.stream()
                .collect(Collectors.groupingBy(
                        ShiftPlan::getShiftType,
                        Collectors.mapping(plan -> new ScheduledEmployee(
                                plan.getEmployee().getId(),
                                plan.getEmployee().getName(),
                                plan.getEmployee().getRole()
                        ), Collectors.toList())
                ));

        // Ensure both EARLY and LATE are present, even if empty
        return Arrays.stream(ShiftType.values())
                .map(shiftType -> new ScheduledShiftResponse(
                        shiftType,
                        grouped.getOrDefault(shiftType, List.of())
                ))
                .toList();
    }
    private ShiftPlanResponse mapToResponse(ShiftPlan plan) {
        return new ShiftPlanResponse(
                plan.getId(),
                plan.getDate(),
                plan.getShiftType(),
                plan.getEmployee().getId(),
                plan.getEmployee().getName(),
                plan.getEmployee().getRole()
        );
    }

}

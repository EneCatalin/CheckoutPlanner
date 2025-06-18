package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.dto.CreateShiftPlanRequest;
import com.ec.checkoutplanner.dto.ShiftPlanResponse;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftPlan;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import com.ec.checkoutplanner.repository.ShiftPlanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

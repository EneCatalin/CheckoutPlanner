package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.dto.CreateShiftWishRequest;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftWish;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import com.ec.checkoutplanner.repository.ShiftWishRepository;
import org.springframework.stereotype.Service;

@Service
public class ShiftWishService {

    private final ShiftWishRepository shiftWishRepository;
    private final EmployeeRepository employeeRepository;

    public ShiftWishService(ShiftWishRepository shiftWishRepository, EmployeeRepository employeeRepository) {
        this.shiftWishRepository = shiftWishRepository;
        this.employeeRepository = employeeRepository;
    }

    public ShiftWish createWish(CreateShiftWishRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        if (shiftWishRepository.existsByEmployeeAndDate(employee, request.date())) {
            throw new IllegalStateException("Wish already submitted for this date");
        }

        ShiftWish wish = new ShiftWish();
        wish.setEmployee(employee);
        wish.setDate(request.date());
        wish.setShiftType(request.shiftType());

        return shiftWishRepository.save(wish);
    }
}

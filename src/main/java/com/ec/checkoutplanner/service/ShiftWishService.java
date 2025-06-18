package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.dto.CreateShiftWishRequest;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftWish;
import com.ec.checkoutplanner.exception.employee.EmployeeNotFoundException;
import com.ec.checkoutplanner.exception.shiftWish.ShiftWishCreationException;
import com.ec.checkoutplanner.exception.shiftWish.ShiftWishRetrievalException;
import com.ec.checkoutplanner.exception.shiftWish.WishAlreadyExistsException;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import com.ec.checkoutplanner.repository.ShiftWishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftWishService {

    private static final Logger logger = LoggerFactory.getLogger(ShiftWishService.class);

    private final ShiftWishRepository shiftWishRepository;
    private final EmployeeRepository employeeRepository;

    public ShiftWishService(ShiftWishRepository shiftWishRepository, EmployeeRepository employeeRepository) {
        this.shiftWishRepository = shiftWishRepository;
        this.employeeRepository = employeeRepository;
    }

    public ShiftWish createWish(CreateShiftWishRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> {
                    logger.warn("Employee not found with ID: {}", request.employeeId());
                    return new EmployeeNotFoundException("Employee not found with ID: " + request.employeeId());
                });

        if (shiftWishRepository.existsByEmployeeAndDate(employee, request.date())) {
            logger.warn("Wish already exists for employee {} on {}", employee.getId(), request.date());
            throw new WishAlreadyExistsException("Shift wish already exists for this employee on " + request.date());
        }

        ShiftWish wish = new ShiftWish();
        wish.setEmployee(employee);
        wish.setDate(request.date());
        wish.setShiftType(request.shiftType());

        try {
            ShiftWish savedWish = shiftWishRepository.save(wish);
            logger.info("Shift wish created: {}", savedWish);
            return savedWish;
        } catch (Exception e) {
            logger.error("Failed to save shift wish: {}", wish, e);
            throw new ShiftWishCreationException("Failed to save shift wish", e);
        }
    }

    public List<ShiftWish> getAllWishes() {
        try {
            return shiftWishRepository.findAll();
        } catch (Exception e) {
            logger.error("Failed to retrieve shift wishes", e);
            throw new ShiftWishRetrievalException("Unable to retrieve shift wishes at this time", e);
        }
    }

    public List<ShiftWish> getWishesByEmployeeName(String name) {
        try {
            Employee employee = employeeRepository.findByName(name)
                    .orElseThrow(() -> {
                        logger.warn("No employee found with name: {}", name);
                        return new EmployeeNotFoundException("No employee found with name: " + name);
                    });

            return shiftWishRepository.findByEmployee(employee);
        } catch (Exception e) {
            logger.error("Failed to retrieve shift wishes for employee: {}", name, e);
            throw new ShiftWishRetrievalException("Could not retrieve shift wishes for employee: " + name, e);
        }
    }

}

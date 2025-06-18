package com.ec.checkoutplanner.repository;

import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ShiftWishRepository extends JpaRepository<ShiftWish, Long> {
    boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
}
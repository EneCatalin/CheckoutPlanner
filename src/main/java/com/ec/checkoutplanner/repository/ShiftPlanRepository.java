package com.ec.checkoutplanner.repository;

import com.ec.checkoutplanner.constants.ShiftType;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftPlanRepository extends JpaRepository<ShiftPlan, Long> {
    long countByDateAndShiftType(LocalDate date, ShiftType shiftType);
    boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
    List<ShiftPlan> findByDate(LocalDate date);
}

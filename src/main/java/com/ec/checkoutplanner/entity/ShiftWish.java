package com.ec.checkoutplanner.entity;

import com.ec.checkoutplanner.constants.ShiftType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "date"})
        }
)public class ShiftWish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;

    @ManyToOne(optional = false)
    private Employee employee;

    public ShiftWish() {
    }

    public ShiftWish(Long id, LocalDate date, ShiftType shiftType, Employee employee) {
        this.id = id;
        this.date = date;
        this.shiftType = shiftType;
        this.employee = employee;
    }


    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
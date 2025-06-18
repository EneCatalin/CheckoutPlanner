package com.ec.checkoutplanner.entity;

import com.ec.checkoutplanner.constants.ShiftType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "date"})
        }
)
public class ShiftPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;

    @ManyToOne(optional = false)
    private Employee employee;

    // Getters and setters
}

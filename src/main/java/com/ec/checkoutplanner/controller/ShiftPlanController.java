package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateShiftPlanRequest;
import com.ec.checkoutplanner.dto.ScheduledShiftResponse;
import com.ec.checkoutplanner.dto.ShiftPlanResponse;
import com.ec.checkoutplanner.service.ShiftPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/planning")
public class ShiftPlanController {

    private final ShiftPlanService shiftPlanService;

    public ShiftPlanController(ShiftPlanService shiftPlanService) {
        this.shiftPlanService = shiftPlanService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduledShiftResponse>> getSchedule(@RequestParam LocalDate date) {
        List<ScheduledShiftResponse> schedule = shiftPlanService.getScheduleForDate(date);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<List<ShiftPlanResponse>> createPlan(@RequestBody CreateShiftPlanRequest request) {
        List<ShiftPlanResponse> plans = shiftPlanService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plans);
    }
}


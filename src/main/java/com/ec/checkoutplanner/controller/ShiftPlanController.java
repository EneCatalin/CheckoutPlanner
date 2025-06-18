package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateShiftPlanRequest;
import com.ec.checkoutplanner.dto.ScheduledShiftResponse;
import com.ec.checkoutplanner.dto.ShiftPlanResponse;
import com.ec.checkoutplanner.service.ShiftPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/planning")
@Tag(name = "Shift Planning", description = "Endpoints for assigning and viewing scheduled shifts")
public class ShiftPlanController {

    private final ShiftPlanService shiftPlanService;

    public ShiftPlanController(ShiftPlanService shiftPlanService) {
        this.shiftPlanService = shiftPlanService;
    }

    @GetMapping
    @Operation(
            summary = "View schedule for a specific day",
            description = "Returns all scheduled employees for each shift (early and late) on a given date"
    )
    public ResponseEntity<List<ScheduledShiftResponse>> getSchedule(@RequestParam LocalDate date) {
        List<ScheduledShiftResponse> schedule = shiftPlanService.getScheduleForDate(date);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    @Operation(
            summary = "Assign employees to a shift",
            description = "Allows admins to assign two employees to a specific shift (early or late) for a given date"
    )
    public ResponseEntity<List<ShiftPlanResponse>> createPlan(@RequestBody CreateShiftPlanRequest request) {
        List<ShiftPlanResponse> plans = shiftPlanService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plans);
    }
}

package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateShiftPlanRequest;
import com.ec.checkoutplanner.dto.ShiftPlanResponse;
import com.ec.checkoutplanner.service.ShiftPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/planning")
public class ShiftPlanController {

    private final ShiftPlanService shiftPlanService;

    public ShiftPlanController(ShiftPlanService shiftPlanService) {
        this.shiftPlanService = shiftPlanService;
    }

    @PostMapping
    public ResponseEntity<List<ShiftPlanResponse>> createPlan(@RequestBody CreateShiftPlanRequest request) {
        List<ShiftPlanResponse> plans = shiftPlanService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plans);
    }
}


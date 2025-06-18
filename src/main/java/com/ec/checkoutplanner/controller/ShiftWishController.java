package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateShiftWishRequest;
import com.ec.checkoutplanner.entity.ShiftWish;
import com.ec.checkoutplanner.service.ShiftWishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
@Tag(name = "Shift Wishes", description = "Endpoints for employees to submit their shift preferences")
public class ShiftWishController {

    private final ShiftWishService shiftWishService;

    public ShiftWishController(ShiftWishService shiftWishService) {
        this.shiftWishService = shiftWishService;
    }

    @PostMapping
    @Operation(
            summary = "Submit a shift wish",
            description = "Allows an employee to submit their preferred shift (early or late) for a specific date"
    )
    public ResponseEntity<ShiftWish> createWish(@RequestBody CreateShiftWishRequest request) {
        ShiftWish wish = shiftWishService.createWish(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(wish);
    }
}
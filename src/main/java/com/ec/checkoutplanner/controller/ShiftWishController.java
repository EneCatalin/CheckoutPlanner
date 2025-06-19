package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateShiftWishRequest;
import com.ec.checkoutplanner.entity.ShiftWish;
import com.ec.checkoutplanner.service.ShiftWishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
@Tag(name = "Shift Wishes", description = "Endpoints for employees to submit and view their shift preferences")
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
        return ResponseEntity.status(201).body(wish);
    }


    //? Optional route
    @GetMapping
    @Operation(
            summary = "Get all shift wishes",
            description = "Returns a list of all submitted shift wishes"
    )
    public ResponseEntity<List<ShiftWish>> getAllWishes() {
        return ResponseEntity.ok(shiftWishService.getAllShiftWishes());
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Get shift wishes by employee name",
            description = "Returns all submitted shift wishes for a specific employee"
    )
    public ResponseEntity<List<ShiftWish>> getWishesByEmployeeName(@PathVariable String name) {
        List<ShiftWish> wishes = shiftWishService.getWishesByEmployeeName(name);
        return ResponseEntity.ok(wishes);
    }

}

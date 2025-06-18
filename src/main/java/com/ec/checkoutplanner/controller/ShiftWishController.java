package com.ec.checkoutplanner.controller;

import com.ec.checkoutplanner.dto.CreateShiftWishRequest;
import com.ec.checkoutplanner.entity.ShiftWish;
import com.ec.checkoutplanner.service.ShiftWishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class ShiftWishController {

    private final ShiftWishService shiftWishService;

    public ShiftWishController(ShiftWishService shiftWishService) {
        this.shiftWishService = shiftWishService;
    }

    @PostMapping
    public ResponseEntity<ShiftWish> createWish(@RequestBody CreateShiftWishRequest request) {
        ShiftWish wish = shiftWishService.createWish(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(wish);
    }
}

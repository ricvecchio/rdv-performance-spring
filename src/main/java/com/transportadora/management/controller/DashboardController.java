package com.transportadora.management.controller;

import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardDTO dashboard(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(name = "year") int year) {

        return dashboardService.dashboard(page, pageSize, filter, year);
    }

}
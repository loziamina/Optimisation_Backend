package com.example.backend.controller;

import com.example.backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public long dashboard() {
        return dashboardService.totalFinishedMatches();
    }
    
    @PostMapping("/match")
    public void createMatch() {
        dashboardService.createDummyMatch();
    }

    
}

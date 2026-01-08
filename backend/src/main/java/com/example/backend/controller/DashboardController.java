package com.example.backend.controller;

import com.example.backend.dto.MatchPlayerDto;
import com.example.backend.dto.MatchSummaryDto;
import com.example.backend.entity.Match;
import com.example.backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Endpoint ANTI-PATTERN
    @GetMapping("/nplus1")
    public List<String> nplus1() {
        return dashboardService.getPlayerNamesNPlusOne();
    }
    
    //  Endpoint OPTIMISÉ
    @GetMapping("/nplus1/optimized")
    public List<String> nplus1Optimized() {
        return dashboardService.getPlayerNamesOptimized();
    }
    
    @GetMapping("/matches/paged")
    public List<Match> getMatchesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return dashboardService.getFinishedMatchesPaged(page, size);
    }
    
    @GetMapping("/matches/paged/optimized")
    public List<MatchPlayerDto> pagedOptimized(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return dashboardService.getMatchesPagedOptimized(page, size);
    }

    
}

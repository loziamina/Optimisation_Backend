package com.example.backend.service;

import com.example.backend.entity.Match;
import com.example.backend.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final MatchRepository matchRepository;

    public DashboardService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Cacheable("dashboard")
    public long totalFinishedMatches() {
        return matchRepository.countByStatus("FINISHED");
    }

    public void createDummyMatch() {
        Match match = new Match();
        match.setStatus("FINISHED");
        match.setScore(100);
        match.setMode("RANKED");
        match.setServer("EU");
        match.setStartTime(LocalDateTime.now().minusMinutes(5));
        match.setEndTime(LocalDateTime.now());

        matchRepository.save(match);
    }
    
}

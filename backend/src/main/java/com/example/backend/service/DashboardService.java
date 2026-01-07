package com.example.backend.service;

import com.example.backend.entity.Match;
import com.example.backend.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final MatchRepository matchRepository;

    public DashboardService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public long totalFinishedMatches() {
        List<Match> matches = matchRepository.findByStatus("FINISHED");
        return matches.size();
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

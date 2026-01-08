package com.example.backend.service;

import com.example.backend.dto.MatchPlayerDto;
import com.example.backend.dto.MatchSummaryDto;
import com.example.backend.entity.Match;
import com.example.backend.repository.MatchRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
@Service
public class DashboardService {

    private final MatchRepository matchRepository;

    public DashboardService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // =====================
    // READ optimisable (cache)
    // =====================
    @Cacheable("dashboard")
    public long totalFinishedMatches() {
        return matchRepository.countByStatus("FINISHED");
    }

    // =====================
    // WRITE
    // =====================
    
    // invalide cache
    
    @CacheEvict(value = "dashboard", allEntries = true)

    public void createDummyMatch() {
        Match match = new Match();
        match.setStatus("FINISHED");
        match.setScore(100);
        match.setMode("RANKED");
        match.setServer("EU");
        match.setStartTime(LocalDateTime.now().minusMinutes(5));
        match.setEndTime(LocalDateTime.now());

        // ⚠️ volontairement PAS de player → cas réaliste
        matchRepository.save(match);
    }

    // =====================
    // ❌ ANTI-PATTERN N+1 (volontaire)
    // =====================
    @Transactional(readOnly = true)
    public List<String> getPlayerNamesNPlusOne() {

        List<Match> matches = matchRepository.findAll(); // 1 requête

        List<String> names = new ArrayList<>();

        for (Match m : matches) {
            if (m.getPlayer() != null) {
                names.add(m.getPlayer().getUsername()); // ❌ N+1 volontaire
            }
        }

        return names;
    }
    
    @Transactional(readOnly = true)
    public List<String> getPlayerNamesOptimized() {
        return matchRepository.findAllPlayerUsernamesDto()
                .stream()
                .map(MatchPlayerDto::getUsername)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<Match> getFinishedMatchesPaged(int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        return matchRepository
                .findByStatus("FINISHED", pageable)
                .getContent();
    }
    
    // optimisée pagination : 
    
    @Transactional(readOnly = true)
    public List<MatchSummaryDto> getFinishedMatchesOptimized(int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        return matchRepository
                .findMatchSummariesByStatus("FINISHED", pageable)
                .getContent();
    }
    
    @Transactional(readOnly = true)
    @Cacheable(
        value = "matchesPaged",
        key = "#page + '-' + #size"
    )
    public List<MatchPlayerDto> getMatchesPagedOptimized(int page, int size) {

        return matchRepository
                .findAllPlayerUsernamesDtoPaged(
                        PageRequest.of(page, size)
                )
                .getContent();
    }



}

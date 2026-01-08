package com.example.backend.dto;

public class MatchSummaryDto {

    private final Long matchId;
    private final String status;
    private final String username;

    public MatchSummaryDto(Long matchId, String status, String username) {
        this.matchId = matchId;
        this.status = status;
        this.username = username;
    }

    public Long getMatchId() {
        return matchId;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }
}

package com.example.backend.dto;

public class MatchPlayerDto {

    private final String username;

    public MatchPlayerDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

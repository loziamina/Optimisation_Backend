package com.example.dto;

public class QueueStatusResponse {

    private Long playerId;
    private String status;
    private int position;

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}

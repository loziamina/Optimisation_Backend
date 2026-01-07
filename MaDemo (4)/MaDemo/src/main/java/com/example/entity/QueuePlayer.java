package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_player")
public class QueuePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private int elo;

    private LocalDateTime joinTime;

    private String status; // WAITING, MATCHED, LEFT

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public int getElo() { return elo; }
    public void setElo(int elo) { this.elo = elo; }

    public LocalDateTime getJoinTime() { return joinTime; }
    public void setJoinTime(LocalDateTime joinTime) { this.joinTime = joinTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

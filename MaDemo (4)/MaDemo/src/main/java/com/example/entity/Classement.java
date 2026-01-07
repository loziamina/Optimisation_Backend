package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "classement")
public class Classement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;   // référence vers profil
    @Column(name = "rang")   // <- colonne SQL
    private int rank;        // <- champ Java
    private int elo;         // valeur d’ELO du joueur

    public Classement() {}

    public Classement(Long playerId, int rank, int elo) {
        this.playerId = playerId;
        this.rank = rank;
        this.elo = elo;
    }

    // GETTERS / SETTERS
    public Long getId() { return id; }
    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }
    public int getElo() { return elo; }
    public void setElo(int elo) { this.elo = elo; }
}

package com.example.dto;

public class ProfilDto {

    private Long id;
    private String nom;
    private int elo;   // 🔥

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getElo() { return elo; }
    public void setElo(int elo) { this.elo = elo; }
}

package com.example.service;

import com.example.dao.MatchDao;
import com.example.dao.ProfilDao;
import com.example.dao.QueuePlayerDao;
import com.example.entity.MatchEntity;
import com.example.entity.Profil;
import com.example.entity.QueuePlayer;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional 
public class MatchService {

    private final QueuePlayerDao queueDao;
    private final MatchDao matchDao;
    private final ProfilDao profilDao;

    public MatchService(QueuePlayerDao queueDao, MatchDao matchDao, ProfilDao profilDao) {
        this.queueDao = queueDao;
        this.matchDao = matchDao;
        this.profilDao = profilDao;
    }

    // --------------------------------------------------------
    // 1️⃣ Création des matchs à partir des joueurs WAITING
    // --------------------------------------------------------
    public List<MatchEntity> createMatches() {

        List<MatchEntity> createdMatches = new ArrayList<>();

        List<QueuePlayer> waiting = queueDao.findByStatusOrderByJoinTimeAsc("WAITING");

        for (int i = 0; i + 1 < waiting.size(); i += 2) {
            QueuePlayer p1 = waiting.get(i);
            QueuePlayer p2 = waiting.get(i + 1);

            MatchEntity m = new MatchEntity();
            m.setPlayer1Id(p1.getPlayerId());
            m.setPlayer2Id(p2.getPlayerId());
            m.setStatus("PENDING");
            m.setCreatedAt(java.time.LocalDateTime.now());
            matchDao.save(m);
            createdMatches.add(m);

            p1.setStatus("MATCHED");
            p2.setStatus("MATCHED");
            queueDao.save(p1);
            queueDao.save(p2);
        }

        return createdMatches;
    }

    // --------------------------------------------------------
    // 2️⃣ Jouer un match : FINISHED + winner + mise à jour ELO
    // --------------------------------------------------------
    public MatchEntity playMatch(Long matchId, Long winnerId) {

        MatchEntity match = matchDao.findById(matchId)
                .orElseThrow(() -> new RuntimeException("MATCH_NOT_FOUND"));

        if (!"PENDING".equals(match.getStatus())) {
            throw new RuntimeException("MATCH_ALREADY_FINISHED");
        }

        Long p1Id = match.getPlayer1Id();
        Long p2Id = match.getPlayer2Id();

        // Charger les profils depuis la BD
        Profil player1 = profilDao.findById(p1Id);
        Profil player2 = profilDao.findById(p2Id);

        if (player1 == null || player2 == null) {
            throw new RuntimeException("PLAYER_NOT_FOUND");
        }

        int delta = 20; // variation simple d’ELO

        if (winnerId.equals(p1Id)) {
            player1.setElo(player1.getElo() + delta);
            player2.setElo(player2.getElo() - delta);
        } else if (winnerId.equals(p2Id)) {
            player2.setElo(player2.getElo() + delta);
            player1.setElo(player1.getElo() - delta);
        } else {
            throw new RuntimeException("WINNER_NOT_IN_MATCH");
        }

        // Sauvegarder les nouveaux ELO
        profilDao.saveEntity(player1);
        profilDao.saveEntity(player2);

        // Mettre à jour le match
        match.setStatus("FINISHED");
        match.setWinnerId(winnerId);

        return matchDao.save(match);
    }
}

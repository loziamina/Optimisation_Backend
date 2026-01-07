package com.example.service;

import com.example.dao.QueuePlayerDao;
import com.example.dto.QueueJoinRequest;
import com.example.dto.QueueStatusResponse;
import com.example.entity.QueuePlayer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {

    private final QueuePlayerDao queueDao;

    public QueueService(QueuePlayerDao queueDao) {
        this.queueDao = queueDao;
    }

    // --------------------------------------------------------
    // 1️⃣ Ajouter un joueur dans la file d’attente
    // --------------------------------------------------------
    public QueuePlayer joinQueue(QueueJoinRequest req) {

        QueuePlayer q = new QueuePlayer();
        q.setPlayerId(req.getPlayerId());
        q.setElo(req.getElo());
        q.setJoinTime(LocalDateTime.now());
        q.setStatus("WAITING");

        return queueDao.save(q);
    }

    // --------------------------------------------------------
    // 2️⃣ Retirer un joueur de la file
    // --------------------------------------------------------
    public String leaveQueue(Long playerId) {

        var opt = queueDao.findByPlayerId(playerId);

        if (opt.isEmpty()) {
            return "NOT_FOUND";
        }

        QueuePlayer q = opt.get();
        q.setStatus("LEFT");
        queueDao.save(q);

        return "LEFT_QUEUE";
    }

    // --------------------------------------------------------
    // 3️⃣ Obtenir le statut d’un joueur dans la file
    // --------------------------------------------------------
    public QueueStatusResponse getStatus(Long playerId) {

        QueueStatusResponse resp = new QueueStatusResponse();
        resp.setPlayerId(playerId);

        var opt = queueDao.findByPlayerId(playerId);

        // Joueur introuvable
        if (opt.isEmpty()) {
            resp.setStatus("NOT_IN_QUEUE");
            resp.setPosition(-1);
            return resp;
        }

        QueuePlayer p = opt.get();
        resp.setStatus(p.getStatus());

        // Calcul de la position dans la file
        List<QueuePlayer> waiting = queueDao.findByStatusOrderByJoinTimeAsc("WAITING");

        int position = -1;

        for (int i = 0; i < waiting.size(); i++) {
            if (waiting.get(i).getPlayerId().equals(playerId)) {
                position = i + 1; // position humaine (1er, 2e…)
            }
        }

        resp.setPosition(position);

        return resp;
    }
}

package com.example.dao;

import com.example.entity.QueuePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QueuePlayerDao extends JpaRepository<QueuePlayer, Long> {

    Optional<QueuePlayer> findByPlayerId(Long playerId);

    List<QueuePlayer> findByStatusOrderByJoinTimeAsc(String status);

}

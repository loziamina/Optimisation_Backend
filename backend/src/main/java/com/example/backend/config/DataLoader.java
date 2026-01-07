package com.example.backend.config;

import com.example.backend.entity.Match;
import com.example.backend.entity.Player;
import com.example.backend.repository.MatchRepository;
import com.example.backend.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(PlayerRepository playerRepo,
                               MatchRepository matchRepo) {

        return args -> {
            if (playerRepo.count() > 0) return;

            Random random = new Random();

            for (int i = 1; i <= 1000; i++) {
                Player p = new Player();
                p.setUsername("player_" + i);
                p.setRegion("EU");
                p.setRanking(random.nextInt(3000));
                playerRepo.save(p);

                for (int j = 0; j < 200; j++) {
                    Match m = new Match();
                    m.setPlayer(p);
                    m.setScore(random.nextInt(100));
                    m.setMode("RANKED");
                    m.setStatus(j % 2 == 0 ? "FINISHED" : "RUNNING");
                    m.setServer("EU-WEST");
                    m.setStartTime(LocalDateTime.now().minusMinutes(30));
                    m.setEndTime(LocalDateTime.now());
                    matchRepo.save(m);
                }
            }

            System.out.println(" Données générées");
        };
    }
}

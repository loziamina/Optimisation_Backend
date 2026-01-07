package com.example.controller;

import com.example.dto.QueueJoinRequest;
import com.example.service.QueueService;
import com.example.service.MatchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matchmaking")
public class MatchmakingController {

    private final QueueService queueService;
    private final MatchService matchService;

    public MatchmakingController(QueueService queueService, MatchService matchService) {
        this.queueService = queueService;
        this.matchService = matchService;
    }

    @PostMapping("/join")
    public String joinQueue(@RequestBody QueueJoinRequest req){
        queueService.joinQueue(req);
        return "JOINED_QUEUE";
    }

    @PostMapping("/leave")
    public String leaveQueue(@RequestParam Long playerId){
        return queueService.leaveQueue(playerId);
    }

    @GetMapping("/status/{playerId}")
    public Object status(@PathVariable Long playerId){
        return queueService.getStatus(playerId);
    }

    @PostMapping("/run")
    public Object runMatchmaking(){
        return matchService.createMatches();
    }

    // 💥 Nouveau : jouer un match + winner + ELO
    @PostMapping("/play/{matchId}")
    public Object playMatch(
            @PathVariable Long matchId,
            @RequestParam Long winnerId
    ){
        return matchService.playMatch(matchId, winnerId);
    }
}

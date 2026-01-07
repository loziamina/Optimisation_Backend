package com.example.dto;

public class MatchDto {

    private Long matchId;
    private Long player1Id;
    private Long player2Id;
    private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPlayer2Id() {
		return player2Id;
	}
	public void setPlayer2Id(Long player2Id) {
		this.player2Id = player2Id;
	}
	public Long getPlayer1Id() {
		return player1Id;
	}
	public void setPlayer1Id(Long player1Id) {
		this.player1Id = player1Id;
	}
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

}

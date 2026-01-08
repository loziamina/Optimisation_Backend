package com.example.backend.repository;

import com.example.backend.dto.MatchPlayerDto;
import com.example.backend.dto.MatchSummaryDto;
import com.example.backend.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

	public interface MatchRepository extends JpaRepository<Match, Long> {

	    // ❌ N+1 version (on garde pour comparaison)
	    List<Match> findAll();

	    // ✅ VERSION OPTIMISÉE DTO
	    @Query("""
	        SELECT new com.example.backend.dto.MatchPlayerDto(p.username)
	        FROM Match m
	        JOIN m.player p
	    """)
	    List<MatchPlayerDto> findAllPlayerUsernamesDto();

    long countByStatus(String status);

    List<Match> findByStatus(String status);
    Page<Match> findByStatus(String status, Pageable pageable);
    

@Query("""
    SELECT new com.example.backend.dto.MatchSummaryDto(
        m.id,
        m.status,
        p.username
    )
    FROM Match m
    JOIN m.player p
    WHERE m.status = :status
""")
	Page<MatchSummaryDto> findMatchSummariesByStatus(
	        String status,
	        Pageable pageable
	);

@Query("""
	    SELECT new com.example.backend.dto.MatchPlayerDto(p.username)
	    FROM Match m
	    JOIN m.player p
	""")
	Page<MatchPlayerDto> findAllPlayerUsernamesDtoPaged(Pageable pageable);



}

// src/main/java/com/example/dao/MatchDao.java
package com.example.dao;

import com.example.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDao extends JpaRepository<MatchEntity, Long> {
}

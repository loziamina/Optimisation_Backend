package com.example.dao;

import com.example.entity.Classement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassementDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void clear() {
        entityManager.createQuery("DELETE FROM Classement").executeUpdate();
    }

    public Classement save(Classement c) {
        entityManager.persist(c);
        return c;
    }

    public List<Classement> findAll() {
        return entityManager
                .createQuery("SELECT c FROM Classement c ORDER BY c.rank ASC", Classement.class)
                .getResultList();
    }
}

package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.dto.ProfilDto;
import com.example.entity.Profil;
import com.example.util.DtoEntityUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProfilDao {

    @PersistenceContext
    private EntityManager entityManager;

    // ----------- CRÉATION à partir d’un DTO -----------

    public ProfilDto save(ProfilDto profilDto) {

        Profil profil = DtoEntityUtil.profilDToToProfil(profilDto);

        // 🔥 si pas d'ELO renseigné → 1500 par défaut
        if (profil.getElo() == 0) {
            profil.setElo(1500);
        }

        entityManager.persist(profil);

        profilDto.setId(profil.getId());
        profilDto.setElo(profil.getElo());

        return profilDto;
    }

    // ----------- trouver un profil par id -----------

    public Profil findById(Long id) {
        return entityManager.find(Profil.class, id);
    }

    // ----------- sauvegarde / mise à jour d’un Profil entity -----------

    public Profil saveEntity(Profil profil) {
        return entityManager.merge(profil);
    }

    // ----------- classement général, trié par ELO décroissant -----------

    public List<Profil> findAllOrderByEloDesc() {
        return entityManager
                .createQuery("SELECT p FROM Profil p ORDER BY p.elo DESC", Profil.class)
                .getResultList();
    }
}

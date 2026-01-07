package com.example.service;

import com.example.dao.ClassementDao;
import com.example.dao.ProfilDao;
import com.example.dto.ProfilDto;
import com.example.entity.Classement;
import com.example.entity.Profil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfilService {

    private final ProfilDao profilDao;
    private final ClassementDao classementDao;

    public ProfilService(ProfilDao profilDao, ClassementDao classementDao) {
        this.profilDao = profilDao;
        this.classementDao = classementDao;
    }

    // 🔹 Création d’un profil à partir d’un DTO
    @Transactional
    public ProfilDto saveProfil(ProfilDto profilDto) {
        return profilDao.save(profilDto);
    }

    // 🔹 Mise à jour de la table classement + retour du classement
    @Transactional
    public List<Classement> updateAndGetRanking() {

        // 1) Récupérer tous les profils triés par ELO décroissant
        List<Profil> profils = profilDao.findAllOrderByEloDesc();

        // 2) Vider l’ancienne table classement
        classementDao.clear();

        // 3) Recréer le classement à partir des profils
        int rank = 1;
        for (Profil p : profils) {
            Classement c = new Classement(p.getId(), rank, p.getElo());
            classementDao.save(c);
            rank++;
        }

        // 4) Renvoyer le classement depuis la table
        return classementDao.findAll();
    }
}

package com.example.controller;

import com.example.dao.ProfilDao;
import com.example.entity.Profil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankingController {

    private final ProfilDao profilDao;

    public RankingController(ProfilDao profilDao) {
        this.profilDao = profilDao;
    }

    @GetMapping("/ranking")
    public List<Profil> ranking() {
        return profilDao.findAllOrderByEloDesc();
    }
}

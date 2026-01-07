package com.example.controller;

import com.example.dto.ProfilDto;
import com.example.entity.Classement;
import com.example.service.ProfilService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profil")
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    // POST /profil  → créer un joueur
    @PostMapping
    public ProfilDto createProfil(@RequestBody ProfilDto profilDto) {
        return profilService.saveProfil(profilDto);
    }

    // GET /profil/ranking → met à jour + renvoie le classement
    @GetMapping("/ranking")
    public List<Classement> ranking() {
        return profilService.updateAndGetRanking();
    }
}

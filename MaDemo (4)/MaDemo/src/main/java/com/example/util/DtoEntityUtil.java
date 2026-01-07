package com.example.util;

import com.example.dto.ProfilDto;
import com.example.entity.Profil;

public class DtoEntityUtil {

    public static Profil profilDToToProfil(ProfilDto dto) {
        Profil p = new Profil();
        p.setId(dto.getId());
        p.setNom(dto.getNom());
        p.setElo(dto.getElo());
        return p;
    }

    public static ProfilDto profilToDto(Profil p) {
        ProfilDto dto = new ProfilDto();
        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setElo(p.getElo());
        return dto;
    }
}

package fr.pau.univ.suivie_projet.suivie_projet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
public class EncadrantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
}
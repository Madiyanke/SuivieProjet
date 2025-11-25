package fr.pau.univ.suivie_projet.suivie_projet.dtos;

import fr.pau.univ.suivie_projet.suivie_projet.enums.EtatProjet;
import lombok.Data;


import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjetDTO {
    private Long id;
    private String titre;
    private String description;
    private EtatProjet etat;
    private LocalDate dateDebut;
    private Integer duree;

    // Pour l'encadrant, on veut juste ses infos de base
    private EncadrantDTO encadrant;

    // Pour les étudiants, liste simple
    private Set<EtudiantDTO> etudiants;
}
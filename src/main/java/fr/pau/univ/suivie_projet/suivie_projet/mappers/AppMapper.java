package fr.pau.univ.suivie_projet.suivie_projet.mappers;

import fr.pau.univ.suivie_projet.suivie_projet.dtos.*;
import fr.pau.univ.suivie_projet.suivie_projet.models.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AppMapper {

    // --- ETUDIANT ---
    public EtudiantDTO fromEtudiant(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(etudiant.getId());
        dto.setNom(etudiant.getNom());
        dto.setPrenom(etudiant.getPrenom());
        dto.setEmail(etudiant.getEmail());
        dto.setFiliere(etudiant.getFiliere());
        return dto;
    }

    // --- ENCADRANT ---
    public EncadrantDTO fromEncadrant(Encadrant encadrant) {
        if (encadrant == null) return null;
        EncadrantDTO dto = new EncadrantDTO();
        dto.setId(encadrant.getId());
        dto.setNom(encadrant.getNom());
        dto.setPrenom(encadrant.getPrenom());
        dto.setEmail(encadrant.getEmail());
        dto.setSpecialite(encadrant.getSpecialite());
        return dto;
    }

    // --- PROJET ---
    public ProjetDTO fromProjet(Projet projet) {
        ProjetDTO dto = new ProjetDTO();
        dto.setId(projet.getId());
        dto.setTitre(projet.getTitre());
        dto.setDescription(projet.getDescription());
        dto.setEtat(projet.getEtat());
        dto.setDateDebut(projet.getDateDebut());
        dto.setDuree(projet.getDuree());

        // Mapping de l'encadrant
        dto.setEncadrant(fromEncadrant(projet.getEncadrant()));

        // Mapping des étudiants (Stream pour transformer la liste)
        if (projet.getEtudiants() != null) {
            dto.setEtudiants(
                    projet.getEtudiants().stream()
                            .map(this::fromEtudiant)
                            .collect(Collectors.toSet())
            );
        }
        return dto;
    }
}
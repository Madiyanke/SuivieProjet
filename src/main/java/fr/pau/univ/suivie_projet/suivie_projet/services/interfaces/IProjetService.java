package fr.pau.univ.suivie_projet.suivie_projet.services.interfaces;



import fr.pau.univ.suivie_projet.suivie_projet.dtos.ProjetDTO;
import fr.pau.univ.suivie_projet.suivie_projet.models.Projet;

import java.util.List;

public interface IProjetService {
    ProjetDTO createProjet(Projet projet, Long idEncadrant);
    void affecterEtudiantAuProjet(Long idProjet, Long idEtudiant);
    void changerEncadrant(Long idProjet, Long idNouvelEncadrant);

    List<ProjetDTO> getAllProjets();
    List<ProjetDTO> getProjetsEncadrant(Long idEncadrant);
    List<ProjetDTO> getProjetsEtudiant(Long idEtudiant);

    void supprimerProjet(Long id);
    // Dans l'interface ProjetService
    ProjetDTO updateProjet(Long id, Projet projet, Long idEncadrant);

    void retirerEtudiantDuProjet(Long idProjet, Long idEtudiant);
}

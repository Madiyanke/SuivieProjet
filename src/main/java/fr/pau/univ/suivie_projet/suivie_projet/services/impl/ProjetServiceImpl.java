package fr.pau.univ.suivie_projet.suivie_projet.services.impl;


import fr.pau.univ.suivie_projet.suivie_projet.dtos.ProjetDTO;
import fr.pau.univ.suivie_projet.suivie_projet.mappers.AppMapper;
import fr.pau.univ.suivie_projet.suivie_projet.models.Encadrant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Etudiant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Projet;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.EncadrantRepository;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.EtudiantRepository;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.ProjetRepository;
import fr.pau.univ.suivie_projet.suivie_projet.services.interfaces.IProjetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjetServiceImpl implements IProjetService {

    private final ProjetRepository projetRepository;
    private final EncadrantRepository encadrantRepository;
    private final EtudiantRepository etudiantRepository;
    private final AppMapper appMapper;

    @Override
    public ProjetDTO createProjet(Projet projet, Long idEncadrant) {
        Encadrant encadrant = encadrantRepository.findById(idEncadrant)
                .orElseThrow(() -> new EntityNotFoundException("Encadrant non trouvé"));

        // Utilisation de la méthode helper pour la cohérence
        projet.setEncadrant(encadrant); // IMPORTANT : Gère la relation des deux côtés

        Projet savedProjet = projetRepository.save(projet);
        return appMapper.fromProjet(savedProjet);
    }

    @Override
    public void affecterEtudiantAuProjet(Long idProjet, Long idEtudiant) {
        Projet projet = projetRepository.findById(idProjet)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant non trouvé"));

        // La méthode helper gère l'ajout dans le Set du projet ET le Set de l'étudiant
        projet.addEtudiant(etudiant);

        // Le @Transactional sauvegardera automatiquement les changements à la fin de la méthode
        // grâce au mécanisme de "Dirty Checking" de Hibernate. Pas besoin de projetRepository.save() explicite ici,
        // mais le mettre ne fait pas de mal pour la lisibilité.
    }

    @Override
    public void changerEncadrant(Long idProjet, Long idNouvelEncadrant) {
        Projet projet = projetRepository.findById(idProjet)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));
        Encadrant nouvelEncadrant = encadrantRepository.findById(idNouvelEncadrant)
                .orElseThrow(() -> new EntityNotFoundException("Nouvel encadrant non trouvé"));

        projet.setEncadrant(nouvelEncadrant); // Gère le détachement de l'ancien et l'attachement du nouveau
    }

    @Override
    @Transactional(readOnly = true) // Optimisation pour la lecture
    public List<ProjetDTO> getAllProjets() {
        return projetRepository.findAll().stream()
                .map(appMapper::fromProjet)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjetDTO> getProjetsEncadrant(Long idEncadrant) {
        return projetRepository.findByEncadrant_Id(idEncadrant).stream()
                .map(appMapper::fromProjet)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjetDTO> getProjetsEtudiant(Long idEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant non trouvé"));

        // On passe par l'étudiant pour avoir ses projets (Lazy Loading géré par la transaction)
        return etudiant.getProjets().stream()
                .map(appMapper::fromProjet)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerProjet(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet introuvable avec l'id : " + id));

        if (projet.getEncadrant() != null) {
            projet.getEncadrant().getProjets().remove(projet);
            projet.setEncadrant(null);
        }

        for (Etudiant etudiant : projet.getEtudiants()) {
            etudiant.getProjets().remove(projet);
        }
        projet.getEtudiants().clear();

        projetRepository.delete(projet);
    }
    @Override
    public ProjetDTO updateProjet(Long id, Projet projetDetails, Long idEncadrant) {
        Projet projetExistant = projetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet introuvable id: " + id));

        projetExistant.setTitre(projetDetails.getTitre());
        projetExistant.setDescription(projetDetails.getDescription());
        projetExistant.setDateDebut(projetDetails.getDateDebut());
        projetExistant.setDuree(projetDetails.getDuree());
        projetExistant.setEtat(projetDetails.getEtat());

        // On vérifie d'abord si l'encadrant a changé pour éviter des requêtes inutiles
        if (projetExistant.getEncadrant() == null || !projetExistant.getEncadrant().getId().equals(idEncadrant)) {
            Encadrant nouvelEncadrant = encadrantRepository.findById(idEncadrant)
                    .orElseThrow(() -> new EntityNotFoundException("Encadrant introuvable id: " + idEncadrant));

            // Utilisation de la méthode helper qui gère la relation bidirectionnelle
            projetExistant.setEncadrant(nouvelEncadrant);
        }

        // 4. Sauvegarde et retour DTO
        return appMapper.fromProjet(projetRepository.save(projetExistant));
    }

    @Override
    public void retirerEtudiantDuProjet(Long idProjet, Long idEtudiant) {
        Projet projet = projetRepository.findById(idProjet)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant non trouvé"));

        // Utilisation de la méthode helper existante dans l'entité Projet
        projet.removeEtudiant(etudiant);

        projetRepository.save(projet);
    }
}

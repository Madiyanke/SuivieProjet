package fr.pau.univ.suivie_projet.suivie_projet.controllers;

import fr.pau.univ.suivie_projet.suivie_projet.dtos.ProjetDTO;
import fr.pau.univ.suivie_projet.suivie_projet.models.Projet;
import fr.pau.univ.suivie_projet.suivie_projet.services.interfaces.IProjetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjetController {

    private final IProjetService projetService;

    // Créer un projet et l'assigner directement à un encadrant
    // POST /api/projets?encadrantId=1
    @PostMapping
    public ResponseEntity<ProjetDTO> createProjet(@RequestBody Projet projet,
                                                  @RequestParam Long encadrantId) {
        ProjetDTO newProjet = projetService.createProjet(projet, encadrantId);
        return new ResponseEntity<>(newProjet, HttpStatus.CREATED);
    }

    // Assigner un étudiant à un projet
    // PUT /api/projets/1/etudiants/5
    @PutMapping("/{projetId}/etudiants/{etudiantId}")
    public ResponseEntity<Void> assignerEtudiant(@PathVariable Long projetId,
                                                 @PathVariable Long etudiantId) {
        projetService.affecterEtudiantAuProjet(projetId, etudiantId);
        return ResponseEntity.ok().build();
    }

    // Changer l'encadrant d'un projet
    // PUT /api/projets/1/encadrants/2
    @PutMapping("/{projetId}/encadrants/{encadrantId}")
    public ResponseEntity<Void> changerEncadrant(@PathVariable Long projetId,
                                                 @PathVariable Long encadrantId) {
        projetService.changerEncadrant(projetId, encadrantId);
        return ResponseEntity.ok().build();
    }

    // Lister tous les projets
    @GetMapping
    public ResponseEntity<List<ProjetDTO>> getAllProjets() {
        return ResponseEntity.ok(projetService.getAllProjets());
    }

    // Voir les projets d'un encadrant (Vue Encadrant)
    @GetMapping("/encadrant/{encadrantId}")
    public ResponseEntity<List<ProjetDTO>> getProjetsByEncadrant(@PathVariable Long encadrantId) {
        return ResponseEntity.ok(projetService.getProjetsEncadrant(encadrantId));
    }

    // Voir les projets d'un étudiant (Vue Etudiant)
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<ProjetDTO>> getProjetsByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(projetService.getProjetsEtudiant(etudiantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        projetService.supprimerProjet(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetDTO> updateProjet(
            @PathVariable Long id,
            @RequestBody Projet projet,
            @RequestParam Long encadrantId) {

        ProjetDTO updated = projetService.updateProjet(id, projet, encadrantId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{projetId}/etudiants/{etudiantId}")
    public ResponseEntity<Void> retirerEtudiant(@PathVariable Long projetId, @PathVariable Long etudiantId) {
        projetService.retirerEtudiantDuProjet(projetId, etudiantId);
        return ResponseEntity.noContent().build();
    }
}
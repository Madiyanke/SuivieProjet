package fr.pau.univ.suivie_projet.suivie_projet.controllers;


import fr.pau.univ.suivie_projet.suivie_projet.models.Administrateur;
import fr.pau.univ.suivie_projet.suivie_projet.models.Encadrant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Etudiant;
import fr.pau.univ.suivie_projet.suivie_projet.services.interfaces.ICompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@CrossOrigin("*") // Autorise les appels depuis Angular/React/Postman
public class CompteController {

    private final ICompteService compteService;

    // --- CRÉATION (ADMIN ONLY plus tard) ---

    @PostMapping("/etudiants")
    public ResponseEntity<Etudiant> createEtudiant(@RequestBody Etudiant etudiant) {
        // Note: En production, on recevrait un DTO d'inscription, pas l'entité directe.
        // Pour ce prototype, on accepte l'entité pour la création initiale.
        return new ResponseEntity<>(compteService.saveEtudiant(etudiant), HttpStatus.CREATED);
    }

    @PostMapping("/encadrants")
    public ResponseEntity<Encadrant> createEncadrant(@RequestBody Encadrant encadrant) {
        return new ResponseEntity<>(compteService.saveEncadrant(encadrant), HttpStatus.CREATED);
    }

    @PostMapping("/admins")
    public ResponseEntity<Administrateur> createAdmin(@RequestBody Administrateur admin) {
        return new ResponseEntity<>(compteService.saveAdmin(admin), HttpStatus.CREATED);
    }

    // --- LECTURE ---

    @GetMapping("/etudiants")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(compteService.getAllEtudiants());
    }

    @GetMapping("/encadrants")
    public ResponseEntity<List<Encadrant>> getAllEncadrants() {
        return ResponseEntity.ok(compteService.getAllEncadrants());
    }
}

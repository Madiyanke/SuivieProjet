package fr.pau.univ.suivie_projet.suivie_projet.config;
import fr.pau.univ.suivie_projet.suivie_projet.enums.EtatProjet;
import fr.pau.univ.suivie_projet.suivie_projet.models.Administrateur;
import fr.pau.univ.suivie_projet.suivie_projet.models.Encadrant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Etudiant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Projet;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.UtilisateurRepository;
import fr.pau.univ.suivie_projet.suivie_projet.services.interfaces.ICompteService;
import fr.pau.univ.suivie_projet.suivie_projet.services.interfaces.IProjetService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final ICompteService compteService;
    private final IProjetService projetService;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public void run(String... args) throws Exception {
        // On vérifie si la base est déjà remplie pour éviter les doublons
        if (utilisateurRepository.count() > 0) {
            return;
        }

        System.out.println("--- DÉBUT INITIALISATION DES DONNÉES ---");

        // 1. Création Admin
        Administrateur admin = new Administrateur();
        admin.setNom("Admin");
        admin.setPrenom("Super");
        admin.setEmail("admin@univ-pau.fr");
        admin.setMdp("admin123");
        compteService.saveAdmin(admin);

        // 2. Création Encadrants
        Encadrant e1 = new Encadrant();
        e1.setNom("Dupont");
        e1.setPrenom("Jean");
        e1.setEmail("jean.dupont@univ-pau.fr");
        e1.setMdp("pass123");
        e1.setSpecialite("IA & Big Data");
        e1 = compteService.saveEncadrant(e1);

        Encadrant e2 = new Encadrant();
        e2.setNom("Martin");
        e2.setPrenom("Sophie");
        e2.setEmail("sophie.martin@univ-pau.fr");
        e2.setMdp("pass123");
        e2.setSpecialite("Sécurité Réseaux");
        e2 = compteService.saveEncadrant(e2);

        // 3. Création Etudiants
        Etudiant et1 = new Etudiant();
        et1.setNom("Eleve");
        et1.setPrenom("Un");
        et1.setEmail("e1@univ-pau.fr");
        et1.setMdp("pass123");
        et1.setFiliere("Informatique");
        et1 = compteService.saveEtudiant(et1);

        Etudiant et2 = new Etudiant();
        et2.setNom("Eleve");
        et2.setPrenom("Deux");
        et2.setEmail("e2@univ-pau.fr");
        et2.setMdp("pass123");
        et2.setFiliere("Informatique");
        et2 = compteService.saveEtudiant(et2);

        // 4. Création Projets et Attribution
        Projet p1 = new Projet();
        p1.setTitre("Développement d'une IA de tri");
        p1.setDescription("Utilisation de Python et TensorFlow");
        p1.setDateDebut(LocalDate.now());
        p1.setEtat(EtatProjet.EN_COURS);

        // Création via le service (qui assigne l'encadrant)
        projetService.createProjet(p1, e1.getId());

        // Attribution des étudiants
        projetService.affecterEtudiantAuProjet(p1.getId(), et1.getId());
        projetService.affecterEtudiantAuProjet(p1.getId(), et2.getId());

        System.out.println("--- DONNÉES INITIALISÉES AVEC SUCCÈS ---");
    }
}

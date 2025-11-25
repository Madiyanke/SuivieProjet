package fr.pau.univ.suivie_projet.suivie_projet.models;

import fr.pau.univ.suivie_projet.suivie_projet.enums.EtatProjet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projet")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Evite les boucles hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private EtatProjet etat;
    private LocalDate dateDebut;
    private Integer duree; // en semaines ou mois

    // RELATION: Plusieurs projets -> 1 Encadrant
    @ManyToOne(fetch = FetchType.EAGER) // EAGER car on veut souvent voir le chef de projet
    @JoinColumn(name = "encadrant_id", nullable = false)
    private Encadrant encadrant;

    // RELATION: Plusieurs projets <-> Plusieurs Etudiants
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "projet_etudiant",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id")
    )
    private Set<Etudiant> etudiants = new HashSet<>();

    // --- Helper Methods (Crucial pour la cohérence mémoire/BDD) ---

    public void addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.getProjets().add(this);
    }

    public void removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.getProjets().remove(this);
    }

    public void assignerEncadrant(Encadrant encadrant) {
        // Gestion de l'ancien encadrant si existant
        if (this.encadrant != null) {
            this.encadrant.getProjets().remove(this);
        }
        this.encadrant = encadrant;
        if (encadrant != null) {
            encadrant.getProjets().add(this);
        }
    }
}
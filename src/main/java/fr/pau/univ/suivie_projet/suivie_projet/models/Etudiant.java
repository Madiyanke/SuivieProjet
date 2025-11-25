package fr.pau.univ.suivie_projet.suivie_projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etudiant")
@Data
@EqualsAndHashCode(callSuper = true) // Important pour l'héritage Lombok
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // Jointure sur la PK du parent
public class Etudiant extends Utilisateur {

    private String filiere;

    @ManyToMany(mappedBy = "etudiants")
    @JsonIgnore // On coupe la boucle JSON ici
    private Set<Projet> projets = new HashSet<>();
}
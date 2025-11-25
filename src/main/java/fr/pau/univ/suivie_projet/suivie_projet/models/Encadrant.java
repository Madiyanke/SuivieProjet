package fr.pau.univ.suivie_projet.suivie_projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "encadrant")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Encadrant extends Utilisateur {

    private String specialite;

    @OneToMany(mappedBy = "encadrant", fetch = FetchType.LAZY)
    @JsonIgnore // L'encadrant ne serialise pas la liste de ses projets par défaut (Performance)
    private Set<Projet> projets = new HashSet<>();
}
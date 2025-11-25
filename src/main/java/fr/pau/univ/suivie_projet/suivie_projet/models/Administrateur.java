package fr.pau.univ.suivie_projet.suivie_projet.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrateur")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Administrateur extends Utilisateur {
    // Champs spécifiques admin futurs (ex: niveauAccréditation, service, etc.)
}
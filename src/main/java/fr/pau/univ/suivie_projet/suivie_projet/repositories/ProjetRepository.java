package fr.pau.univ.suivie_projet.suivie_projet.repositories;

import fr.pau.univ.suivie_projet.suivie_projet.models.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    // Trouver les projets d'un encadrant spécifique
    List<Projet> findByEncadrant_Id(Long encadrantId);
    // Trouver les projets par état (ex: "EN_COURS")
    List<Projet> findByEtat(String etat);
}

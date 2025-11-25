package fr.pau.univ.suivie_projet.suivie_projet.repositories;

import fr.pau.univ.suivie_projet.suivie_projet.models.Encadrant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface EncadrantRepository extends JpaRepository<Encadrant,Long> {
    List<Encadrant> findBySpecialite(String specialite);

}

package fr.pau.univ.suivie_projet.suivie_projet.repositories;

import fr.pau.univ.suivie_projet.suivie_projet.models.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer>
{
}

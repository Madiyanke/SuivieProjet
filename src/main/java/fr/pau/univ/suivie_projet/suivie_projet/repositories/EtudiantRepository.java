package fr.pau.univ.suivie_projet.suivie_projet.repositories;


import fr.pau.univ.suivie_projet.suivie_projet.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    List<Etudiant> findByFiliere(String filiere);
}

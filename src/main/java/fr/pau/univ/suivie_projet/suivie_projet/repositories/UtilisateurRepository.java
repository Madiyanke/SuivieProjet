package fr.pau.univ.suivie_projet.suivie_projet.repositories;


import fr.pau.univ.suivie_projet.suivie_projet.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Spring Data JPA va générer la requête SQL automatiquement
    // C'est vital pour charger le UserDetails dans Spring Security
    Optional<Utilisateur> findByEmail(String email);

    // Vérifier l'existence (utile lors de l'inscription)
    boolean existsByEmail(String email);
}
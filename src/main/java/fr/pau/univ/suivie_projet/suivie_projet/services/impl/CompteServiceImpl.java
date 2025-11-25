package fr.pau.univ.suivie_projet.suivie_projet.services.impl;

import fr.pau.univ.suivie_projet.suivie_projet.enums.TypeDeRole;
import fr.pau.univ.suivie_projet.suivie_projet.models.Administrateur;
import fr.pau.univ.suivie_projet.suivie_projet.models.Encadrant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Etudiant;
import fr.pau.univ.suivie_projet.suivie_projet.models.Utilisateur;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.AdministrateurRepository;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.EncadrantRepository;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.EtudiantRepository;
import fr.pau.univ.suivie_projet.suivie_projet.repositories.UtilisateurRepository;
import fr.pau.univ.suivie_projet.suivie_projet.services.interfaces.ICompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // Toutes les méthodes sont transactionnelles par défaut
@RequiredArgsConstructor // Injection de dépendances par constructeur (Best Practice)
public class CompteServiceImpl implements ICompteService {

    private final EtudiantRepository etudiantRepository;
    private final EncadrantRepository encadrantRepository;
    private final AdministrateurRepository administrateurRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Etudiant saveEtudiant(Etudiant etudiant) {
        etudiant.setMdp(passwordEncoder.encode(etudiant.getMdp()));
        etudiant.setRole(TypeDeRole.ETUDIANT); // On force le rôle ici
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Encadrant saveEncadrant(Encadrant encadrant) {
        encadrant.setMdp(passwordEncoder.encode(encadrant.getMdp()));
        encadrant.setRole(TypeDeRole.ENCADRANT);
        return encadrantRepository.save(encadrant);
    }

    @Override
    public Administrateur saveAdmin(Administrateur admin) {
        admin.setMdp(passwordEncoder.encode(admin.getMdp()));
        admin.setRole(TypeDeRole.ADMIN);
        return administrateurRepository.save(admin);
    }

    @Override
    public Utilisateur findUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public List<Encadrant> getAllEncadrants() {
        return encadrantRepository.findAll();
    }
}
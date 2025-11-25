package fr.pau.univ.suivie_projet.suivie_projet.services.interfaces;


import fr.pau.univ.suivie_projet.suivie_projet.models.*;
import java.util.List;

public interface ICompteService {
    Etudiant saveEtudiant(Etudiant etudiant);
    Encadrant saveEncadrant(Encadrant encadrant);
    Administrateur saveAdmin(Administrateur admin);
    Utilisateur findUtilisateurByEmail(String email);
    List<Etudiant> getAllEtudiants();
    List<Encadrant> getAllEncadrants();
}

package fr.pau.univ.suivie_projet.suivie_projet.dtos;


import lombok.Data;

@Data
public class EtudiantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String filiere;
}
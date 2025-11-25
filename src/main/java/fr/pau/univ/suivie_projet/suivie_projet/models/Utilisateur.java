package fr.pau.univ.suivie_projet.suivie_projet.models;

import fr.pau.univ.suivie_projet.suivie_projet.enums.TypeDeRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED) // Stratégie clé pour l'héritage propre
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id; // "protected" pour être accessible par les enfants si besoin

    @Column(unique = true, nullable = false)
    private String email; // L'email sert de username

    @Column(name = "mot_de_passe", nullable = false)
    private String mdp;

    private String nom;
    private String prenom;

    private boolean actif = true;

    @Enumerated(EnumType.STRING)
    private TypeDeRole role;

    // --- Implémentation UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return mdp;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return actif; }
}
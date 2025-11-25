package fr.pau.univ.suivie_projet.suivie_projet.security;// Import de ton modèle
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import fr.pau.univ.suivie_projet.suivie_projet.models.Utilisateur;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    // Clé secrète (Toujours la même que précédemment)
    private static final String SECRET_KEY = "c3VwZXJfc2VjcmV0X2tleV9wb3VyX2xlX3Byb2pldF9kdV9zdWl2aWVfMTIzNDU2Nzg5";

    // --- 1. GÉNÉRATION DU TOKEN AVEC DONNÉES (C'est ici que ça se passe) ---
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();

        // A. Injection des Rôles (ex: ["ROLE_ADMIN"])
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        extraClaims.put("roles", roles);

        // B. Injection Nom/Prénom (Pour l'affichage frontend)
        if (userDetails instanceof Utilisateur) {
            Utilisateur user = (Utilisateur) userDetails;
            extraClaims.put("nom", user.getNom());
            extraClaims.put("prenom", user.getPrenom());
            extraClaims.put("id", user.getId()); // Utile pour les requêtes liées à l'ID
        }

        return generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // L'email est stocké dans le "subject"
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- 2. EXTRACTION ET VALIDATION (Standard) ---

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
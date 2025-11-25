package fr.pau.univ.suivie_projet.suivie_projet.config;

import fr.pau.univ.suivie_projet.suivie_projet.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer; // IMPORTANT
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // IMPORTANT
import org.springframework.web.cors.CorsConfigurationSource; // IMPORTANT
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // IMPORTANT

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Activer CORS (Cross-Origin Resource Sharing)
                .cors(Customizer.withDefaults())

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/comptes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/projets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/projets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/projets/**").hasAnyRole("ADMIN", "ENCADRANT")
                        .requestMatchers(HttpMethod.GET, "/api/projets/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 2. Configuration explicite pour autoriser Angular (Port 4200)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Autoriser l'origine Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Autoriser tous les verbes HTTP (GET, POST, PUT, DELETE, OPTIONS)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Autoriser les headers (Authorization pour le Token, Content-Type pour le JSON)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Autoriser l'envoi de credentials (cookies/headers auth)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Appliquer cette config à toutes les routes API
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
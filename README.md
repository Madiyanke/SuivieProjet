# SuiviProjet - Backend API

Ce dépôt contient le code source de la partie Serveur (Backend) de l'application de gestion de projets universitaires.

Il s'agit d'une API RESTful sécurisée, construite avec Java Spring Boot, qui gère la logique métier, la persistance des données et la sécurité des échanges.

 ## Stack Technique

Langage : Java 21 (LTS)

Framework : Spring Boot 3.3.x

Sécurité : Spring Security 6 (Stateless + JWT)

Base de Données : PostgreSQL

ORM : Spring Data JPA (Hibernate)

Build Tool : Maven

## Fonctionnalités Principales

### Authentification & Autorisation :

Système de login via JWT (JSON Web Tokens).

Gestion des rôles : ADMIN, ENCADRANT, ETUDIANT.

Protection des routes par filtre de sécurité.

### Gestion des Utilisateurs :

CRUD complet pour les étudiants et les encadrants.

Attribution de métadonnées (Filière pour étudiants, Spécialité pour encadrants).

### Gestion des Projets :

Création, modification, suppression de projets.

Attribution d'un encadrant responsable.

Affectation/Retrait d'étudiants sur les projets.

## Installation et Démarrage

Prérequis

JDK 21 installé.

Maven 3.8+ installé (ou utiliser le wrapper ./mvnw).

### 1. Cloner le projet

git clone https://github.com/Madiyanke/SuivieProjet.git

cd Suiviprojet


### 2. Configuration

Le fichier src/main/resources/application.properties est configuré vous pouvez le personaliser
3. Lancer l'application

### Via Maven Wrapper (Recommandé)
./mvnw spring-boot:run


L'API sera accessible sur : http://localhost:8080

### Comptes de Démonstration

Au démarrage, le fichier DataInit.java injecte automatiquement ces utilisateurs :

Administrateur

admin@univ-pau.fr

admin123

Encadrant

jean.dupont@univ-pau.fr

pass123

Étudiant

eleve1@univ-pau.fr

pass123

## Endpoints API Principaux

Auth : POST /api/auth/login

Projets : * GET /api/projets (Liste filtrée selon rôle)

POST /api/projets?encadrantId=X (Création)

PUT /api/projets/{id} (Modification)

Utilisateurs : GET /api/comptes/etudiants, GET /api/comptes/encadrants

# Règles de Gestion (Sécurité)

CORS : Configuré pour accepter les requêtes venant de http://localhost:4200 (Angular).

Stateless : Aucune session serveur. Le Token JWT doit être envoyé dans le header Authorization: Bearer <token> pour chaque requête.

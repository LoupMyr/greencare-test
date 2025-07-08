# Projet GreenCare - Gestion des Utilisateurs

## Objectif

Implémenter un système d'authentification pour l'application GreenCare avec :
- Inscription d'utilisateurs
- Connexion sécurisée
- Génération de tokens JWT

## Structure du projet

- **Entity** : `Utilisateur` (déjà complétée)
- **Repository** : `UtilisateurRepository` (déjà complété)
- **Services** : `AuthService` et `UserService` (déjà complétés)
- **Controllers** : `UserController` (déjà complété)
- **À compléter** : `AuthController` ⚠️

## Votre mission

Vous devez compléter les méthodes dans `AuthController.java` :

### 1. Méthode `register()`
- **Vérifier si l'utilisateur existe déjà** avec `userService.existsByEmail()`
- Si l'utilisateur existe, retourner une erreur "Utilisateur déjà existant"
- Vérifier si l'utilisateur a un rôle (sinon "USER" par défaut)  
- Créer l'utilisateur avec `userService.create()`
- Générer un token avec `authService.generateToken()`
- Retourner le token et l'utilisateur

### 2. Méthode `login()`
- Vérifier les identifiants avec `userService.verifyLogin()`
- Si valide : générer un token et retourner la réponse
- Sinon : retourner une erreur

## Configuration Base de Données

Le projet est configuré pour MariaDB :
- URL : `jdbc:mariadb://localhost:3306/greencare`
- Utilisateur : `root`
- Mot de passe : `password`

## Tests

Les routes à tester :
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion
- `GET /api/users` - Liste des utilisateurs

## Exemples de requête

```json
// POST /api/auth/register
{
  "nom": "John Doe",
  "email": "john@example.com",
  "motDePasse": "password123",
  "role": "USER"
}

// POST /api/auth/login
{
  "email": "john@example.com",
  "motDePasse": "password123"
}
```

## Gestion des erreurs

Votre code doit gérer ces cas d'erreur :

```json
// Utilisateur déjà existant
{
  "error": "Utilisateur déjà existant"
}

// Login incorrect
{
  "error": "Email ou mot de passe incorrect"
}
```

Bonne chance ! 🚀 
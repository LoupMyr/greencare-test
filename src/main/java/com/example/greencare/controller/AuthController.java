package com.example.greencare.controller;

import com.example.greencare.model.Utilisateur;
import com.example.greencare.service.AuthService;
import com.example.greencare.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    private final UserService userService;
    
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    
    /**
     * EXERCICE : Implémentez l'inscription d'un utilisateur
     * 
     * Cette méthode doit :
     * 1. Vérifier si l'utilisateur existe déjà avec userService.existsByEmail()
     * 2. Si l'utilisateur existe, retourner une erreur "Utilisateur déjà existant"
     * 3. Vérifier si l'utilisateur a un rôle, sinon définir "USER" par défaut
     * 4. Créer l'utilisateur avec userService.create()
     * 5. Générer un token JWT avec authService.generateToken()
     * 6. Retourner une réponse avec le token et l'utilisateur
     * 7. Gérer les erreurs avec un try-catch
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utilisateur utilisateur) {
        // TODO: À compléter par l'étudiant
        return null;
    }
    
    /**
     * EXERCICE : Implémentez la connexion d'un utilisateur
     * 
     * Cette méthode doit :
     * 1. Vérifier les identifiants avec userService.verifyLogin()
     * 2. Si l'utilisateur existe, générer un token JWT
     * 3. Retourner une réponse avec le token et l'utilisateur
     * 4. Sinon, retourner une erreur "Email ou mot de passe incorrect"
     */
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur loginData) {
        // TODO: À compléter par l'étudiant
        return null;
    }
} 
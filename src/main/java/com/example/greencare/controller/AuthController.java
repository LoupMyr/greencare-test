package com.example.greencare.controller;

import com.example.greencare.model.Utilisateur;
import com.example.greencare.service.AuthService;
import com.example.greencare.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        try {
            // 1. Vérifier si l'utilisateur existe déjà
            if (userService.existsByEmail(utilisateur.getEmail())) {
                return ResponseEntity.badRequest().body("Utilisateur déjà existant");
            }

            // 3. Vérifier si l'utilisateur a un rôle, sinon définir "USER" par défaut
            if (utilisateur.getRole() == null || utilisateur.getRole().isEmpty()) {
                utilisateur.setRole("USER");
            }

            // 4. Créer l'utilisateur
            Utilisateur createdUser = userService.create(utilisateur);

            // 5. Générer un token JWT
            String token = authService.generateToken(createdUser.getEmail());

            // 6. Retourner une réponse avec le token et l'utilisateur
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("token", token);
            response.put("utilisateur", createdUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 7. Gérer les erreurs
            return ResponseEntity.status(500).body("Erreur lors de l'inscription : " + e.getMessage());
        }
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
        try {
            // 1. Vérifier les identifiants
            java.util.Optional<Utilisateur> userOpt = userService.verifyLogin(loginData.getEmail(), loginData.getMotDePasse());
            if (userOpt.isPresent()) {
                Utilisateur user = userOpt.get();
                // 2. Générer un token JWT
                String token = authService.generateToken(user.getEmail());
                // 3. Retourner une réponse avec le token et l'utilisateur
                java.util.Map<String, Object> response = new java.util.HashMap<>();
                response.put("token", token);
                response.put("utilisateur", user);
                return ResponseEntity.ok(response);
            } else {
                // 4. Sinon, retourner une erreur
                return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la connexion : " + e.getMessage());
        }
    }
} 
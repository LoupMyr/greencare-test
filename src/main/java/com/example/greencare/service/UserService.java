package com.example.greencare.service;

import com.example.greencare.model.Utilisateur;
import com.example.greencare.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UtilisateurRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public UserService(UtilisateurRepository repository) {
        this.repository = repository;
    }
    
    public Utilisateur create(Utilisateur user) {
        user.setMotDePasse(encoder.encode(user.getMotDePasse()));
        return repository.save(user);
    }
    
    public List<Utilisateur> listAll() {
        return repository.findAll();
    }
    
    public Optional<Utilisateur> getById(Long id) {
        return repository.findById(id);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public Optional<Utilisateur> verifyLogin(String email, String rawPassword) {
        return repository.findByEmail(email)
                .filter(u -> encoder.matches(rawPassword, u.getMotDePasse()));
    }
    
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
} 
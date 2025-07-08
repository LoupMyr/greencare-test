# GreenCare - Système de Gestion des Notes Étudiants

## 📖 Description

GreenCare est une application Spring Boot pour la gestion des notes des étudiants. Elle permet aux enseignants de créer des comptes étudiants, d'attribuer des notes et de suivre les performances académiques.

## 🚀 Fonctionnalités

### Authentification et Autorisation
- **Inscription/Connexion** : Système d'authentification JWT
- **Rôles** : Deux types d'utilisateurs (TEACHER, STUDENT)
- **Sécurité** : Protection des endpoints par rôle

### Gestion des Étudiants
- **CRUD Étudiants** : Création, lecture, mise à jour, suppression
- **Informations** : Nom, prénom, numéro étudiant, email, téléphone, adresse
- **Validation** : Unicité du numéro étudiant et de l'email

### Gestion des Notes
- **Attribution de notes** : Seuls les enseignants peuvent attribuer des notes
- **Calculs automatiques** : Pourcentage et note littérale (A-F)
- **Statistiques** : Moyennes par étudiant et par matière
- **Historique** : Suivi des notes avec horodatage

## 🛠️ Technologies Utilisées

- **Framework** : Spring Boot 3.4.3
- **Base de données** : H2 (développement), PostgreSQL (production)
- **Sécurité** : Spring Security + JWT
- **ORM** : Spring Data JPA
- **Documentation** : Lombok pour réduire le boilerplate
- **Tests** : JUnit 5, Mockito, Spring Boot Test

## 📁 Structure du Projet

```
src/
├── main/
│   ├── java/com/example/greencare/
│   │   ├── config/          # Configuration (Sécurité)
│   │   ├── controller/      # Contrôleurs REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── model/          # Entités JPA
│   │   ├── repository/     # Repositories JPA
│   │   ├── security/       # Classes de sécurité JWT
│   │   └── service/        # Logique métier
│   └── resources/
│       └── application.properties
└── test/
    ├── java/com/example/greencare/
    │   ├── controller/     # Tests des contrôleurs
    │   ├── integration/    # Tests d'intégration
    │   ├── model/         # Tests des modèles
    │   ├── security/      # Tests de sécurité
    │   └── service/       # Tests des services
    └── resources/
        └── application-test.properties
```

## 🔧 Installation et Démarrage

### Prérequis
- Java 17+
- Maven 3.6+

### Étapes d'installation

1. **Cloner le projet**
   ```bash
   git clone <repository-url>
   cd greencare
   ```

2. **Compiler le projet**
   ```bash
   mvn clean compile
   ```

3. **Exécuter les tests**
   ```bash
   mvn test
   ```

4. **Démarrer l'application**
   ```bash
   mvn spring-boot:run
   ```

L'application sera accessible sur `http://localhost:8080`

## 📊 Base de Données

### Console H2
En mode développement, la console H2 est accessible :
- **URL** : `http://localhost:8080/h2-console`
- **JDBC URL** : `jdbc:h2:mem:testdb`
- **Username** : `sa`
- **Password** : `password`

### Schéma de Base de Données

```sql
-- Table des utilisateurs
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Table des étudiants
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    student_number VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT
);

-- Table des notes
CREATE TABLE grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    score DOUBLE NOT NULL,
    max_score DOUBLE NOT NULL,
    comments TEXT,
    graded_at TIMESTAMP NOT NULL,
    graded_by BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (graded_by) REFERENCES users(id)
);
```

## 🔌 API Endpoints

### Authentification

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| POST | `/api/users` | Créer un utilisateur | Public |
| POST | `/api/auth/login` | Se connecter | Public |

### Gestion des Étudiants

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| POST | `/api/students` | Créer un étudiant | TEACHER |
| GET | `/api/students` | Lister tous les étudiants | TEACHER |
| GET | `/api/students/{id}` | Obtenir un étudiant par ID | TEACHER |
| GET | `/api/students/number/{studentNumber}` | Obtenir un étudiant par numéro | TEACHER |
| PUT | `/api/students/{id}` | Modifier un étudiant | TEACHER |
| DELETE | `/api/students/{id}` | Supprimer un étudiant | TEACHER |

### Gestion des Notes

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| POST | `/api/grades` | Attribuer une note | TEACHER |
| GET | `/api/grades` | Lister toutes les notes | TEACHER |
| GET | `/api/grades/{id}` | Obtenir une note par ID | TEACHER |
| GET | `/api/grades/student/{studentId}` | Notes d'un étudiant | TEACHER |
| GET | `/api/grades/subject/{subject}` | Notes par matière | TEACHER |
| GET | `/api/grades/student/{studentId}/average` | Moyenne d'un étudiant | TEACHER |
| PUT | `/api/grades/{id}` | Modifier une note | TEACHER |
| DELETE | `/api/grades/{id}` | Supprimer une note | TEACHER |

## 📝 Exemples d'Utilisation

### 1. Créer un compte enseignant
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teacher@example.com",
    "password": "password123",
    "role": "TEACHER"
  }'
```

### 2. Se connecter
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teacher@example.com",
    "password": "password123"
  }'
```

### 3. Créer un étudiant
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "firstName": "Jean",
    "lastName": "Dupont",
    "studentNumber": "STU001",
    "email": "jean.dupont@example.com",
    "phone": "01-23-45-67-89",
    "address": "123 Rue de la Paix, Paris"
  }'
```

### 4. Attribuer une note
```bash
curl -X POST http://localhost:8080/api/grades \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "studentId": 1,
    "subject": "Mathématiques",
    "score": 16.5,
    "maxScore": 20.0,
    "comments": "Très bon travail !"
  }'
```

## 🧪 Tests

Le projet inclut une suite complète de tests :

### Types de Tests
- **Tests unitaires** : Services, modèles, utilitaires
- **Tests d'intégration** : Contrôleurs avec MockMvc
- **Tests de sécurité** : JWT, authentification

### Exécution des Tests
```bash
# Tous les tests
mvn test

# Tests d'une classe spécifique
mvn test -Dtest=UserServiceTest

# Tests avec couverture
mvn clean test jacoco:report
```

### Couverture de Tests
- Services : >90%
- Contrôleurs : >85%
- Modèles : >95%
- Sécurité : >80%

## 🔒 Sécurité

### Configuration JWT
- **Algorithme** : HS512
- **Expiration** : 24 heures (configurable)
- **Secret** : Configuré dans `application.properties`

### Autorisations
- **TEACHER** : Accès complet (CRUD étudiants et notes)
- **STUDENT** : Accès limité (consultation future)

### Bonnes Pratiques
- Mots de passe chiffrés avec BCrypt
- Validation des entrées
- Protection CSRF désactivée pour l'API REST
- Headers de sécurité configurés

## 📈 Fonctionnalités Avancées

### Calculs Automatiques
```java
public Double getPercentage() {
    return (score / maxScore) * 100;
}

public String getLetterGrade() {
    double percentage = getPercentage();
    if (percentage >= 90) return "A";
    if (percentage >= 80) return "B";
    if (percentage >= 70) return "C";
    if (percentage >= 60) return "D";
    return "F";
}
```

### Statistiques
- Moyenne générale par étudiant
- Moyenne par matière et étudiant
- Historique des notes avec horodatage

## 🚦 États et Codes de Retour

| Code | Signification | Utilisation |
|------|---------------|-------------|
| 200 | OK | Succès |
| 401 | Unauthorized | Token manquant/invalide |
| 403 | Forbidden | Permissions insuffisantes |
| 404 | Not Found | Ressource introuvable |
| 500 | Internal Server Error | Erreur serveur |

## 🔄 Évolutions Futures

### Fonctionnalités Prévues
- [ ] Interface web avec Thymeleaf
- [ ] Notifications par email
- [ ] Export des notes en PDF/Excel
- [ ] Graphiques de performance
- [ ] Gestion des classes/groupes
- [ ] Système de commentaires détaillés

### Améliorations Techniques
- [ ] Cache Redis pour les performances
- [ ] Base de données PostgreSQL
- [ ] Containerisation Docker
- [ ] CI/CD avec GitHub Actions
- [ ] Monitoring avec Actuator

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/amazing-feature`)
3. Commit les changements (`git commit -m 'Add amazing feature'`)
4. Push vers la branche (`git push origin feature/amazing-feature`)
5. Ouvrir une Pull Request

## 📜 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 📞 Support

Pour toute question ou problème :
- Créer une issue sur GitHub
- Contacter l'équipe de développement

---

**Note** : Ce README est maintenu à jour avec chaque version. Consultez la section des releases pour les changements récents.

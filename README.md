# Stage-Connect

<div align="center">

![Stage-Connect Logo](app/src/main/res/drawable/logo.png)

**Une plateforme mobile moderne de connexion entre étudiants, recruteurs et établissements pour la gestion de stages**

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-green.svg)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-blue.svg)](https://developer.android.com/about/versions/nougat)

[Features](#features) • [Architecture](#architecture) • [Installation](#installation) • [Contributing](#contributing)

</div>

---

## À Propos

**Stage-Connect** est une application Android native développée en Kotlin qui facilite la mise en relation entre étudiants à la recherche de stages, recruteurs proposant des opportunités, et établissements d'enseignement supervisant leurs étudiants.

## Features

### 👨‍🎓 Pour les Étudiants

- **Recherche d'offres** avec filtres avancés (localisation, salaire, type de contrat)
- **CV numérique complet** (formation, expériences, projets, certifications, langues)
- **Candidature en un clic** aux offres de stage
- **Messagerie temps réel** avec les recruteurs
- **Suivi des candidatures** (en attente, acceptée, refusée)
- **Sauvegarde des favorites** et notifications push

### 👔 Pour les Recruteurs

- **Création et gestion** d'offres de stage
- **Réception et tri** des candidatures
- **Consultation** des profils complets des étudiants
- **Communication directe** avec les candidats

### 🏫 Pour les Établissements

- **Vue d'ensemble** des étudiants
- **Suivi** des candidatures et placements
- **Communication directe** avec les candidats et les recruteurs

---

## Technologies

### Core
- **Kotlin** 1.9+ - Langage principal
- **Jetpack Compose** - UI déclarative
- **Material Design 3** - Design system

### Architecture
- **Clean Architecture** + **MVVM**
- **Repository Pattern**
- **Use Cases**

### Jetpack Components
- **Hilt/Dagger** - Injection de dépendances
- **Navigation Component** - Routage
- **ViewModel** + **StateFlow** - Gestion d'état réactive
- **Lifecycle** - Gestion du cycle de vie

### Networking
- **Retrofit** - Client REST
- **OkHttp** - Client HTTP
- **STOMP WebSocket** - Messagerie temps réel
- **Kotlin Serialization** - Sérialisation JSON

### Firebase
- **Cloud Messaging** - Notifications push
- **Authentication** (optionnel)

---

## Architecture

Le projet suit **Clean Architecture** avec **MVVM** pour une séparation claire des responsabilités.

```
Presentation Layer (Compose + ViewModel + Navigation)
          ↕
Domain Layer (UseCases + Models + Result)
          ↕
Data Layer (Repositories + API/WebSocket)
```

### Structure des Packages

```
com.example.stageconnect/
├── data/
│   ├── di/                 # Hilt modules
│   ├── dtos/              # API models
│   ├── remote/
│   │   ├── api/           # Retrofit services
│   │   └── repository/    # Implementations
│   ├── local/             # Storage
│   ├── interceptor/       # Auth interceptor
│   └── firebase/          # FCM services
│
├── domain/
│   ├── model/             # Entities
│   ├── usecases/          # Business logic
│   └── result/            # Result wrapper
│
└── presentation/
    ├── components/        # Reusable UI
    ├── screens/          # Feature screens
    ├── navigation/       # NavHost
    └── viewmodels/       # State management
```

---

## Installation

### Prérequis

- **Android Studio** Hedgehog (2023.1.1)+
- **JDK** 17+
- **Min SDK** 24 (Android 7.0)
- **Target SDK** 34 (Android 14)
- Compte Firebase

### Étapes

1. **Cloner le repository**
```bash
git clone https://github.com/BELLILMohamedNadir/Stage-Connect.git
cd Stage-Connect
```

2. **Configurer Firebase**
   - Créez un projet sur [Firebase Console](https://console.firebase.google.com/)
   - Téléchargez `google-services.json`
   - Placez-le dans le dossier `app/`

3. **Configurer l'API Backend**

Créez `local.properties`:
```properties
sdk.dir=/path/to/Android/Sdk
BASE_URL=https://your-backend-api.com/api/
```

4. **Builder le projet**
```bash
./gradlew build
```

5. **Lancer l'application**
   - Ouvrez dans Android Studio
   - Connectez un appareil ou lancez un émulateur
   - Cliquez "Run" (Shift + F10)

---

## Build & Déploiement

### Build Debug
```bash
./gradlew assembleDebug
```

### Build Release
```bash
./gradlew assembleRelease
```

### APK Signé
1. Configurez `keystore.properties`:
```properties
storeFile=/path/to/keystore.jks
storePassword=your_password
keyAlias=your_alias
keyPassword=your_password
```

2. Builder:
```bash
./gradlew assembleRelease
```

---

## Tests

```bash
# Tests unitaires
./gradlew test

# Tests d'instrumentation
./gradlew connectedAndroidTest

# Rapport de couverture
./gradlew jacocoTestReport
```

---

## API Backend

L'application nécessite un backend REST avec ces endpoints:

### Authentification
- `POST /api/auth/register` - Inscription (multipart: photo)
- `POST /api/auth/authentication` - Connexion
- `POST /api/auth/refresh` - Rafraîchir le token

### Offres
- `GET /api/offer/{id}` - Détails d'une offre
- `GET /api/offer/student/{id}` - Offres visibles par un étudiant
- `GET /api/offer/recruiter/{id}` - Offres créées par un recruteur
- `GET /api/offer/student/saved/{id}` - Offres sauvegardées par un étudiant
- `POST /api/offer` - Créer une offre (recruteur)
- `PUT /api/offer/{id}` - Modifier une offre
- `POST /api/offer/save/{offerId}/{studentId}` - Sauvegarder une offre
- `POST /api/offer/un-save/{offerId}/{studentId}` - Retirer une offre sauvegardée

### Candidatures
- `GET /api/application/student/{id}` - Candidatures d'un étudiant
- `GET /api/application/recruiter/{id}` - Candidatures reçues par un recruteur
- `GET /api/application/establishment/{id}` - Candidatures d'un établissement
- `POST /api/application` - Postuler à une offre (multipart: CV)
- `PUT /api/application/{id}` - Mettre à jour le statut d'une candidature
- `DELETE /api/application/{applicationId}/{studentId}` - Retirer une candidature

### Profil Étudiant
- `POST /api/student` - Créer/Mettre à jour profil étudiant (multipart: photo)
- `POST /api/student/skills/{studentId}` - Ajouter des compétences
- `PUT /api/student/skills/{studentId}` - Modifier les compétences
- `PUT /api/student/photo/{userId}` - Uploader une photo de profil

### Profil Recruteur
- `POST /api/recruiter` - Créer/Mettre à jour profil recruteur (multipart: photo)

### Profil Établissement
- `GET /api/establishment/all` - Liste de tous les établissements
- `GET /api/establishment/students/{id}` - Étudiants d'un établissement
- `POST /api/establishment` - Créer/Mettre à jour établissement (multipart: photo)

### Formation
- `POST /api/education` - Ajouter une formation
- `GET /api/education/{id}` - Lister les formations
- `PUT /api/education/{id}` - Modifier une formation
- `DELETE /api/education/{id}` - Supprimer une formation

### Expérience Professionnelle
- `POST /api/work-experiences` - Ajouter une expérience
- `GET /api/work-experiences/{id}` - Lister les expériences
- `PUT /api/work-experiences/{id}` - Modifier une expérience
- `DELETE /api/work-experiences/{id}` - Supprimer une expérience

### Stage
- `POST /api/internship` - Ajouter un stage
- `GET /api/internship/{id}` - Lister les stages
- `PUT /api/internship/{id}` - Modifier un stage
- `DELETE /api/internship/{id}` - Supprimer un stage

### Projets
- `POST /api/project` - Ajouter un projet
- `GET /api/project/{id}` - Lister les projets
- `PUT /api/project/{id}` - Modifier un projet
- `DELETE /api/project/{id}` - Supprimer un projet

### Certifications
- `POST /api/certification` - Ajouter une certification
- `GET /api/certification/{id}` - Lister les certifications
- `PUT /api/certification/{id}` - Modifier une certification
- `DELETE /api/certification/{id}` - Supprimer une certification

### Langues
- `POST /api/language` - Ajouter une langue
- `GET /api/language/{id}` - Lister les langues
- `PUT /api/language/{id}` - Modifier une langue
- `DELETE /api/language/{id}` - Supprimer une langue

### Messagerie
- `GET /api/room/{userId}` - Liste des conversations d'un utilisateur
- `GET /api/room/{roomId}/messages` - Messages d'une conversation

### Fichiers
- `POST /api/files/upload/{userId}` - Uploader un fichier (multipart)
- `GET /api/files/{fileName}` - Télécharger un fichier

---

## Roadmap

- [ ] Room Database pour le cache offline
- [ ] Tests unitaires (couverture > 80%)
- [ ] Mode sombre
- [ ] Multi-langue (FR/EN/AR)
- [ ] Notifications push avancées
- [ ] Filtres de recherche sauvegardés
- [ ] Recommandations ML
- [ ] Version tablette
- [ ] Export PDF du CV

---

## Contributing

Les contributions sont les bienvenues!

1. **Fork** le projet
2. Créez votre branche (`git checkout -b feature/AmazingFeature`)
3. Committez (`git commit -m 'Add AmazingFeature'`)
4. Pushez (`git push origin feature/AmazingFeature`)
5. Ouvrez une **Pull Request**

### Standards de Code
- Conventions Kotlin officielles
- Ktlint pour le formatage
- Tests pour les nouvelles fonctionnalités
- Documentation des fonctions publiques

---

Copyright (c) 2025 BELLIL Mohamed Nadir

---

## Auteur

**BELLIL Mohamed Nadir**

- GitHub: [@BELLILMohamedNadir](https://github.com/BELLILMohamedNadir)
- LinkedIn: [Mohamed Nadir BELLIL](https://www.linkedin.com/in/mohamed-nadir-bellil-ab749a256/)
- Email: bellil.mohamednadir@gmail.com

---

## Remerciements

- [Android Jetpack](https://developer.android.com/jetpack)
- [Square](https://square.github.io/) - Retrofit & OkHttp
- [Google](https://firebase.google.com/) - Firebase
- [Dagger](https://dagger.dev/) - Hilt
- Communauté open-source Android

---

<div align="center">

**⭐ Si ce projet vous a aidé, n'hésitez pas à lui donner une étoile! ⭐**

Made by BELLIL Mohamed Nadir

</div>

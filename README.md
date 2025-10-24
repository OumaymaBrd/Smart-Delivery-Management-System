# Smart Delivery Management System

Système de gestion de livraisons pour SmartLogi utilisant **Spring Core avec configuration XML pure** (sans annotations).

## Architecture

Ce projet utilise une architecture en couches avec configuration XML complète:

- **Entity**: Entités JPA (Livreur, Colis)
- **Repository**: Interfaces Spring Data JPA
- **Service**: Logique métier avec injection de dépendances XML
- **Configuration**: `applicationContext.xml` pour toute la configuration Spring

## Types d'Injection de Dépendances

Le projet démontre les 3 types d'injection:

1. **Constructor Injection** - `LivreurService` (recommandé)
2. **Setter Injection** - `ColisService`
3. **Field Injection** - Non utilisé car nécessite des annotations

## Configuration Base de Données

Modifiez `applicationContext.xml` pour votre configuration MySQL:

\`\`\`xml
<property name="url" value="jdbc:mysql://localhost:3306/smart_delivery_db"/>
<property name="username" value="root"/>
<property name="password" value="votre_mot_de_passe"/>
\`\`\`

## Exécution

\`\`\`bash
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.smart_delivery.App"
\`\`\`

## Fonctionnalités

- ✅ CRUD Livreurs
- ✅ CRUD Colis
- ✅ Assignation de colis aux livreurs
- ✅ Mise à jour du statut des colis
- ✅ Validation des données
- ✅ Gestion des transactions
- ✅ Configuration XML pure (sans annotations @Component, @Service, etc.)

## Scopes des Beans

- `livreurService`: **singleton** (une seule instance)
- `colisService`: **prototype** (nouvelle instance à chaque appel)
- `deliveryValidator`: **singleton**

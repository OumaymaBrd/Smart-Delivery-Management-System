# 📦 Smart Delivery Management System

Système de gestion de livraisons pour **SmartLogi** utilisant **Spring Core avec configuration XML pure** et **Spring MVC REST API**.

---

## 📋 Table des Matières

- [Vue d'ensemble](#-vue-densemble)
- [Technologies](#-technologies)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Lancement](#-lancement)
- [API REST Documentation](#-api-rest-documentation)
- [Spring Core - Configuration XML](#-spring-core---configuration-xml)
- [Spring MVC - REST API](#-spring-mvc---rest-api)
- [Structure du Projet](#-structure-du-projet)

---

## 🎯 Vue d'ensemble

Ce projet est un système de gestion de livraisons qui permet de gérer des **livreurs** et des **colis** avec leurs statuts. Il démontre l'utilisation de **Spring Core avec configuration XML pure** (sans annotations @Component, @Service, @Repository) et expose les opérations CRUD via une **API REST Spring MVC**.

### Fonctionnalités

- ✅ Gestion complète des livreurs (CRUD)
- ✅ Gestion complète des colis (CRUD)
- ✅ Assignation de colis aux livreurs
- ✅ Mise à jour du statut des colis (PREPARATION, EN_COURS, LIVRE, ANNULE)
- ✅ API REST pour toutes les opérations
- ✅ Configuration XML pure (Spring Core)
- ✅ Validation des données métier
- ✅ Gestion des transactions

---

## 🛠 Technologies

### Backend
- **Java 8**
- **Spring Framework 5.3.31** (Core, Context, TX, ORM, Web MVC)
- **Spring Data JPA 2.7.18**
- **Hibernate 5.6.15.Final** (JPA Provider)
- **MySQL 8.0** (Base de données)
- **Maven** (Gestion des dépendances)

### Outils
- **Lombok** (Réduction du code boilerplate)
- **Jackson** (Sérialisation/Désérialisation JSON)
- **Tomcat 7** (Serveur d'application)

---

## 🏗 Architecture

Le projet suit une **architecture en couches** avec séparation des responsabilités :

\`\`\`
┌─────────────────────────────────────────┐
│         REST Controllers                │  ← Spring MVC
│  (LivreurController, ColisController)   │
└─────────────────┬───────────────────────┘
│
┌─────────────────▼───────────────────────┐
│            Services                     │  ← Logique métier
│  (LivreurService, ColisService)         │
└─────────────────┬───────────────────────┘
│
┌─────────────────▼───────────────────────┐
│              DAOs                       │  ← Accès aux données
│  (LivreurDao, ColisDao)                 │
└─────────────────┬───────────────────────┘
│
┌─────────────────▼───────────────────────┐
│         Entities (JPA)                  │  ← Modèle de données
│  (Livreur, Colis, StatutColis)          │
└─────────────────┬───────────────────────┘
│
┌─────────────────▼───────────────────────┐
│          MySQL Database                 │
└─────────────────────────────────────────┘
\`\`\`

### Couches

1. **Controllers** : Exposent les endpoints REST et gèrent les requêtes HTTP
2. **Services** : Contiennent la logique métier et orchestrent les DAOs
3. **DAOs** : Gèrent l'accès aux données via EntityManager
4. **Entities** : Représentent les tables de la base de données (POJOs)

---

## 📥 Installation

### Prérequis

- **Java 8+** installé
- **Maven 3.6+** installé
- **MySQL 8.0+** installé et en cours d'exécution
- **Git** (optionnel)

### Étapes

1. **Cloner le projet** (ou télécharger le ZIP)
   \`\`\`bash
   git clone <repository-url>
   cd smart_delivry_management
   \`\`\`

2. **Créer la base de données MySQL**
   \`\`\`sql
   CREATE DATABASE smart_delivery_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   \`\`\`

3. **Créer les tables** (optionnel - Hibernate peut les créer automatiquement)
   \`\`\`sql
   USE smart_delivery_db;

CREATE TABLE livreur (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(100) NOT NULL,
prenom VARCHAR(100) NOT NULL,
vehicule VARCHAR(50),
telephone VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE colis (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
reference VARCHAR(50) UNIQUE NOT NULL,
poids DOUBLE NOT NULL,
destination VARCHAR(255) NOT NULL,
statut VARCHAR(20) NOT NULL,
livreur_id BIGINT,
FOREIGN KEY (livreur_id) REFERENCES livreur(id)
);
\`\`\`

---

## ⚙️ Configuration

### 1. Configuration de la base de données

Modifiez `src/main/resources/META-INF/persistence.xml` :

\`\`\`xml
<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/smart_delivery_db"/>
<property name="javax.persistence.jdbc.user" value="root"/>
<property name="javax.persistence.jdbc.password" value="VOTRE_MOT_DE_PASSE"/>
\`\`\`

### 2. Configuration Hibernate

Le fichier `persistence.xml` contient également la configuration Hibernate :
- **Dialect** : MySQL57Dialect
- **DDL Auto** : `update` (crée/met à jour les tables automatiquement)
- **Show SQL** : `true` (affiche les requêtes SQL dans les logs)

---

## 🚀 Lancement

### Option 1 : Lancer avec Maven Tomcat Plugin (Recommandé)

\`\`\`bash
# Compiler et lancer le serveur Tomcat
mvn clean tomcat7:run
\`\`\`

Le serveur démarre sur **http://localhost:8080/smart-delivery**

### Option 2 : Déployer sur Tomcat externe

\`\`\`bash
# Créer le fichier WAR
mvn clean package

# Copier le WAR dans Tomcat
cp target/smart-delivery.war $TOMCAT_HOME/webapps/

# Démarrer Tomcat
$TOMCAT_HOME/bin/startup.sh
\`\`\`

### Vérification

Accédez à : **http://localhost:8080/smart-delivery/api/livreurs**

Vous devriez voir une liste JSON (vide au début).

---

## 📡 API REST Documentation

### Base URL
\`\`\`
http://localhost:8080/smart-delivery/api
\`\`\`

---

### 👤 Endpoints Livreurs

#### 1. Lister tous les livreurs
\`\`\`bash
GET /livreurs

curl http://localhost:8080/smart-delivery/api/livreurs
\`\`\`

**Réponse** :
\`\`\`json
[
{
"id": 1,
"nom": "Alami",
"prenom": "Mohammed",
"vehicule": "Scooter",
"telephone": "0612345678",
"colis": [
{
"id": 1,
"reference": "COL001",
"poids": 2.5,
"destination": "Casablanca",
"statut": "EN_COURS"
}
]
}
]
\`\`\`

#### 2. Récupérer un livreur par ID
\`\`\`bash
GET /livreurs/{id}

curl http://localhost:8080/smart-delivery/api/livreurs/1
\`\`\`

#### 3. Créer un nouveau livreur
\`\`\`bash
POST /livreurs
Content-Type: application/json

curl -X POST http://localhost:8080/smart-delivery/api/livreurs \
-H "Content-Type: application/json" \
-d '{
"nom": "Bennani",
"prenom": "Fatima",
"vehicule": "Voiture",
"telephone": "0698765432"
}'
\`\`\`

**⚠️ Important** : Ne pas inclure `id` ou `colis` lors de la création.

#### 4. Modifier un livreur
\`\`\`bash
PUT /livreurs/{id}
Content-Type: application/json

curl -X PUT http://localhost:8080/smart-delivery/api/livreurs/1 \
-H "Content-Type: application/json" \
-d '{
"nom": "Alami",
"prenom": "Mohammed",
"vehicule": "Moto",
"telephone": "0612345678"
}'
\`\`\`

#### 5. Supprimer un livreur
\`\`\`bash
DELETE /livreurs/{id}

curl -X DELETE http://localhost:8080/smart-delivery/api/livreurs/1
\`\`\`

---

### 📦 Endpoints Colis

#### 1. Lister tous les colis
\`\`\`bash
GET /colis

curl http://localhost:8080/smart-delivery/api/colis
\`\`\`

#### 2. Récupérer un colis par ID
\`\`\`bash
GET /colis/{id}

curl http://localhost:8080/smart-delivery/api/colis/1
\`\`\`

#### 3. Créer un nouveau colis
\`\`\`bash
POST /colis
Content-Type: application/json

curl -X POST http://localhost:8080/smart-delivery/api/colis \
-H "Content-Type: application/json" \
-d '{
"reference": "COL001",
"poids": 2.5,
"destination": "Casablanca",
"statut": "PREPARATION",
"livreur": {
"id": 1
}
}'
\`\`\`

**⚠️ Important** : Pour assigner un livreur, incluez uniquement son `id` dans l'objet `livreur`.

#### 4. Modifier un colis
\`\`\`bash
PUT /colis/{id}
Content-Type: application/json

curl -X PUT http://localhost:8080/smart-delivery/api/colis/1 \
-H "Content-Type: application/json" \
-d '{
"reference": "COL001",
"poids": 3.0,
"destination": "Rabat",
"statut": "EN_COURS",
"livreur": {
"id": 1
}
}'
\`\`\`

#### 5. Mettre à jour le statut d'un colis
\`\`\`bash
PATCH /colis/{id}/statut?statut=LIVRE

curl -X PATCH "http://localhost:8080/smart-delivery/api/colis/1/statut?statut=LIVRE"
\`\`\`

**Statuts disponibles** : `PREPARATION`, `EN_COURS`, `LIVRE`, `ANNULE`

#### 6. Lister les colis d'un livreur
\`\`\`bash
GET /colis/livreur/{livreurId}

curl http://localhost:8080/smart-delivery/api/colis/livreur/1
\`\`\`

#### 7. Supprimer un colis
\`\`\`bash
DELETE /colis/{id}

curl -X DELETE http://localhost:8080/smart-delivery/api/colis/1
\`\`\`

---

## 🌱 Spring Core - Configuration XML

### Principe

Ce projet utilise **Spring Core avec configuration XML pure**, sans aucune annotation de stéréotype (@Component, @Service, @Repository). Toute la configuration des beans et l'injection de dépendances sont définies dans les fichiers XML.

### Fichiers de configuration

#### 1. `applicationContext.xml`
Configuration principale Spring Core :
- DataSource (connexion MySQL)
- EntityManagerFactory (JPA/Hibernate)
- TransactionManager (gestion des transactions)
- Beans des DAOs, Services, et Validators

#### 2. `persistence.xml`
Configuration JPA standard :
- Persistence Unit "smartDeliveryPU"
- Propriétés Hibernate
- Référence au fichier `orm.xml`

#### 3. `orm.xml`
Mappings JPA en XML (sans annotations @Entity, @Table, etc.) :
- Définition des entités Livreur et Colis
- Relations one-to-many et many-to-one
- Colonnes et contraintes

### Types d'Injection de Dépendances

Le projet démontre les **3 types d'injection** supportés par Spring :

#### 1. **Constructor Injection** (Recommandé)
\`\`\`xml
<bean id="livreurService" class="org.example.smart_delivry.service.LivreurService">
<constructor-arg ref="livreurDao"/>
</bean>
\`\`\`

**Avantages** :
- Immutabilité des dépendances
- Dépendances obligatoires garanties
- Facilite les tests unitaires

#### 2. **Setter Injection**
\`\`\`xml
<bean id="colisService" class="org.example.smart_delivry.service.ColisService">
<property name="colisDao" ref="colisDao"/>
<property name="livreurDao" ref="livreurDao"/>
</bean>
\`\`\`

**Avantages** :
- Dépendances optionnelles
- Reconfiguration possible après création

#### 3. **Field Injection**
Non utilisé dans ce projet car nécessite des annotations (@Autowired).

### Scopes des Beans

\`\`\`xml
<!-- Singleton (par défaut) - Une seule instance partagée -->
<bean id="livreurService" class="..." scope="singleton"/>

<!-- Prototype - Nouvelle instance à chaque injection -->
<bean id="colisService" class="..." scope="prototype"/>
\`\`\`

**Scopes utilisés** :
- `livreurService` : **singleton** (une seule instance)
- `colisService` : **prototype** (nouvelle instance à chaque appel)
- `deliveryValidator` : **singleton**

### Gestion des Transactions

Les transactions sont gérées de manière **déclarative** via XML :

\`\`\`xml
<tx:advice id="txAdvice" transaction-manager="transactionManager">
<tx:attributes>
<tx:method name="enregistrer*" propagation="REQUIRED"/>
<tx:method name="modifier*" propagation="REQUIRED"/>
<tx:method name="supprimer*" propagation="REQUIRED"/>
<tx:method name="trouver*" read-only="true"/>
<tx:method name="lister*" read-only="true"/>
</tx:attributes>
</tx:advice>
\`\`\`

**Propagation** :
- `REQUIRED` : Crée une transaction si elle n'existe pas
- `read-only="true"` : Optimisation pour les lectures

---

## 🌐 Spring MVC - REST API

### Principe

Spring MVC expose les services métier sous forme d'**API REST** avec sérialisation JSON automatique.

### Configuration

#### 1. `web.xml`
Configuration du servlet container :
- DispatcherServlet (point d'entrée Spring MVC)
- Mapping `/api/*` pour les endpoints REST
- Chargement du contexte Spring Core

\`\`\`xml
<servlet>
<servlet-name>dispatcher</servlet-name>
<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/api/*</url-pattern>
</servlet-mapping>
\`\`\`

#### 2. `dispatcher-servlet.xml`
Configuration Spring MVC :
- Component scan des contrôleurs
- Message converters (Jackson pour JSON)
- Hibernate5Module (gestion des lazy collections)

\`\`\`xml
<context:component-scan base-package="org.example.smart_delivry.controller"/>

<mvc:annotation-driven>
<mvc:message-converters>
<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
<property name="objectMapper" ref="objectMapper"/>
</bean>
</mvc:message-converters>
</mvc:annotation-driven>
\`\`\`

### Contrôleurs REST

Les contrôleurs utilisent les annotations Spring MVC :

\`\`\`java
@RestController
@RequestMapping("/livreurs")
public class LivreurController {

    private final LivreurService livreurService;
    
    @GetMapping
    public ResponseEntity<List<Livreur>> getAllLivreurs() {
        return ResponseEntity.ok(livreurService.listerTousLesLivreurs());
    }
    
    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(livreurService.enregistrerLivreur(livreur));
    }
}
\`\`\`

### Sérialisation JSON

**Jackson** gère automatiquement la conversion Java ↔ JSON :

- `@JsonIgnore` : Ignore un champ lors de la sérialisation (évite les références circulaires)
- `@JsonIgnoreProperties(ignoreUnknown = true)` : Ignore les champs JSON inconnus
- `Hibernate5Module` : Gère les collections lazy-loaded Hibernate

### Gestion des erreurs

Les contrôleurs retournent des codes HTTP appropriés :
- `200 OK` : Succès
- `201 CREATED` : Ressource créée
- `204 NO CONTENT` : Suppression réussie
- `404 NOT FOUND` : Ressource introuvable
- `400 BAD REQUEST` : Données invalides
- `500 INTERNAL SERVER ERROR` : Erreur serveur

---

## 📁 Structure du Projet

\`\`\`
smart_delivry_management/
│
├── src/
│   ├── main/
│   │   ├── java/org/example/smart_delivry/
│   │   │   ├── controller/          # Contrôleurs REST (Spring MVC)
│   │   │   │   ├── LivreurController.java
│   │   │   │   └── ColisController.java
│   │   │   │
│   │   │   ├── service/             # Services métier
│   │   │   │   ├── LivreurService.java
│   │   │   │   ├── ColisService.java
│   │   │   │   └── DeliveryValidator.java
│   │   │   │
│   │   │   ├── dao/                 # DAOs (accès données)
│   │   │   │   ├── LivreurDao.java
│   │   │   │   └── ColisDao.java
│   │   │   │
│   │   │   ├── entity/              # Entités JPA (POJOs)
│   │   │   │   ├── Livreur.java
│   │   │   │   └── Colis.java
│   │   │   │
│   │   │   ├── enums/               # Énumérations
│   │   │   │   └── StatutColis.java
│   │   │   │
│   │   │   └── App.java             # Classe principale (tests)
│   │   │
│   │   ├── resources/
│   │   │   ├── META-INF/
│   │   │   │   ├── persistence.xml  # Configuration JPA
│   │   │   │   └── orm.xml          # Mappings JPA XML
│   │   │   │
│   │   │   └── applicationContext.xml  # Configuration Spring Core
│   │   │
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── web.xml          # Configuration Servlet
│   │           └── dispatcher-servlet.xml  # Configuration Spring MVC
│   │
│   └── test/                        # Tests unitaires (à implémenter)
│
├── pom.xml                          # Dépendances Maven
├── README.md                        # Ce fichier
├── LANCEMENT_SERVEUR.md            # Guide de lancement
└── EXEMPLES_API.md                 # Exemples d'utilisation API
\`\`\`

---

## 🎓 Concepts Clés

### Spring Core
- ✅ **Inversion of Control (IoC)** : Spring gère le cycle de vie des objets
- ✅ **Dependency Injection (DI)** : Les dépendances sont injectées par Spring
- ✅ **Configuration XML** : Aucune annotation de stéréotype
- ✅ **Scopes** : Singleton et Prototype
- ✅ **Transactions déclaratives** : Gestion automatique des transactions

### Spring MVC
- ✅ **DispatcherServlet** : Front Controller pattern
- ✅ **@RestController** : Contrôleurs REST
- ✅ **@RequestMapping** : Mapping des URLs
- ✅ **@RequestBody / @ResponseBody** : Sérialisation JSON automatique
- ✅ **ResponseEntity** : Contrôle des réponses HTTP

### JPA / Hibernate
- ✅ **EntityManager** : API JPA standard
- ✅ **JPQL** : Requêtes orientées objet
- ✅ **Lazy Loading** : Chargement différé des relations
- ✅ **Transactions** : ACID garanties
- ✅ **Mappings XML** : Configuration sans annotations

---

## 🐛 Dépannage

### Erreur : "EntityPathResolver must not be null"
**Solution** : Vérifiez que vous utilisez Spring Data JPA 2.7.x (pas 3.x) avec Spring 5.3.x

### Erreur : "LazyInitializationException"
**Solution** : Le Hibernate5Module est configuré pour gérer les collections lazy. Vérifiez `dispatcher-servlet.xml`

### Erreur : HTTP 400 lors de POST
**Solution** : Vérifiez que vous envoyez `Content-Type: application/json` et que le JSON est valide

### Erreur : "package jakarta.persistence does not exist"
**Solution** : Utilisez `javax.persistence` avec Hibernate 5.x (pas `jakarta.persistence`)

### Erreur : Références circulaires JSON
**Solution** : `@JsonIgnore` est configuré sur `Colis.livreur` pour éviter les cycles

---

## 📚 Ressources

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/2.7.x/reference/html/)
- [Hibernate Documentation](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html)
- [Jackson Documentation](https://github.com/FasterXML/jackson-docs)

---

## 👨‍💻 Auteur

Projet développé pour démontrer l'utilisation de **Spring Core avec configuration XML pure** et **Spring MVC REST API**.

---

## 📄 Licence

Ce projet est à usage éducatif.

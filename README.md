# 📦 Smart Delivery Management System

Système de gestion de livraisons pour **SmartLogi** utilisant **Spring Core avec configuration XML pure** et **Spring MVC REST API**.

---
## 🔧 Prérequis et Technologies

<div align="center">

### 💻 Technologies Utilisées

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring-5.3.31-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring"/>
  <img src="https://img.shields.io/badge/Hibernate-5.6.15-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate"/>
  <img src="https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Tomcat-7.0.47-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black" alt="Tomcat"/>
  <img src="https://img.shields.io/badge/Lombok-1.18.34-BC4521?style=for-the-badge&logo=lombok&logoColor=white" alt="Lombok"/>
  <img src="https://img.shields.io/badge/Jackson-2.15.3-000000?style=for-the-badge" alt="Jackson"/>
  <img src="https://img.shields.io/badge/Jakarta_EE-3.1.0-007396?style=for-the-badge" alt="Jakarta"/>
  <img src="https://img.shields.io/badge/JUnit-4.13.2-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit"/>
  <img src="https://img.shields.io/badge/SLF4J-2.0.9-000000?style=for-the-badge" alt="SLF4J"/>
  <img src="https://img.shields.io/badge/Apidog-Latest-FF6C37?style=for-the-badge" alt="Apidog"/>
</p>
</div>
---

## 📊 Diagramme de Classes UML

Le diagramme ci-dessous illustre la structure complète du système avec les entités, les relations, et les couches architecturales.

![Diagramme de Classes](view/diagramme_classe.png)

### Description du Diagramme

#### Entités Principales

**Livreur**
- Représente un livreur avec ses informations personnelles
- Attributs : id, nom, prenom, vehicule, telephone
- Relation : Un livreur peut avoir plusieurs colis (One-to-Many)

**Colis**
- Représente un colis à livrer
- Attributs : id, destinataire, adresse, poids, statut
- Relation : Un colis appartient à un seul livreur (Many-to-One)

**StatutColis (Enum)**
- Énumération des statuts possibles d'un colis
- Valeurs : PREPARATION, EN_COURS, LIVRE, ANNULE

---

## 🌱 Spring Core - 

### Principe Fondamental

Ce projet utilise **Spring Core avec configuration XML pure**, sans aucune annotation de stéréotype (@Component, @Service, @Repository). Toute la configuration des beans et l'injection de dépendances sont définies dans les fichiers XML.


## 📡 API REST Documentation

### Base URL
\`\`\`
http://localhost:8080/smart-delivery/api
\`\`\`

![API Documentation](view/Api-Documentation.png)

## 🚀 Lancement du Serveur

###  Maven Tomcat Plugin (Recommandé)
\`\`\`bash
mvn clean tomcat7:run
\`\`\`

Le serveur démarre sur **http://localhost:8080/smart-delivery**

# Démarrer Tomcat
$TOMCAT_HOME/bin/startup.sh
\`\`\`

### Vérification
\`\`\`bash
curl http://localhost:8080/smart-delivery/api/livreurs
\`\`\`

---

## 🧪 Tests avec Apidog

#### 1. Créer un environnement

- **Nom** : Local Development
- **Base URL** : `http://localhost:8080/smart-delivery/api`

#### 2. Importer les endpoints

Créez les endpoints suivants dans Apidog :

**Livreurs**
- `GET` `/livreurs` - Liste tous les livreurs
- `GET` `/livreurs/{id}` - Récupérer un livreur
- `POST` `/livreurs` - Créer un livreur
- `PUT` `/livreurs/{id}` - Modifier un livreur
- `DELETE` `/livreurs/{id}` - Supprimer un livreur

**Colis**
- `GET` `/colis` - Liste tous les colis
- `GET` `/colis/{id}` - Récupérer un colis
- `POST` `/colis` - Créer un colis
- `PUT` `/colis/{id}` - Modifier un colis
- `PATCH` `/colis/{id}/statut` - Mettre à jour le statut
- `GET` `/colis/livreur/{livreurId}` - Colis d'un livreur
- `DELETE` `/colis/{id}` - Supprimer un colis


## 🎓 Concepts Clés Démontrés

### Spring Core
- ✅ Inversion of Control (IoC)
- ✅ Dependency Injection (Constructor & Setter)
- ✅ Configuration XML pure
- ✅ Bean Scopes (Singleton, Prototype)
- ✅ Transactions déclaratives

### Spring MVC
- ✅ DispatcherServlet
- ✅ @RestController
- ✅ @RequestMapping
- ✅ Sérialisation JSON automatique
- ✅ Gestion des erreurs HTTP

### JPA / Hibernate
- ✅ EntityManager
- ✅ JPQL
- ✅ Lazy Loading
- ✅ Mappings XML
- ✅ Relations bidirectionnelles

---

## 📚 Ressources

- [Spring Framework 5.3 Documentation](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/)
- [Spring Data JPA 2.7 Documentation](https://docs.spring.io/spring-data/jpa/docs/2.7.x/reference/html/)
- [Hibernate 5.6 User Guide](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html)
- [Apidog Documentation](https://apidog.com/help/)

---

## 👨‍💻 Auteur

Projet développé par Oumayma Bramid pour démontrer l'utilisation de **Spring Core avec configuration XML pure** et **Spring MVC REST API**.

---

## 📄 Licence

Ce projet est à usage éducatif.

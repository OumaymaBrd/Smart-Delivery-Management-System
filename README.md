# 📦 Smart Delivery Management System

Système de gestion de livraisons pour **SmartLogi** utilisant **Spring Core avec configuration XML pure** et **Spring MVC REST API**.

---

## 📋 Table des Matières

- [Prérequis et Extensions](#-prérequis-et-extensions)
- [Vue d'ensemble](#-vue-densemble)
- [Technologies](#-technologies)
- [Diagramme de Classes UML](#-diagramme-de-classes-uml)
- [Architecture du Projet](#-architecture-du-projet)
- [Spring Core - Configuration XML](#-spring-core---configuration-xml)
- [API REST Documentation](#-api-rest-documentation)
- [Installation et Configuration](#-installation-et-configuration)
- [Lancement du Serveur](#-lancement-du-serveur)
- [Tests avec Apidog](#-tests-avec-apidog)
- [Structure du Projet](#-structure-du-projet)
- [Dépannage](#-dépannage)

---

## 🔧 Prérequis et Extensions

### Outils Requis

| Outil | Version | Description |
|-------|---------|-------------|
| **Java JDK** | 17+ | Environnement d'exécution Java |
| **Maven** | 3.6+ | Gestionnaire de dépendances et build |
| **MySQL** | 8.0+ | Base de données relationnelle |
| **Tomcat** | 7.0+ | Serveur d'application (inclus via Maven plugin) |

### Extensions IDE Recommandées

#### Pour IntelliJ IDEA
- **Spring Core** - Support Spring Framework
- **Spring MVC** - Support Spring Web MVC
- **JPA Buddy** - Assistance JPA/Hibernate
- **Lombok** - Support annotations Lombok
- **Database Navigator** - Gestion bases de données

#### Pour VS Code
- **Extension Pack for Java** (Microsoft)
- **Spring Boot Extension Pack** (Pivotal)
- **Lombok Annotations Support**
- **XML Tools** - Édition fichiers XML Spring
- **REST Client** - Tests API REST

#### Pour Eclipse
- **Spring Tools 4** (STS)
- **Lombok** (installer via jar)
- **JPA Tools** (Dali)
- **Maven Integration** (m2e)


**Note importante** : Ce projet utilise `javax.persistence` (JPA 2.2) et non `jakarta.persistence` (JPA 3.0+) pour compatibilité avec Spring 5.x et Hibernate 5.x.

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
- ✅ Gestion des transactions déclaratives

---

## 🛠 Technologies

### Backend
- **Java 17**
- **Spring Framework 5.3.31** (Core, Context, TX, ORM, Web MVC)
- **Spring Data JPA 2.7.18**
- **Hibernate 5.6.15.Final** (JPA Provider)
- **MySQL 8.0** (Base de données)
- **Maven** (Gestion des dépendances)

### Outils
- **Lombok** (Réduction du code boilerplate)
- **Jackson** (Sérialisation/Désérialisation JSON)
- **Tomcat 7** (Serveur d'application)
- **Apidog** (Tests API REST)

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

#### Relations

\`\`\`
Livreur "1" ←──→ "0..*" Colis
\`\`\`

- **Cardinalité** : Un livreur peut avoir zéro ou plusieurs colis
- **Type** : Bidirectionnelle (navigable des deux côtés)
- **Cascade** : Les opérations sur Livreur peuvent se propager aux Colis
- **Lazy Loading** : Les colis sont chargés à la demande



### Responsabilités des Couches

| Couche | Responsabilité | Technologies |
|--------|----------------|--------------|
| **Presentation** | Gestion des requêtes HTTP, sérialisation JSON | Spring MVC, Jackson |
| **Métier** | Logique applicative, validation, transactions | Spring Core, Services |
| **Accès Données** | Opérations CRUD, requêtes JPQL | Spring Data JPA, DAOs |
| **Persistance** | Mapping objet-relationnel, gestion sessions | Hibernate, JPA |
| **Base de Données** | Stockage persistant des données | MySQL |

---

## 🌱 Spring Core - Configuration XML

### Principe Fondamental

Ce projet utilise **Spring Core avec configuration XML pure**, sans aucune annotation de stéréotype (@Component, @Service, @Repository). Toute la configuration des beans et l'injection de dépendances sont définies dans les fichiers XML.

### Fichiers de Configuration

#### 1. `applicationContext.xml` - Configuration Spring Core

\`\`\`xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:context="http://www.springframework.org/schema/context"
xmlns:tx="http://www.springframework.org/schema/tx"
xsi:schemaLocation="...">

    <!-- DataSource - Connexion MySQL -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/smart_delivery_db"/>
        <property name="username" value="root"/>
        <property name="password" value=""/>
    </bean>

    <!-- EntityManagerFactory - JPA/Hibernate -->
    <bean id="entityManagerFactory" 
          class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="persistenceUnitName" value="smartDeliveryPU"/>
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>
        </property>
    </bean>

    <!-- TransactionManager - Gestion des transactions -->
    <bean id="transactionManager" 
          class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!-- Activation des transactions déclaratives -->
    <tx:annotation-driven transaction-manager="transactionManager"/>

    <!-- EntityManager partagé pour les DAOs -->
    <bean id="entityManager" 
          class="org.springframework.orm.jpa.support.SharedEntityManagerBean">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!-- DAOs - Injection de l'EntityManager -->
    <bean id="livreurDao" class="org.example.smart_delivry.dao.LivreurDao">
        <property name="entityManager" ref="entityManager"/>
    </bean>

    <bean id="colisDao" class="org.example.smart_delivry.dao.ColisDao">
        <property name="entityManager" ref="entityManager"/>
    </bean>

    <!-- Services - Injection des DAOs -->
    <bean id="livreurService" 
          class="org.example.smart_delivry.service.LivreurService">
        <constructor-arg ref="livreurDao"/>
    </bean>

    <bean id="colisService" 
          class="org.example.smart_delivry.service.ColisService" 
          scope="prototype">
        <property name="colisDao" ref="colisDao"/>
        <property name="livreurDao" ref="livreurDao"/>
    </bean>

    <!-- Validator -->
    <bean id="deliveryValidator" 
          class="org.example.smart_delivry.service.DeliveryValidator"/>

</beans>
\`\`\`

#### 2. `persistence.xml` - Configuration JPA

\`\`\`xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.2">
    <persistence-unit name="smartDeliveryPU" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <!-- Référence au fichier de mappings XML -->
        <mapping-file>META-INF/orm.xml</mapping-file>
        
        <properties>
            <!-- Hibernate properties -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL57Dialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
\`\`\`

#### 3. `orm.xml` - Mappings JPA en XML

\`\`\`xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm" version="2.2">

    <!-- Entité Livreur -->
    <entity class="org.example.smart_delivry.entity.Livreur">
        <table name="livreur"/>
        <attributes>
            <id name="id">
                <generated-value strategy="IDENTITY"/>
            </id>
            <basic name="nom">
                <column name="nom" nullable="false"/>
            </basic>
            <basic name="prenom">
                <column name="prenom" nullable="false"/>
            </basic>
            <basic name="vehicule"/>
            <basic name="telephone">
                <column name="telephone" unique="true" nullable="false"/>
            </basic>
            <one-to-many name="colis" mapped-by="livreur" fetch="LAZY">
                <cascade>
                    <cascade-all/>
                </cascade>
            </one-to-many>
        </attributes>
    </entity>

    <!-- Entité Colis -->
    <entity class="org.example.smart_delivry.entity.Colis">
        <table name="colis"/>
        <attributes>
            <id name="id">
                <generated-value strategy="IDENTITY"/>
            </id>
            <basic name="destinataire">
                <column name="destinataire" nullable="false"/>
            </basic>
            <basic name="adresse">
                <column name="adresse" nullable="false"/>
            </basic>
            <basic name="poids">
                <column name="poids" nullable="false"/>
            </basic>
            <basic name="statut">
                <column name="statut" nullable="false"/>
                <enumerated>STRING</enumerated>
            </basic>
            <many-to-one name="livreur" fetch="LAZY">
                <join-column name="livreur_id"/>
            </many-to-one>
        </attributes>
    </entity>

</entity-mappings>
\`\`\`

### Types d'Injection de Dépendances

#### 1. Constructor Injection (Recommandé)

\`\`\`xml
<bean id="livreurService" class="org.example.smart_delivry.service.LivreurService">
<constructor-arg ref="livreurDao"/>
</bean>
\`\`\`

\`\`\`java
public class LivreurService {
private final LivreurDao livreurDao;

    // Injection par constructeur
    public LivreurService(LivreurDao livreurDao) {
        this.livreurDao = livreurDao;
    }
}
\`\`\`

**Avantages** :
- Immutabilité des dépendances (final)
- Dépendances obligatoires garanties
- Facilite les tests unitaires
- Évite les NullPointerException

#### 2. Setter Injection

\`\`\`xml
<bean id="colisService" class="org.example.smart_delivry.service.ColisService">
<property name="colisDao" ref="colisDao"/>
<property name="livreurDao" ref="livreurDao"/>
</bean>
\`\`\`

\`\`\`java
public class ColisService {
private ColisDao colisDao;
private LivreurDao livreurDao;

    // Injection par setter
    public void setColisDao(ColisDao colisDao) {
        this.colisDao = colisDao;
    }
    
    public void setLivreurDao(LivreurDao livreurDao) {
        this.livreurDao = livreurDao;
    }
}
\`\`\`

**Avantages** :
- Dépendances optionnelles possibles
- Reconfiguration après création
- Flexibilité pour les dépendances circulaires

### Scopes des Beans

\`\`\`xml
<!-- Singleton (par défaut) - Une seule instance partagée -->
<bean id="livreurService" class="..." scope="singleton"/>

<!-- Prototype - Nouvelle instance à chaque injection -->
<bean id="colisService" class="..." scope="prototype"/>
\`\`\`

| Scope | Description | Utilisation |
|-------|-------------|-------------|
| **singleton** | Une seule instance pour tout le conteneur | Services stateless, DAOs |
| **prototype** | Nouvelle instance à chaque demande | Services stateful, objets temporaires |

### Gestion des Transactions

Les transactions sont gérées de manière **déclarative** via XML :

\`\`\`xml
<tx:advice id="txAdvice" transaction-manager="transactionManager">
<tx:attributes>
<!-- Méthodes d'écriture : transaction requise -->
<tx:method name="enregistrer*" propagation="REQUIRED"/>
<tx:method name="modifier*" propagation="REQUIRED"/>
<tx:method name="supprimer*" propagation="REQUIRED"/>

        <!-- Méthodes de lecture : optimisation read-only -->
        <tx:method name="trouver*" read-only="true"/>
        <tx:method name="lister*" read-only="true"/>
    </tx:attributes>
</tx:advice>

<aop:config>
<aop:pointcut id="serviceMethods"
expression="execution(* org.example.smart_delivry.service.*.*(..))"/>
<aop:advisor advice-ref="txAdvice" pointcut-ref="serviceMethods"/>
</aop:config>
\`\`\`

**Propagation** :
- `REQUIRED` : Crée une transaction si elle n'existe pas, sinon rejoint l'existante
- `read-only="true"` : Optimisation pour les opérations de lecture seule

---

## 📡 API REST Documentation

### Base URL
\`\`\`
http://localhost:8080/smart-delivery/api
\`\`\`

![API Documentation](view/Api-Documentation.png)

## 🚀 Lancement du Serveur

### Option 1 : Maven Tomcat Plugin (Recommandé)
\`\`\`bash
mvn clean tomcat7:run
\`\`\`

Le serveur démarre sur **http://localhost:8080/smart-delivery**

### Option 2 : Déployer sur Tomcat externe
\`\`\`bash
# Créer le fichier WAR
mvn clean package

# Copier dans Tomcat
cp target/smart-delivery.war $TOMCAT_HOME/webapps/

# Démarrer Tomcat
$TOMCAT_HOME/bin/startup.sh
\`\`\`

### Vérification
\`\`\`bash
curl http://localhost:8080/smart-delivery/api/livreurs
\`\`\`

---

## 🧪 Tests avec Apidog

### Installation d'Apidog

1. Téléchargez Apidog depuis [apidog.com](https://apidog.com)
2. Installez l'application
3. Créez un nouveau projet "Smart Delivery Management"

### Configuration dans Apidog

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

#### 3. Exemple de test POST avec Apidog

**Endpoint** : `POST /livreurs`

**Headers** :
\`\`\`
Content-Type: application/json
\`\`\`

**Body (JSON)** :
\`\`\`json
{
"nom": "Martin",
"prenom": "Sophie",
"vehicule": "Moto",
"telephone": "0611223344"
}
\`\`\`

**Réponse attendue** : `201 Created`

### Captures d'écran Apidog

La capture d'écran ci-dessous montre l'interface Apidog avec tous les endpoints configurés :

![Apidog Interface](view/Api-Documentation.png)

---

## 📁 Structure du Projet

\`\`\`
smart_delivry_management/
│
├── src/
│   ├── main/
│   │   ├── java/org/example/smart_delivry/
│   │   │   ├── controller/              # Contrôleurs REST
│   │   │   │   ├── LivreurController.java
│   │   │   │   └── ColisController.java
│   │   │   │
│   │   │   ├── service/                 # Services métier
│   │   │   │   ├── LivreurService.java
│   │   │   │   ├── ColisService.java
│   │   │   │   └── DeliveryValidator.java
│   │   │   │
│   │   │   ├── dao/                     # DAOs
│   │   │   │   ├── LivreurDao.java
│   │   │   │   └── ColisDao.java
│   │   │   │
│   │   │   ├── entity/                  # Entités JPA
│   │   │   │   ├── Livreur.java
│   │   │   │   └── Colis.java
│   │   │   │
│   │   │   ├── enums/                   # Énumérations
│   │   │   │   └── StatutColis.java
│   │   │   │
│   │   │   └── App.java                 # Classe principale
│   │   │
│   │   ├── resources/
│   │   │   ├── META-INF/
│   │   │   │   ├── persistence.xml      # Configuration JPA
│   │   │   │   └── orm.xml              # Mappings JPA XML
│   │   │   │
│   │   │   └── applicationContext.xml   # Configuration Spring Core
│   │   │
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── web.xml              # Configuration Servlet
│   │           └── dispatcher-servlet.xml  # Configuration Spring MVC
│   │
│   └── test/                            # Tests unitaires
│
├── view/                                # Ressources visuelles
│   ├── diagramme_classe.png            # Diagramme UML
│   └── Api-Documentation.png           # Capture Apidog
│
├── pom.xml                              # Dépendances Maven
├── README.md                            # Ce fichier
├── DIAGRAMME_CLASSES.md                # Documentation UML
├── LANCEMENT_SERVEUR.md                # Guide de lancement
└── EXEMPLES_API.md                     # Exemples d'utilisation
\`\`\`

---

## 🐛 Dépannage

### Erreur : "Cannot find symbol: method builder()"
**Cause** : Les entités utilisaient @Builder qui a été retiré  
**Solution** : Utilisez les constructeurs et setters standards

### Erreur : HTTP 400 lors de POST
**Cause** : JSON invalide ou Content-Type manquant  
**Solution** : Vérifiez le header `Content-Type: application/json` et la structure JSON

### Erreur : HTTP 409 Conflict
**Cause** : Violation de contrainte unique (ex: téléphone déjà existant)  
**Solution** : Utilisez un numéro de téléphone différent

### Erreur : LazyInitializationException
**Cause** : Tentative d'accès à une collection lazy hors session  
**Solution** : Le Hibernate5Module est configuré pour gérer ce cas

### Erreur : "package jakarta.persistence does not exist"
**Cause** : Mauvaise version de JPA (3.0+ au lieu de 2.2)  
**Solution** : Utilisez `javax.persistence` avec Hibernate 5.x

---

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

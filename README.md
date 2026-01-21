# OpenClassrooms - Cursrus Dev Java #
Ce projet à été créé dans le cadre de ma formation **Développeur d'application Java** dispensée par [OpenClassrooms](https://openclassrooms.com/)

## Contexte
> ### Étudiant  : **Franck Mounier** ###
> ### Projet : P6 - Concevez une application web Java de A à Z ###
> ### Type : Livrable ###
> #### Repo source : empty project ####
> #### Date de démarrage du projet : 01/01/2026 ####
## Checklist

à rédiger

## Prérequis

- **Java 25+**
- **Maven 3.9+**
- **MySQL 8+**

## Installation de la base de données

1. Exécuter le script SQL :
   ```
   src/main/resources/SQL/paymybuddy_V2.sql
   ```

2. Créer un utilisateur MySQL `buddy` avec accès à la base `paymybuddy`, ou modifier les credentials dans `application.properties`

## Configuration

Fichier `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/paymybuddy
spring.datasource.username=buddy
spring.datasource.password=<votre_mot_de_passe>
```

## Lancement

```bash
mvn spring-boot:run
```

L'application est accessible sur `http://localhost:8080`

## Tests

```bash
mvn test
```

Rapport de couverture JaCoCo : `target/site/jacoco/index.html`

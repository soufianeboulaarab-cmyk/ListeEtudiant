# Configuration de l'application ListeEtudiant

## Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- Tomcat 10+
- MySQL Aiven accessible

## Configuration de la base de données

### Définir la variable d'environnement

L'application requiert que la variable d'environnement `DB_PASSWORD` soit définie avant le démarrage.

#### Sur Windows (PowerShell)

```powershell
$env:DB_PASSWORD="<votre_mot_de_passe_ici>"
```

#### Sur Windows (CMD)

```cmd
set DB_PASSWORD=<votre_mot_de_passe_ici>
```

#### Sur Linux/Mac (Bash)

```bash
export DB_PASSWORD="<votre_mot_de_passe_ici>"
```

### Ou via le fichier .env

Un fichier `.env` est fourni à la racine du projet avec la configuration nécessaire.

## Build de l'application

### Avec PowerShell

Vous pouvez utiliser le script d'aide fourni:

```powershell
.\build.ps1
```

Cela:
1. Charge les variables d'environnement depuis `.env`
2. Construit l'application avec Maven

### Manuellement

```bash
mvn clean package
```

## Déploiement sur Tomcat

1. Copiez le fichier WAR généré à partir de `target/` vers le répertoire `webapps` de Tomcat
2. Assurez-vous que la variable d'environnement `DB_PASSWORD` est définie dans le script de démarrage de Tomcat
3. Démarrez Tomcat

### Configuration de Tomcat (Windows)

Dans `CATALINA_HOME\bin\setenv.bat`, ajoutez:

```batch
@echo off
set "DB_PASSWORD=<votre_mot_de_passe_ici>"
```

### Configuration de Tomcat (Linux/Mac)

Dans `CATALINA_HOME/bin/setenv.sh`, ajoutez:

```bash
#!/bin/bash
export DB_PASSWORD="<votre_mot_de_passe_ici>"
```

## Dépannage

### Erreur: "Access denied for user 'avnadmin'@'...' (using password: NO)"

Cela signifie que la variable d'environnement `DB_PASSWORD` n'est pas définie. Assurez-vous de l'avoir définie avant de lancer l'application.

### Erreur: "Package ensa.ma.liste.model does not exist"

Assurez-vous que Java est configuré pour la version 17. Vérifiez votre `pom.xml`:

```xml
<maven.compiler.target>17</maven.compiler.target>
<maven.compiler.source>17</maven.compiler.source>
```

## Architecture

- **Model**: Classes de données (Etudiant)
- **DAO**: Couche d'accès aux données (EtudiantDAO)
- **Controller**: Contrôleurs JSF (ManagedEtudiant)
- **View**: Pages JSF (index.xhtml)

## Connexion à la base de données

- **Host**: mysql-2e43917c-soufianeboulaarab003-13d3.a.aivencloud.com
- **Port**: 22530
- **Database**: etudiantsdb
- **Username**: avnadmin
- **Password**: Défini via la variable d'environnement `DB_PASSWORD`
- **SSL**: Requis


#!/usr/bin/env powershell

# Script de redéploiement sur Tomcat
# Assurez-vous que TOMCAT_HOME est défini ou modifiez le chemin ci-dessous

param(
    [string]$TomcatHome = "C:\Program Files\Apache Software Foundation\Tomcat 11.0",
    [string]$WarFile = "C:\Users\blsb4\Desktop\liste\target\p5-1.0-SNAPSHOT.war"
)

Write-Host "====== REDÉPLOIEMENT SUR TOMCAT ======"
Write-Host ""

# Vérifier que le fichier WAR existe
if (-not (Test-Path $WarFile)) {
    Write-Host "ERREUR: Le fichier WAR n'existe pas: $WarFile"
    Write-Host "Veuillez d'abord construire l'application: mvn clean package"
    exit 1
}

Write-Host "Fichier WAR trouvé: $WarFile"
Write-Host ""

# Vérifier que Tomcat existe
if (-not (Test-Path "$TomcatHome\bin\shutdown.bat")) {
    Write-Host "ERREUR: Tomcat n'a pas été trouvé à: $TomcatHome"
    Write-Host "Veuillez modifier le chemin ou définir la variable TOMCAT_HOME"
    exit 1
}

Write-Host "Répertoire Tomcat: $TomcatHome"
Write-Host ""

# Vérifier la variable d'environnement DB_PASSWORD
if (-not $env:DB_PASSWORD) {
    Write-Host "ATTENTION: La variable d'environnement DB_PASSWORD n'est pas définie!"
    Write-Host "Assurez-vous de la définir avant de redémarrer Tomcat"
    Write-Host ""
}

Write-Host "1. Arrêt de Tomcat..."
& "$TomcatHome\bin\shutdown.bat"
Start-Sleep -Seconds 3

Write-Host ""
Write-Host "2. Suppression de l'ancienne application..."
Remove-Item "$TomcatHome\webapps\liste" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$TomcatHome\webapps\p5" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$TomcatHome\webapps\*.war" -Force -ErrorAction SilentlyContinue

Write-Host ""
Write-Host "3. Copie du nouveau WAR..."
Copy-Item $WarFile -Destination "$TomcatHome\webapps\"

Write-Host ""
Write-Host "4. Redémarrage de Tomcat..."
& "$TomcatHome\bin\startup.bat"

Write-Host ""
Write-Host "====== REDÉPLOIEMENT TERMINÉ ======"
Write-Host ""
Write-Host "L'application devrait être disponible à:"
Write-Host "  http://localhost:8080/p5-1.0-SNAPSHOT/"
Write-Host ""
Write-Host "Consultez les logs de Tomcat pour voir les messages de diagnostic:"
Write-Host "  $TomcatHome\logs\catalina.out"


@echo off
REM Script d'initialisation des variables d'environnement pour Tomcat
REM Ce fichier doit être placé dans CATALINA_HOME\bin\

REM Charger les variables d'environnement depuis .env
for /f "tokens=1,2 delims==" %%a in (.env) do (
    set "%%a=%%b"
)

REM Afficher les variables chargées
echo Variables d'environnement chargees:
if defined DB_PASSWORD (
    echo DB_PASSWORD=******* (défini)
) else (
    echo ATTENTION: DB_PASSWORD n'est pas défini!
)

REM Les variables d'environnement seront maintenant disponibles pour Tomcat


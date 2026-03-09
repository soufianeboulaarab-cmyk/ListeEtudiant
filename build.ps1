#!/usr/bin/env powershell

# Charger le fichier .env
$envFile = ".\.env"
if (Test-Path $envFile) {
    Get-Content $envFile | ForEach-Object {
        if ($_ -match "^\s*([^=]+)\s*=\s*(.+)$") {
            $key = $matches[1].Trim()
            $value = $matches[2].Trim()
            [Environment]::SetEnvironmentVariable($key, $value, "Process")
            Write-Host "Variable d'environnement définie: $key=$value"
        }
    }
} else {
    Write-Host "Fichier .env non trouvé!"
    exit 1
}

# Vérifier que DB_PASSWORD est défini
if (-not $env:DB_PASSWORD) {
    Write-Host "Erreur: Variable d'environnement DB_PASSWORD non définie!"
    exit 1
}

Write-Host "DB_PASSWORD a été défini avec succès"
Write-Host "Maintenance du build Maven..."

# Build Maven
mvn clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Erreur lors du build Maven!"
    exit 1
}

Write-Host "Build réussi! Vous pouvez maintenant déployer l'application."


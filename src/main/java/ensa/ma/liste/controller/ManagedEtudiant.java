package ensa.ma.liste.controller;

import ensa.ma.liste.dao.EtudiantDAO;
import ensa.ma.liste.model.Etudiant;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ManagedEtudiant implements Serializable {

    private List<Etudiant> etudiants;
    private String locked;
    public Etudiant etudiant = new Etudiant();
    private EtudiantDAO etudiantDAO;
    private boolean initialized = false;

    public ManagedEtudiant() {
        // Initialisation lazy pour éviter les erreurs lors de la création du bean
        etudiants = new ArrayList<>();
    }

    private void initializeDAO() throws SQLException {
        if (!initialized) {
            try {
                this.etudiantDAO = new EtudiantDAO();
                etudiants = etudiantDAO.getAll();
                initialized = true;
            } catch (RuntimeException e) {
                throw new SQLException("Erreur lors de l'initialisation de la base de données: " + e.getMessage(), e);
            }
        }
    }

    public void ajouter() throws SQLException {
        initializeDAO();
        Etudiant etudianttemp = new Etudiant();

        etudianttemp.setId(etudiant.getId());
        etudianttemp.setNom(etudiant.getNom());
        etudianttemp.setPrenom(etudiant.getPrenom());
        etudianttemp.setFiliere(etudiant.getFiliere());

        etudiantDAO.save(etudianttemp);
        etudiants = etudiantDAO.getAll();
    }

    public void supprimer(Etudiant etudiantt) throws SQLException {
        initializeDAO();
        etudiantDAO.deleteById(etudiantt.getId());
        etudiants = etudiantDAO.getAll();
    }

    public void modifier(Etudiant etudiantt) throws SQLException {
        initializeDAO();
        etudiantDAO.update(etudiantt);
        etudiantt.save();
    }

    public List<Etudiant> getEtudiants() {
        try {
            initializeDAO();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la liste des étudiants: " + e.getMessage());
            return new ArrayList<>();
        }
        return etudiants;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
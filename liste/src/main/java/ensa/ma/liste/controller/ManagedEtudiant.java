package ensa.ma.liste.controller;

import ensa.ma.liste.dao.EtudiantDAO;
import ensa.ma.liste.model.Etudiant;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ManagedEtudiant implements Serializable {

    private List<Etudiant> etudiants ;
    private String locked;
    public Etudiant etudiant = new Etudiant();
    private EtudiantDAO etudiantDAO = new EtudiantDAO();

    public ManagedEtudiant() throws SQLException {
        etudiants = etudiantDAO.getAll();
    }

    public void ajouter() throws SQLException {
        Etudiant etudianttemp = new Etudiant();

        etudianttemp.setId(etudiant.getId());
        etudianttemp.setNom(etudiant.getNom());
        etudianttemp.setPrenom(etudiant.getPrenom());
        etudianttemp.setFiliere(etudiant.getFiliere());

        etudiantDAO.save(etudianttemp);
        etudiants = etudiantDAO.getAll();
    }

    public void supprimer( Etudiant etudiantt) throws SQLException {
        etudiantDAO.deleteById(etudiantt.getId());
        etudiants = etudiantDAO.getAll();
    }

    public void modifier ( Etudiant etudiantt) throws SQLException {
        etudiantDAO.update(etudiantt);
        etudiantt.save();
    }
    public List<Etudiant> getEtudiants() { return etudiants; }

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
package ensa.ma.liste.dao;

import ensa.ma.liste.model.Etudiant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    private String jdbcURL = "jdbc:mysql://mysql-2e43917c-soufianeboulaarab003-13d3.a.aivencloud.com:22530/etudiantsdb?sslMode=REQUIRED";
    private String jdbcUsername = "avnadmin";
    private String jdbcPassword = System.getenv("DB_PASSWORD");

    private static final String INSERT_ETUDIANT_SQL =
            "INSERT INTO students (id, nom, prenom, filiere) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL_ETUDIANTS =
            "SELECT * FROM students";

    private static final String DELETE_ETUDIANT_SQL =
            "DELETE FROM students WHERE id = ?";

    private static final String UPDATE_ETUDIANT_SQL =
            "UPDATE students SET nom = ?, prenom = ?, filiere = ? WHERE id = ?";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL non trouvé", e);
        }

        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void save(Etudiant etudiant) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_ETUDIANT_SQL)) {

            preparedStatement.setInt(1, etudiant.getId());
            preparedStatement.setString(2, etudiant.getNom());
            preparedStatement.setString(3, etudiant.getPrenom());
            preparedStatement.setString(4, etudiant.getFiliere());

            preparedStatement.executeUpdate();
        }
    }

    public List<Etudiant> getAll() throws SQLException {
        List<Etudiant> etudiants = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SELECT_ALL_ETUDIANTS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String filiere = rs.getString("filiere");

                etudiants.add(new Etudiant(id, nom, prenom, filiere));
            }
        }
        return etudiants;
    }

    public boolean update(Etudiant etudiant) throws SQLException {
        boolean rowUpdated;

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_ETUDIANT_SQL)) {

            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setString(3, etudiant.getFiliere());
            statement.setInt(4, etudiant.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteById(int id) throws SQLException {
        boolean rowDeleted;

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_ETUDIANT_SQL)) {

            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
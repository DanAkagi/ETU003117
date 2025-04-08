package models;

import conn.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Compte {
    private int id;
    private String numCpt;
    private double solde;
    private boolean actif;

    // Constructors remain the same
    public Compte() {
    }

    public Compte(int id, String numCpt, double solde, boolean actif) {
        this.id = id;
        this.numCpt = numCpt;
        this.solde = solde;
        this.actif = actif;
    }

    public Compte(String numCpt, double solde, boolean actif) {
        this.numCpt = numCpt;
        this.solde = solde;
        this.actif = actif;
    }

    // Getters and Setters remain the same
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumCpt() {
        return numCpt;
    }

    public void setNumCpt(String numCpt) {
        this.numCpt = numCpt;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void save(Connection connection) throws SQLException {
        String sql = "INSERT INTO Compte (numCpt, solde, actif) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, this.numCpt);
            preparedStatement.setDouble(2, this.solde);
            preparedStatement.setBoolean(3, this.actif);
            preparedStatement.executeUpdate();
            
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        }
    }
    
    public void save() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            save(connection);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Compte getCompteByNum(String num, Connection connection) throws SQLException {
        String sql = "SELECT * FROM Compte WHERE numCpt = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, num);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Compte(
                    resultSet.getInt("id"),
                    resultSet.getString("numCpt"),
                    resultSet.getDouble("solde"),
                    resultSet.getBoolean("actif")
                );
            }
        }
        return null;
    }
    
    public static Compte getCompteByNum(String num) throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            return getCompteByNum(num, connection);
        }
    }

    public static List<Compte> readAll(Connection connection) throws SQLException {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM Compte";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                comptes.add(new Compte(
                    resultSet.getInt("id"),
                    resultSet.getString("numCpt"),
                    resultSet.getDouble("solde"),
                    resultSet.getBoolean("actif")
                ));
            }
        }
        return comptes;
    }
    
    public static List<Compte> readAll() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            return readAll(connection);
        }
    }

    public void update(Connection connection) throws SQLException {
        String sql = "UPDATE Compte SET numCpt = ?, solde = ?, actif = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.numCpt);
            preparedStatement.setDouble(2, this.solde);
            preparedStatement.setBoolean(3, this.actif);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        }
    }
    
    public void update() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            update(connection);
        }
    }

    public void delete(Connection connection) throws SQLException {
        String sql = "DELETE FROM Compte WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
        }
    }
    
    // Original method with auto-connection for backwards compatibility
    public void delete() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            delete(connection);
        }
    }
}
package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conn.DbConnect;

public class Mvt {
    private int id;
    private int compteId;
    private double montant;
    private String type; // 'debit' or 'credit'

    public Mvt() {
    }

    public Mvt(int compteId, double montant, String type) {
        this.compteId = compteId;
        this.montant = montant;
        this.type = type;
    }

    // Getters and setters remain the same
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Create with connection parameter
    public void save(Connection connection) throws SQLException {
        String sql = "INSERT INTO Mvt (compteId, montant, typeMvt) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.compteId);
            preparedStatement.setDouble(2, this.montant);
            preparedStatement.setString(3, this.type);
            preparedStatement.executeUpdate();
        }
    }
    
    // Original method with auto-connection for backwards compatibility
    public void save() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            save(connection);
        }
    }

    // Read with connection parameter
    public static Mvt read(int id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM Mvt WHERE id = ?";
        Mvt mvt = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mvt = new Mvt();
                mvt.setId(resultSet.getInt("id"));
                mvt.setCompteId(resultSet.getInt("compteId"));
                mvt.setMontant(resultSet.getDouble("montant"));
                mvt.setType(resultSet.getString("typeMvt"));
            }
        }
        return mvt;
    }
    
    // Original method with auto-connection for backwards compatibility
    public static Mvt read(int id) throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            return read(id, connection);
        }
    }

    // Read All with connection parameter
    public static List<Mvt> readAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Mvt";
        List<Mvt> mvts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Mvt mvt = new Mvt();
                mvt.setId(resultSet.getInt("id"));
                mvt.setCompteId(resultSet.getInt("compteId"));
                mvt.setMontant(resultSet.getDouble("montant"));
                mvt.setType(resultSet.getString("typeMvt"));
                mvts.add(mvt);
            }
        }
        return mvts;
    }
    
    // Original method with auto-connection for backwards compatibility
    public static List<Mvt> readAll() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            return readAll(connection);
        }
    }

    // Update with connection parameter
    public void update(Connection connection) throws SQLException {
        String sql = "UPDATE Mvt SET compteId=?, montant=?, typeMvt=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.compteId);
            preparedStatement.setDouble(2, this.montant);
            preparedStatement.setString(3, this.type);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        }
    }
    
    // Original method with auto-connection for backwards compatibility
    public void update() throws SQLException {
        try (Connection connection = DbConnect.getConnection()) {
            update(connection);
        }
    }

    // Delete with connection parameter
    public void delete(Connection connection) throws SQLException {
        String sql = "DELETE FROM Mvt WHERE id=?";
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
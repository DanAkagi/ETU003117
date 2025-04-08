package models;

import conn.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Mvt;

public class Prev {
    public int id;
    public int idCompte;
    public String libelle;
    public double valeur;

    public Prev() {

    }

    public Prev(int idCompte, String libelle, double valeur) {
        this.idCompte = idCompte;
        this.libelle = libelle;
        this.valeur = valeur;
    }

    public Prev(int id, int idCompte, String libelle, double valeur) {
        this.id = id;
        this.idCompte = idCompte;
        this.libelle = libelle;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getValeur() {
        return this.valeur;
    }
    
    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Compte getCompte(String num, Connection connection){
        try {
            return Compte.getCompteByNum(num, DbConnect.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save() throws SQLException,Exception {
        Prev checkprevisions = read(this.libelle, this.idCompte);
        if(checkprevisions != null){
            throw new Exception("La prévision que vous voulez créer existe déjà!");
        }
        String sql = "INSERT INTO previsions (idCompte,libelle,valeur) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.idCompte);
            preparedStatement.setString(2, this.libelle);
            preparedStatement.setDouble(3, this.valeur);
            preparedStatement.executeUpdate();
            Mvt move = new Mvt (this.idCompte, this.valeur, "credit");
            move.save();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM previsions WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE previsions SET idCompte=?, libelle=?, valeur=? WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.idCompte);
            preparedStatement.setString(2, this.libelle);
            preparedStatement.setDouble(3, this.valeur);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static Prev read(String libelle, int idCompte) throws SQLException {
        String sql = "SELECT * FROM previsions WHERE libelle = ? AND idCompte = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Prev Prev = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, libelle);
            preparedStatement.setInt(2, idCompte);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Prev = new Prev();
                Prev.setId(resultSet.getInt("id"));
                Prev.setLibelle(resultSet.getString("libelle"));
                Prev.setValeur(resultSet.getInt("valeur"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return Prev;
    }

    public static List<Prev> readAll() throws SQLException {
        String sql = "SELECT * FROM previsions";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Prev> Prevs = new ArrayList<>();

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Prev Prev = new Prev();
                Prev.setId(resultSet.getInt("id"));
                Prev.setLibelle(resultSet.getString("libelle"));
                Prev.setValeur(resultSet.getInt("valeur"));
                Prevs.add(Prev);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return Prevs;
    }
}


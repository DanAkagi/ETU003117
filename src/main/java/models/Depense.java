package models;

import conn.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Depense {
    public int id;
    public int idPrev;
    public String motif;
    public double montant;
    public String numCompte;

    public Depense() {

    }

    public Depense(int idPrev, String motif, double montant, String numCompte) {
        this.idPrev = idPrev;
        this.motif = motif;
        this.montant = montant;
        this.numCompte = numCompte;
    }

    public Depense(int id, int idPrev, String motif, double montant, String numCompte) {
        this.id = id;
        this.idPrev = idPrev;
        this.motif = motif;
        this.montant = montant;
        this.numCompte = numCompte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrev() {
        return idPrev;
    }

    public void setIdPrev(int id) {
        this.idPrev = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public double getMontant() {
        return this.montant;
    }
    
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public static List<Depense> readAllByPrevId(int prevId) throws SQLException {
        String sql = "SELECT * FROM depense WHERE idPrev = ?";
        List<Depense> depenses = new ArrayList<>();
        
        try (Connection connection = DbConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, prevId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Depense depense = new Depense();
                depense.setId(resultSet.getInt("id"));
                depense.setIdPrev(resultSet.getInt("idPrev"));
                depense.setMotif(resultSet.getString("motif"));
                depense.setMontant(resultSet.getDouble("montant"));
                depenses.add(depense);
            }
        }
        return depenses;
    }

    public void save() throws SQLException, Exception {
        // Récupérer le compte associé
        Compte cpt = Compte.getCompteByNum(this.numCompte);
        if (cpt == null) {
            throw new Exception("Compte non trouvé");
        }

        // Récupérer la prévision correspondante
        Prev prev = Prev.read(this.motif, cpt.getId());
        if (prev == null) {
            throw new Exception("Aucune prévision trouvée pour ce motif et compte");
        }

        // Calculer la somme des dépenses existantes pour ce motif et compte
        double sumDepenses = 0;
        List<Depense> depenses = readAllByPrevId(prev.getId());
        for (Depense depense : depenses) {
            sumDepenses += depense.getMontant();
        }

        // Vérifier si la nouvelle dépense dépasse la prévision
        if ((sumDepenses + this.montant) > prev.getValeur()) {
            throw new Exception("Le montant total des dépenses (" + (sumDepenses + this.montant) + 
                            ") dépasse la prévision (" + prev.getValeur() + ")");
        }

        // Si tout est OK, enregistrer la dépense
        String sql = "INSERT INTO depense (idPrev, motif, montant) VALUES (?, ?, ?)";
        try (Connection connection = DbConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, prev.getId());
            preparedStatement.setString(2, this.motif);
            preparedStatement.setDouble(3, this.montant);
            preparedStatement.executeUpdate();
            
            // Enregistrer le mouvement
            Mvt move = new Mvt(cpt.getId(), this.montant, "debit");
            move.save(connection);
        }
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM depense WHERE id=?";
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
        
        String sql = "UPDATE depense SET idPrev=?, motif=?, montant=? WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.idPrev);
            preparedStatement.setString(2, this.motif);
            preparedStatement.setDouble(3, this.montant);
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

    public static Depense read(String motif, int idPrev) throws SQLException {
        String sql = "SELECT * FROM depense WHERE motif = ? AND idPrev = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Depense depense = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, motif);
            preparedStatement.setInt(2, idPrev);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                depense = new Depense();
                depense.setId(resultSet.getInt("id"));
                depense.setMotif(resultSet.getString("motif"));
                depense.setMontant(resultSet.getInt("montant"));
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

        return depense;
    }

    public static List<Depense> readAll() throws SQLException {
        String sql = "SELECT * FROM depense";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Depense> depenses = new ArrayList<>();

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Depense depense = new Depense();
                depense.setId(resultSet.getInt("id"));
                depense.setMotif(resultSet.getString("motif"));
                depense.setMontant(resultSet.getInt("montant"));
                depenses.add(depense);
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
        return depenses;
    }
}


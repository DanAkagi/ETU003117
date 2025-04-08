package service;
import java.sql.Connection;
import java.sql.SQLException;

import conn.DbConnect;
import models.Compte;
import models.Mvt;

public class CompteService {
    public void debiterCompte(double montant, String numCpt) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnect.getConnection();
            connection.setAutoCommit(false);
            
            Compte compte = Compte.getCompteByNum(numCpt, connection);
            
            if(compte != null) {
                if(compte.isActif()) {
                    if(compte.getSolde() >= montant) {
                        compte.setSolde(compte.getSolde() - montant);
                        compte.update(connection); // Utilise la même connexion
                        
                        Mvt mvt = new Mvt(compte.getId(), montant, "debit");
                        mvt.save(connection);
                        
                        connection.commit();
                    } else {
                        throw new SQLException("Solde insuffisant");
                    }
                } else {
                    throw new SQLException("Compte inactif");
                }
            } else {
                throw new SQLException("Compte non trouvé");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Erreur lors du rollback: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new SQLException("Erreur lors de la fermeture de la connexion: " + e.getMessage());
                }
            }
        }
    }
}


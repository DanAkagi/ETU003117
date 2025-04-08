package servlet;

import java.io.*;
import java.sql.SQLException;
import jakarta.servlet.*; 
import jakarta.servlet.http.*; 

import models.Prev;
import models.Compte;
import models.Depense;

public class FormDepenseServlet extends HttpServlet { 
 
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            response.sendRedirect("depenseForm.jsp");
    } 
 
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException { 
        if(request.getParameter("nouvelleDepense") != null) {
            try {
                String numCompte = request.getParameter("numCompte");
                Compte account = Compte.getCompteByNum(numCompte);
                
                if(account == null) {
                    throw new Exception("Le compte spécifié n'existe pas");
                }
                
                String motif = request.getParameter("motif");
                Double montant = Double.parseDouble(request.getParameter("montant"));
                
                Prev prev = Prev.read(motif, account.getId());
                if(prev == null) {
                    throw new Exception("Aucune prévision trouvée pour ce motif");
                }
                
                Depense newDepense = new Depense(prev.getId(), motif, montant, numCompte);
                newDepense.save();
                
                request.setAttribute("success", "Dépense enregistrée avec succès");
                request.setAttribute("depense", newDepense);
            } catch (NumberFormatException e) {
                request.setAttribute("erreur", "Le montant doit être un nombre valide");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur lors de l'enregistrement: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("depenseResult.jsp").forward(request, response);
    }
 
    @Override 
    public String getServletInfo() { 
        return "Short description"; 
    } 
}
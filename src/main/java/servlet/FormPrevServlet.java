package servlet;

import java.io.*;
import java.sql.SQLException;
import jakarta.servlet.*; 
import jakarta.servlet.http.*; 

import models.Prev;
import models.Compte;

public class FormPrevServlet extends HttpServlet { 
 
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            response.sendRedirect("prevForm.jsp");
    } 
 
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException { 
        if(request.getParameter("nouvellePrevision") != null) {
            try {
                String numCompte = request.getParameter("numCompte");
                Compte account = Compte.getCompteByNum(numCompte);
                
                if(account == null) {
                    throw new Exception("Le compte spécifié n'existe pas");
                }
                
                String libelle = request.getParameter("libelle");
                Double valeur = Double.parseDouble(request.getParameter("valeur"));
                
                Prev newPrevision = new Prev(account.getId(), libelle, valeur);
                newPrevision.save();
                
                request.setAttribute("success", "Prévision créée avec succès");
                request.setAttribute("prev", newPrevision);
            } catch (NumberFormatException e) {
                request.setAttribute("erreur", "La valeur doit être un nombre valide");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur lors de la création: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("prevResult.jsp").forward(request, response);
    }
 
    @Override 
    public String getServletInfo() { 
        return "Short description"; 
    } 
}
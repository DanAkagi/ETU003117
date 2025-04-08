package servlet;

import java.io.*;
import jakarta.servlet.*; 
import jakarta.servlet.http.*; 

import models.Compte;

public class FormCompteServlet extends HttpServlet { 
 
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            response.sendRedirect("compteForm.jsp");
    } 
 
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException { 
        if(request.getParameter("nouveauCompte") != null) {
            try {
                String numCompte = request.getParameter("numCompte");
                Double solde = Double.parseDouble(request.getParameter("solde"));
                
                // Utilisation du bon constructeur (sans ID)
                Compte newAccount = new Compte(numCompte, solde, true);
                newAccount.save();
                
                request.setAttribute("success", "Compte créé avec succès");
                request.setAttribute("compte", newAccount);
            } catch (NumberFormatException e) {
                request.setAttribute("erreur", "Le solde doit être un nombre valide");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur lors de la création du compte: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("compteResult.jsp").forward(request, response);
    }
 
    @Override 
    public String getServletInfo() { 
        return "Short description"; 
    } 
}
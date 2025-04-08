package servlet;
import java.io.*;
import java.sql.SQLException;

import models.Dept;
import jakarta.servlet.*; 
import jakarta.servlet.http.*; 
 
public class DeptServlet extends HttpServlet { 
 
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        // try {
        //     // Récupérer la liste des départements
        //     request.setAttribute("depts", Dept.readAll());
        //     // Dispatcher vers index.html
        //     RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        //     dispatcher.forward(request, response);
        // } catch (SQLException e) {
        //     // Gérer l'erreur
        //     e.printStackTrace();
        //     request.setAttribute("erreur", "Erreur lors de la récupération des départements");
        //     RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        //     dispatcher.forward(request, response);
        // }
    } 
 
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        String nomDept = request.getParameter("NomDept");
        if(nomDept != null){
            try {
                Dept dept = new Dept();
                dept.setNomDept(nomDept);
                dept.save();
            } catch (SQLException e) {
                throw new ServletException(e.getMessage());
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        dispatcher.forward(request, response);
    } 
 
    @Override 
    public String getServletInfo() { 
        return "Short description"; 
    } 
}
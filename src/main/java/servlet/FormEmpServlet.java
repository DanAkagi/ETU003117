package servlet;

import java.io.*;
import java.sql.SQLException;
import jakarta.servlet.*; 
import jakarta.servlet.http.*; 

import models.Dept;
import models.Emp;
 
public class FormEmpServlet extends HttpServlet { 
 
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        try {
            // Récupérer la liste des départements
            request.setAttribute("depts", Dept.readAll());

            if(request.getParameter("idEmp") != null){
                Emp employee = Emp.read(Integer.parseInt(request.getParameter("idEmp")));
                request.setAttribute("employee", employee);
                request.setAttribute("deptEmp", Dept.read(employee.getDeptId()));
            }
            // Dispatcher vers index.html
            RequestDispatcher dispatcher = request.getRequestDispatcher("formEmp.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            // Gérer l'erreur
            e.printStackTrace();
        }
    } 
 
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 

    } 
 
    @Override 
    public String getServletInfo() { 
        return "Short description"; 
    } 
}
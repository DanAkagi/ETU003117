package servlet;

import java.io.*;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import models.Emp;

public class EmpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer la liste des départements
            request.setAttribute("emps", Emp.readAll());
            // Dispatcher vers index.html
            RequestDispatcher dispatcher = request.getRequestDispatcher("listeEmp.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            // Gérer l'erreur
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nomEmp = request.getParameter("NomEmp");
        int deptId = Integer.parseInt(request.getParameter("deptId"));
        if (nomEmp != null && deptId != 0) {
            try {
                Emp emp = new Emp();
                emp.setNomEmp(nomEmp);
                emp.setDeptId(deptId);
                emp.save();
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur lors de l'ajout du département");
            }
        }
        response.sendRedirect("FormEmpServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
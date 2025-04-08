<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Emp" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Employés</title>
    <link rel="stylesheet" href="assets/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <div class="container">
        <h1>Liste des Employés</h1>
    
        <% 
        List<Emp> emps = (List<Emp>)request.getAttribute("emps");
        if(emps != null && !emps.isEmpty()) {
        %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Département</th>
                        <th>Département ID</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(Emp emp : emps) { %>
                        <tr>
                            <td><%= emp.getId() %></td>
                            <td><%= emp.getNomEmp() %></td>
                            <td><%= emp.getDept(emp.getDeptId()).getNomDept() %></td>
                            <td><%= emp.getDeptId() %></td>
                            <td><a href="FormEmpServlet?id=<%= emp.getId() %>">Modifier</a>
                                <a href="EmpServlet?id=<%= emp.getId() %>">Supprimer</a> 
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p class="empty-message">Aucun employé trouvé dans la base de données.</p>
        <% } %>
        
        <div class="actions">
            <a href="FormEmpServlet">Ajouter un nouvel employé</a>
        </div>
</div>
    <script src="bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
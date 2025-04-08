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
        List<Emp> prevs = (List<Emp>)request.getAttribute("prevs");
        if(prevs != null && !prevs.isEmpty()) {
        %>
            <table>
                <thead>
                    <tr>
                        <th>ID compte</th>
                        <th>Libelle</th>
                        <th>Valeur</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(Prev prev : prevs) { %>
                        <tr>
                            <td><%= prev.getId() %></td>
                            <td><%= prev.getLibelles() %></td>
                            <td><%= prev.getValeur() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p class="empty-message">Aucun employé trouvé dans la base de données.</p>
        <% } %>
        
        <div class="actions">
            <a href="Formprevservlet">Ajouter un nouvel employé</a>
        </div>
</div>
    <script src="bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
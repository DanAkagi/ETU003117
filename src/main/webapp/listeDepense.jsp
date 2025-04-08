<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Depense" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Dépenses</title>
    <link rel="stylesheet" href="assets/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <div class="container">
        <h1>Liste des Dépenses</h1>
    
        <% 
        List<Depense> depenses = (List<Depense>)request.getAttribute("depenses");
        if(depenses != null && !depenses.isEmpty()) {
        %>
            <table>
                <thead>
                    <tr>
                        <th>Numéro de compte</th>
                        <th>Montant</th>
                        <th>Motif</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(Depense depense : depenses) { %>
                        <tr>
                            <td><%= depense.getNumCompte() %></td>
                            <td><%= depense.getMontant() %></td>
                            <td><%= depense.getMotif() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p class="empty-message">Aucune dépense trouvé dans la base de données.</p>
        <% } %>
        
        <div class="actions">
            <a href="FormDepenseservlet">Ajouter une nouvelle dépense</a>
        </div>
</div>
    <script src="bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
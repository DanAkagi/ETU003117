<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Dept" %>
<%@ page import="models.Compte" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire de nouveau compte</title>
    <link rel="stylesheet" href="assets/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <div class="container">
        <h1 class="text-center">Bienvenue sur FormDatabase</h1>
        <form action="FormCompteServlet" method="post" class="mb-3">
            <input type="hidden" name="nouveauCompte" id="nouveauCompte">
            <div class="form-group">
                <label for="numCompte">Nouveau compte</label>
                <input type="text" class="form-control" name="numCompte" id="numCompte" placeholder="Ex: 12345">
            </div>
            <div class="form-group">
                <label for="solde">Solde initial</label>
                <input type="number" class="form-control" name="solde" id="solde" placeholder="Ex: 100000">
            </div>
            <button type="submit" class="btn btn-primary">Valider</button>
        </form>
        <a href="FormEmpServlet" class="btn btn-link">Aller vers Formulaire Emp</a>
    </div>
    <script src="bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
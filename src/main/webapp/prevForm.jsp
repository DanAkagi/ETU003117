<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Dept" %>
<%@ page import="models.Compte" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire de prévision</title>
    <link rel="stylesheet" href="assets/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <div class="container">
        <h1 class="text-center">Bienvenue sur FormDatabase</h1>
        <form action="FormPrevServlet" method="post" class="mb-3">
            <input type="hidden" name="nouvellePrevision" id="nouvellePrevision">
            <div class="form-group">
                <label for="libelle">Nouvelle prévision</label>
                <input type="text" class="form-control" name="libelle" id="libelle" placeholder="Ex: Repas">
            </div>
            <div class="form-group">
                <label for="valeur">Montant</label>
                <input type="number" class="form-control" name="valeur" id="valeur" placeholder="Ex: 50000">
            </div>
            <div class="form-group">
                <label for="valeur">Numéro de compte correspondant</label>
                <input type="text" class="form-control" name="numCompte" id="numCompte" placeholder="Ex: 12345">
            </div>
            <button type="submit" class="btn btn-primary">Valider</button>
        </form>
        <a href="FormEmpServlet" class="btn btn-link">Aller vers Formulaire Emp</a>
    </div>
    <script src="bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
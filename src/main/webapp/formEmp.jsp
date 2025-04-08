<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Dept" %>
<%@ page import="models.Emp" %>
<%@ page import="java.util.List" %>
<%
Emp employee = null;
Dept deptEmployee = null;

if(request.getParameter("employee") != null) {
    employee = (Emp) request.getParameter("employee");
}

if(request.getParameter("deptEmp") != null) {
    deptEmployee = (Dept) request.getParameter("deptEmp");
}
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Employé</title>
    <link rel="stylesheet" href="assets/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <div class="container">
        <h1>Ajout d'un nouvel employé</h1>
    
        <form action="EmpServlet" method="post">
            <% 
            Emp emp = (Emp)request.getAttribute("emp");
            if(emp != null) { 
            %>
                <input type="hidden" name="empId" value="<%= emp.getId() %>">
            <% } %>
            
            <label for="NomEmp">Nom de l'employé:</label>
            <input type="text" id="NomEmp" name="NomEmp" value="<%= emp != null ? emp.getNomEmp() : "" %>" required>
            
            <label for="deptId">Département:</label>
            <select id="deptId" name="deptId" required>
                <% 
                List<Dept> depts = (List<Dept>)request.getAttribute("depts");
                if(depts != null) {
                    for(Dept dept : depts) {
                %>
                    <option value="<%= dept.getId() %>" 
                        <%= (emp != null && emp.getDeptId() == dept.getId()) ? "selected" : "" %>>
                        <%= dept.getNomDept() %>
                    </option>
                <% 
                    }
                }
                %>
            </select>
            
            <input type="submit" value="Valider">
        </form>
        
        <a href="EmpServlet">Voir la liste des employés</a>
    </div>
    <script src="bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
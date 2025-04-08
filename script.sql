create database formdatabase;
use formdatabase;

create table Dept(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomDept VARCHAR(100) NOT NULL
);

create table Emp(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomEmp VARCHAR(100) NOT NULL,
    deptId INT,
    FOREIGN KEY (deptId) REFERENCES Dept(id)
);

create table Compte(
    id INT AUTO_INCREMENT PRIMARY KEY,
    numCpt VARCHAR(100) NOT NULL,
    solde DECIMAL(10, 2) NOT NULL,
    actif BOOLEAN DEFAULT true
);

create table Mvt(
    id INT AUTO_INCREMENT PRIMARY KEY,
    compteId INT,
    montant DECIMAL(10, 2) NOT NULL,
    typeMvt ENUM('debit', 'credit') NOT NULL,
    FOREIGN KEY (compteId) REFERENCES Compte(id)
);

create table previsions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idCompte INT,
    libelle VARCHAR(200),
    valeur DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idCompte) REFERENCES Compte(id)
);

CREATE TABLE depense (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idPrev INT,
    montant DECIMAL(10, 2) NOT NULL,
    motif VARCHAR(50) NOT NULL,
    FOREIGN KEY (idPrev) REFERENCES previsions (id)
);
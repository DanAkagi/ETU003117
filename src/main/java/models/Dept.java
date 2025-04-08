package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conn.DbConnect;

public class Dept {
    public int id;
    public String nomDept;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomDept() {
        return nomDept;
    }

    public void setNomDept(String nomDept) {
        this.nomDept = nomDept;
    }

    public Dept() {

    }

    public Dept(String nomDept) {
        this.nomDept = nomDept;
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Dept (nomDept) VALUES (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomDept);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE Dept SET nomDept = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomDept);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static Dept read(int id) throws SQLException {
        String sql = "SELECT * FROM Dept WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Dept dept = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dept = new Dept();
                dept.setId(resultSet.getInt("id"));
                dept.setNomDept(resultSet.getString("nomDept"));
                
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return dept;
    }

    public static List<Dept> readAll() throws SQLException {
        String sql = "SELECT * FROM Dept";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Dept> depts = new ArrayList<>();

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Dept dept = new Dept();
                dept.setId(resultSet.getInt("id"));
                dept.setNomDept(resultSet.getString("nomDept"));
                depts.add(dept);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return depts;
    }
}

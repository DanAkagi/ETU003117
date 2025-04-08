package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conn.DbConnect;

public class Emp {
    public int id;
    public String nomEmp;
    public int deptId;

    public Emp() {

    }

    public Emp(String nomEmp, int deptId) {
        this.nomEmp = nomEmp;
        this.deptId = deptId;
    }

    public Emp(int id, String nomEmp, int deptId) {
        this.id = id;
        this.nomEmp = nomEmp;
        this.deptId = deptId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public int getDeptId() {
        return deptId;
    }

    public Dept getDept(int deptId){
        try {
            return Dept.read(deptId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Emp (nomEmp,deptId) VALUES (?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomEmp);
            preparedStatement.setInt(2, this.deptId);
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

    public void delete() throws SQLException {
        String sql = "DELETE FROM Emp WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
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
        String sql = "UPDATE Emp SET nomEmp=?, deptId=? WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomEmp);
            preparedStatement.setInt(2, this.deptId);
            preparedStatement.setInt(3, this.id);
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

    public static Emp read(int id) throws SQLException {
        String sql = "SELECT * FROM Emp WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Emp emp = null;

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                emp = new Emp();
                emp.setId(resultSet.getInt("id"));
                emp.setNomEmp(resultSet.getString("nomEmp"));
                emp.setDeptId(resultSet.getInt("deptId"));
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

        return emp;
    }

    public static List<Emp> readAll() throws SQLException {
        String sql = "SELECT * FROM Emp";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Emp> emps = new ArrayList<>();

        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Emp emp = new Emp();
                emp.setId(resultSet.getInt("id"));
                emp.setNomEmp(resultSet.getString("nomEmp"));
                emp.setDeptId(resultSet.getInt("deptId"));
                emps.add(emp);
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
        return emps;
    }
}

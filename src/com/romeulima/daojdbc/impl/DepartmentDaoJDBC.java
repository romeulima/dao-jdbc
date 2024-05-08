package com.romeulima.daojdbc.impl;

import com.romeulima.daojdbc.dao.department.DepartmentDao;
import com.romeulima.daojdbc.db.exceptions.DbException;
import com.romeulima.daojdbc.domain.department.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {


    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "INSERT INTO department " +
                            "(name) VALUES (?);"
            , Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, department.getName());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();

                if(rs.next()) {
                    int id = rs.getInt(1);
                    department.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows were affected");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void update(Department department) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE department " +
                            "SET name = ? WHERE id = ?;"
            );
            pstm.setString(1, department.getName());
            pstm.setInt(2, department.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "DELETE FROM department WHERE id = ?"
            );
            pstm.setInt(1, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 0) {
                throw new DbException("No affected rows");
            }
            System.out.println("Deleted!");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public Department findById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT * FROM department WHERE id = ?;"
            );
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                return dep;
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department obj = new Department();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));

        return obj;
    }

    @Override
    public List<Department> findAllDepartments() {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT * FROM department ORDER BY name;"
            );

            ResultSet rs = pstm.executeQuery();
            List<Department> list = new ArrayList<>();
            while (rs.next()) {
                Department obj = instantiateDepartment(rs);
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }


}

package com.romeulima.daojdbc.impl;

import com.romeulima.daojdbc.dao.seller.SellerDao;
import com.romeulima.daojdbc.db.exceptions.DbException;
import com.romeulima.daojdbc.domain.department.Department;
import com.romeulima.daojdbc.domain.seller.Seller;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "INSERT INTO seller "
                            + "(name, email, birthDate, baseSalary, departmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, seller.getName());
            pstm.setString(2, seller.getEmail());
            pstm.setDate(3, Date.valueOf(seller.getBirthDate()));
            pstm.setDouble(4, seller.getBaseSalary());
            pstm.setInt(5, seller.getDepartment().getId());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();

                if (rs.next()) {
                   int id = rs.getInt(1);
                   seller.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows were affected");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE seller "
                            + "SET name = ?, email = ?, birthDate = ?, baseSalary = ?, departmentId = ? "
                            + "WHERE id = ?");


            pstm.setString(1, seller.getName());
            pstm.setString(2, seller.getEmail());
            pstm.setDate(3, Date.valueOf(seller.getBirthDate()));
            pstm.setDouble(4, seller.getBaseSalary());
            pstm.setInt(5, seller.getDepartment().getId());
            pstm.setInt(6, seller.getId());

            pstm.executeUpdate();


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "DELETE FROM seller WHERE id = ?");
            pstm.setInt(1, id);

            int affectedRows = pstm.executeUpdate();

            if(affectedRows == 0) {
                throw new DbException("No rows affected");
            }
            System.out.println("Deletado");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Seller findById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT seller.*, department.name AS depName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.departmentId = department.id " +
                    "WHERE seller.id = ?;"
            );

            pstm.setInt(1 ,id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(dep, rs);

                return seller;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT seller.*, department.name AS depName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.departmentId = department.id " +
                            "ORDER BY name;");

            ResultSet rs = pstm.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department department = map.get(rs.getInt("departmentId"));

                if (Objects.isNull(department)) {
                    department = instantiateDepartment(rs);
                    map.put(rs.getInt("departmentId"), department);
                }
                Seller seller = instantiateSeller(department, rs);

                list.add(seller);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT seller.*, department.name AS depName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.departmentId = department.id " +
                            "WHERE seller.departmentId = ? " + "ORDER BY name;");
            pstm.setInt(1, dep.getId());
            ResultSet rs = pstm.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department department = map.get(rs.getInt("departmentId"));

                if (Objects.isNull(department)) {
                    department = instantiateDepartment(rs);
                    map.put(rs.getInt("departmentId"), department);
                }
                Seller seller = instantiateSeller(department, rs);

                list.add(seller);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Seller instantiateSeller(Department dep, ResultSet rs) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("id"));
        seller.setName(rs.getString("name"));
        seller.setEmail(rs.getString("email"));
        seller.setBirthDate(rs.getDate("birthDate").toLocalDate());
        seller.setBaseSalary(rs.getDouble("baseSalary"));
        seller.setDepartment(dep);

        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("departmentId"));
        dep.setName(rs.getString("depName"));

        return dep;
    }
}

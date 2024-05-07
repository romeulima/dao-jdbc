package com.romeulima.daojdbc.impl;

import com.romeulima.daojdbc.dao.seller.SellerDao;
import com.romeulima.daojdbc.db.exceptions.DbException;
import com.romeulima.daojdbc.domain.department.Department;
import com.romeulima.daojdbc.domain.seller.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void delete(Integer id) {

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

    private Seller instantiateSeller(Department dep, ResultSet rs) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("id"));
        seller.setName(rs.getString("name"));
        seller.setEmail(rs.getString("email"));
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

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}

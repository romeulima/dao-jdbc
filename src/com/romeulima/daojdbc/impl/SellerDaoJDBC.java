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
                int sellerId = rs.getInt("id");
                String sellerName = rs.getString("name");
                String sellerEmail = rs.getString("email");
                LocalDate sellerBirthDate = rs.getDate("birthDate").toLocalDate();
                double sellerBaseSalary = rs.getDouble("baseSalary");
                int sellerDepartmentId = rs.getInt("departmentId");
                String sellerDepartmentName = rs.getString("depName");

                return new Seller(sellerId, sellerName, sellerEmail, sellerBirthDate, sellerBaseSalary, new Department(sellerDepartmentId, sellerDepartmentName));
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}

package com.romeulima.daojdbc.dao.seller;

import com.romeulima.daojdbc.domain.seller.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller seller);
    void update(Seller seller);
    void delete(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}

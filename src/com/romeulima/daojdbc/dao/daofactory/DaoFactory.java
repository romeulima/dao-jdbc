package com.romeulima.daojdbc.dao.daofactory;

import com.romeulima.daojdbc.dao.department.DepartmentDao;
import com.romeulima.daojdbc.dao.seller.SellerDao;
import com.romeulima.daojdbc.db.DB;
import com.romeulima.daojdbc.impl.DepartmentDaoJDBC;
import com.romeulima.daojdbc.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}

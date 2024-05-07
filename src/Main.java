import com.romeulima.daojdbc.dao.daofactory.DaoFactory;
import com.romeulima.daojdbc.dao.seller.SellerDao;
import com.romeulima.daojdbc.domain.department.Department;
import com.romeulima.daojdbc.domain.seller.Seller;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

        System.out.println();
        System.out.println("=== TEST 2: seller findByDepartment ====");
        List<Seller> sellers = sellerDao.findByDepartment(new Department(2, null));

        sellers.forEach(System.out::println);

        System.out.println();
        System.out.println("=== TEST 3: seller findByAll ====");
        List<Seller> sellersList = sellerDao.findAll();

        sellersList.forEach(System.out::println);

    }
}
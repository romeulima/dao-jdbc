import com.romeulima.daojdbc.dao.daofactory.DaoFactory;
import com.romeulima.daojdbc.dao.seller.SellerDao;
import com.romeulima.daojdbc.domain.seller.Seller;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

    }
}
import com.romeulima.daojdbc.db.DB;
import com.romeulima.daojdbc.domain.department.Department;
import com.romeulima.daojdbc.domain.seller.Seller;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Connection connection = DB.getConnection();
        DB.closeConnection();

        Department obj = new Department(1, "TI");

        Seller seller = new Seller(1, "Romeu Lima", "romeu.sousadp@gmail.com", LocalDate.now(), 50000.00, obj);

        System.out.println(seller);
    }
}
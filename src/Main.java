import com.romeulima.daojdbc.dao.daofactory.DaoFactory;
import com.romeulima.daojdbc.dao.department.DepartmentDao;
import com.romeulima.daojdbc.dao.seller.SellerDao;
import com.romeulima.daojdbc.domain.department.Department;
import com.romeulima.daojdbc.domain.seller.Seller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department findById");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println();
        System.out.println("TEST 2: department findAll");
        List<Department> depList = departmentDao.findAllDepartments();
        depList.forEach(System.out::println);

        System.out.println();
        System.out.println("TEST 3: department insert");
        Department newDep = new Department(null, "Videogame");
        departmentDao.insert(newDep);
        System.out.println("New Department inserted! Id: " + newDep.getId());


        System.out.println();
        System.out.println("TEST 3: department delete");
        System.out.print("Enter the id number that you wish delete: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);

        System.out.println();
        System.out.println("TEST 4: department update");
        newDep.setName("Games");
        departmentDao.update(newDep);


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

        System.out.println();
        System.out.println("=== TEST 4: seller insert");
        Seller newSeller = new Seller(null, "Lucas Montano", "lucas@gmail.com", LocalDate.now(), 100000.00, new Department(2, null));
        sellerDao.insert(newSeller);
        System.out.println("new Id = " + newSeller.getId());

        System.out.println();
        System.out.println("=== TEST 5: seller update");
        seller.setName("Romeu Lima");
        sellerDao.update(seller);
        System.out.println("updated");

        System.out.println("Digite o id do seller que voce quer remover: ");
        int idSeller = sc.nextInt();
        sellerDao.delete(id);

    }
}
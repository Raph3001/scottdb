package db;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class ScottUI {

    public void testRun() throws SQLException, IOException {
        DBAccess dbAccess = DBAccess.getInstance();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of department: ");
        String dept = sc.next();
        try {
            System.out.println("Employees from department " + dept.toUpperCase(Locale.ROOT) + dbAccess.getEmployeeListOfDepartment(dept.toUpperCase(Locale.ROOT)));
            System.out.println("Average salary from all non-manager employees: " + dbAccess.getAvgSalOfEmps() + "€");
            System.out.println("Datasets deleted from table salegrade: " + dbAccess.deleteContentOfSalegrade());
            System.out.println("Datasets inserted into table salegrade: " + dbAccess.insertValuesIntoSalgrade(Path.of("C:\\Users\\Rapha\\Java\\scottdb\\src\\main\\resources\\INSERT INTO public.sql")));


        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    public static void main(String[] args) throws SQLException, IOException {
        ScottUI scottUI = new ScottUI();
        scottUI.testRun();
    }

}

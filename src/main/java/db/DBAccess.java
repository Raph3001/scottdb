package db;

import db.pojos.Department;
import db.pojos.Employee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBAccess {

    private static DBAccess dbAccess;
    private DBDatabase dbDatabase = DBDatabase.getInstance();

    private DBAccess() {

    }

    public static DBAccess getInstance() {
        if (dbAccess == null) {
            dbAccess = new DBAccess();
        }
        return dbAccess;
    }
    private PreparedStatement employeePreparedStatement;
    public List<Employee> getEmployeeListOfDepartment(String dept) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sqlString = "SELECT * FROM emp a INNER JOIN emp e ON e.empno = a.mgr INNER JOIN dept ON dept.deptno = a.deptno WHERE dname = ? ORDER BY a.ename";

        if (employeePreparedStatement == null) {
            employeePreparedStatement = dbDatabase.getConnection().prepareStatement(sqlString);
        }
        employeePreparedStatement.setString(1, dept);

        ResultSet rs = employeePreparedStatement.executeQuery();
        while (rs.next()) {
            employees.add(new Employee(convertCase(rs.getString("ename")),
                    convertCase(rs.getString("job")),
                    convertCase(rs.getString(10)),
                    LocalDate.parse(rs.getString("hiredate")),
                    rs.getInt("sal"),
                    rs.getInt("comm"),
                    new Department(rs.getInt("deptno"),
                            convertCase(rs.getString("dname")),
                            convertCase(rs.getString("loc")))));
        }

        return employees;
    }

    private PreparedStatement managerNamePreparedStatement;
    public String getManagernameForEmp(int mgrno) throws SQLException {

        String sqlString = "SELECT ename FROM emp WHERE empno = ?";

        if (managerNamePreparedStatement == null) {
            managerNamePreparedStatement = dbDatabase.getConnection().prepareStatement(sqlString);
        }
        managerNamePreparedStatement.setInt(1, mgrno);
        ResultSet name = managerNamePreparedStatement.executeQuery();
        while (name.next()) {
            return name.getString("ename");
        }
        throw new SQLException();
    }

    public Double getAvgSalOfEmps() throws SQLException {
        String sqlString = "SELECT AVG(sal) FROM emp WHERE NOT job = 'MANAGER' AND NOT JOB = 'PRESIDENT'";
        Statement statement = dbDatabase.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        double avgSal = 0;
        while (rs.next()) {
            avgSal = rs.getDouble(1);
        }
        dbDatabase.releaseStatement(statement);
        return avgSal;
    }

    public Integer deleteContentOfSalegrade() throws SQLException {
        Integer deletedDatasets = 0;
        String sqlString = "DELETE FROM salgrade";
        Statement statement = dbDatabase.getStatement();
        deletedDatasets = statement.executeUpdate(sqlString);
        dbDatabase.releaseStatement(statement);
        return deletedDatasets;
    }

    public Integer insertValuesIntoSalgrade(Path pathname) throws IOException, SQLException {
        Integer datasets = 0;

        String sql = Files.readString(pathname);
        Statement statement = dbDatabase.getStatement();

        datasets = statement.executeUpdate(sql);

        dbDatabase.releaseStatement(statement);
        return datasets;
    }

    public String convertCase(String string) {
        return (string.charAt(0) + string.substring(1).toLowerCase(Locale.ROOT));
    }

    public static void main(String[] args) throws SQLException, IOException {
        DBAccess dbAccess = DBAccess.getInstance();
        System.out.println(dbAccess.getEmployeeListOfDepartment("RESEARCH"));
        System.out.println(dbAccess.getManagernameForEmp(7369));
        System.out.println(dbAccess.getAvgSalOfEmps());
        System.out.println(dbAccess.deleteContentOfSalegrade());
        System.out.println(dbAccess.insertValuesIntoSalgrade(Path.of("C:\\Users\\Rapha\\Java\\scottdb\\src\\main\\resources\\INSERT INTO public.sql")));
        System.out.println(dbAccess.convertCase("GERoLd"));
    }

}

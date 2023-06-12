package db;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DBDatabase {

    private DBCachedConnection dbCachedConnection = DBCachedConnection.getInstance();
    private Connection connection;
    private static DBDatabase dbDatabase;

    private DBDatabase() {
        try {
            Class.forName(DBProperties.getProperty("driver"));
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBDatabase getInstance() {
        if (dbDatabase == null) {
            dbDatabase = new DBDatabase();
        }
        return dbDatabase;
    }

    public void connect() throws SQLException {
        if (connection != null) {
            disconnect();
        }
        connection = DriverManager.getConnection(DBProperties.getProperty("url"),
                DBProperties.getProperty("username"),
                DBProperties.getProperty("password"));
        dbCachedConnection.setConnection(connection);
        System.out.println("Successfully connected");
    }


    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() throws SQLException {
        return dbCachedConnection.getStatement();
    }

    public void releaseStatement(Statement statement) {
        dbCachedConnection.releaseStatement(statement);
    }

    public static void main(String[] args) throws SQLException {
        DBDatabase dbDatabase = DBDatabase.getInstance();
        Statement statement = dbDatabase.getStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM emp");
        while (rs.next()) {
            System.out.println(rs.getString("ename"));
        }
        dbDatabase.releaseStatement(statement);
    }

}

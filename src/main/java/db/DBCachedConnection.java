package db;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBCachedConnection {

    private static DBCachedConnection dbCachedConnection;
    private LinkedList<Statement> sqlqueue = new LinkedList<>();
    private Connection connection;

    private DBCachedConnection() {}

    public static DBCachedConnection getInstance() {
        if (dbCachedConnection == null) dbCachedConnection = new DBCachedConnection();
        return dbCachedConnection;
    }

    public Statement getStatement() throws SQLException {
        if (sqlqueue.isEmpty()) {
            return connection.createStatement();
        }
        return sqlqueue.poll();
    }

    public void releaseStatement(Statement statement) {
        sqlqueue.offer(statement);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }



}

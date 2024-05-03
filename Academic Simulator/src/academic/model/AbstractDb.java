package academic.model;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class AbstractDb {
    protected String url = null;
    protected String username = null;
    protected String password = null;

    protected Connection connection = null;

    public AbstractDb(String url, String username, String password) throws SQLException{
        this.url = url;
        this.username = username;
        this.password = password;
        this.prepareTables();
    }

    public void prepareTables() throws SQLException{
        this.createTables();
        this.seedTables();
    }

    protected Connection getConection() throws SQLException{
        if(this.connection == null){
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        }
        return this.connection;
    }

    public void shutdown() throws SQLException{
        if(this.connection != null){
            this.connection.close();
        }
    }

    protected void createTables() throws SQLException{
        // Implement this method in the subclass
    }

    protected void seedTables() throws SQLException{
        // Implement this method in the subclass
    }
}
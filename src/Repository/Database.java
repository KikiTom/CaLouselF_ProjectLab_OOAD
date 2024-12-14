package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/calouself_localdb"; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Database instance;
    
    private Database() {  
        // Method 1: Simple driver loading  
        try {  
            // Attempt to load the driver  
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());  
        } catch (SQLException e) {  
            // Handle SQL-related exceptions  
            System.err.println("Error registering MySQL driver: " + e.getMessage());  
            throw new RuntimeException("Could not register MySQL driver", e);  
        }  
    }    

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel_User
 */
public /*abstract*/ class DataGateway {
    private static String JDBS_DRIVER = "com.mysql.jdbc.Driver";
    private static DataGateway dataGateway;
    private static MysqlDataSource dataSource;
    
    private DataGateway() throws IOException {
        ConfigReader config = ConfigReader.getInstance();
        dataSource = new MysqlDataSource();
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUser(config.getDbUser());
        dataSource.setPassword(config.getDbPassword());
        
        try {
            Class.forName(JDBS_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataGateway.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static DataGateway getInstance() throws IOException {
        if (dataGateway == null)
            dataGateway = new DataGateway();
        
        return dataGateway;
    }
    
    public MysqlDataSource getDataSource() {
        return dataSource;
    }
    
    public void dropAll() throws SQLException {
        executeSqlScript(getDataSource().getConnection(), new File("D:/Documents/old/5 курс/Зозуля/TeatrApp/src/resources/script.sql"));
    }

    private void executeSqlScript(Connection conn, File inputFile) {

        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while(scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                // Execute statement
                currentStatement = conn.createStatement();
                currentStatement.execute(rawStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        scanner.close();
}
}


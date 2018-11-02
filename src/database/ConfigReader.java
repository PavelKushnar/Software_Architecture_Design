/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Pavel_User
 */
public class ConfigReader {
    
    InputStream inputStram;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private static ConfigReader instance = null;
    
    public static ConfigReader getInstance() throws IOException {
        if (instance == null) {
            instance = new ConfigReader();
        }
        
        return instance;
    }
    
    private ConfigReader() throws IOException{

            Properties prop = new Properties();
            String propFileName = "resources/config.properties";

            inputStram = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStram != null) {
                prop.load(inputStram);
            } else {
                inputStram.close();
                throw new FileNotFoundException("property file '" + propFileName + "' not fount in the classpath");
            } 
            
            dbUrl = prop.getProperty("dbUrl");
            dbUser = prop.getProperty("dbUser");
            dbPassword = prop.getProperty("dbPassword");            

            inputStram.close();

    }

    /**
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @return the dbUser
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * @return the dbPassword
     */
    public String getDbPassword() {
        return dbPassword;
    }
}

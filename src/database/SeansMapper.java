/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Spectacle;
import buisness_logic.Seans;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel_User
 */
public class SeansMapper {
     private static Map<Integer, Seans> loadedMap = new HashMap<>();
    private static Connection connection;
    private static SeansMapper instance;

    private SeansMapper() throws IOException, SQLException {
        DataGateway gateway = DataGateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public static SeansMapper getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new SeansMapper();
        }
        return instance;
    }
    
    public boolean addSeans(Seans seans) throws SQLException {
        String query = "INSERT INTO `seans` (`spectacleid`,`timestart`,`timeend`) "
                + "VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, seans.getSeansspectacle().getId());
        statement.setTimestamp(2, Timestamp.valueOf(seans.getTime()));
        statement.setTimestamp(3, Timestamp.valueOf(seans.getTimeend()));
        
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            seans.setId(id);
        } else {
            return false;
            //ToDO exception
        }

        loadedMap.put(seans.getId(), seans);
        return true;
    }
    public ArrayList<Seans> getSeanss() throws SQLException, IOException {

        

        String query = "SELECT * FROM `seans` ;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        ArrayList<Seans> list = dbRecordsToSeans(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
    public Seans getById(int id) throws SQLException, IOException {

        Seans pers = loadedMap.get(id);
        if (pers != null) {
            return pers;
        }

        String query = "SELECT * FROM `seans` WHERE `id` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        ArrayList<Seans> list = dbRecordsToSeans(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
    private ArrayList<Seans> dbRecordsToSeans(ResultSet rs) throws SQLException, IOException {

        ArrayList<Seans> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");

            Seans check = loadedMap.get(id);
            if (check != null) {
                list.add(check);
                continue;
            }
            
            LocalDateTime timestart = rs.getTimestamp("timestart").toLocalDateTime();
            LocalDateTime timeend = rs.getTimestamp("timeend").toLocalDateTime();
            int spectacleid = rs.getInt("spectacleid");
            Spectacle spectacle = SpectacleMapper.getInstance().getById(spectacleid);
            check = new Seans(timestart, spectacle);
            check.setId(id);

            loadedMap.put(check.getId(), check);
            list.add(check);
        }
        return list;
    }
    
    public int update() throws SQLException {
        for (Map.Entry<Integer, Seans> entry : loadedMap.entrySet()) {
            this.update(entry.getValue());
        }
        return 0;
    }

    public int update(int id) throws SQLException {

        Seans check = loadedMap.get(id);
        if (check == null) {
            return -1;
        }

        this.update(check);
        return 0;
    }

    private int update(Seans check) throws SQLException {
        if (check == null) {
            return -1;
        }
        
        String query = "UPDATE `seans` SET `spectacleid` = ?,`timestart` = ? ,`timeend` = ? "
                + "WHERE `id` = ?;";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, check.getSeansspectacle().getId());
        statement.setTimestamp(2, Timestamp.valueOf(check.getTime()));
        statement.setTimestamp(3, Timestamp.valueOf(check.getTimeend()));
        statement.setInt(4, check.getId());
        statement.executeUpdate();
        return 0;
    }
    public void clear() {
        loadedMap.clear();
    }

    @Override
    protected void finalize() {
        try {
            connection.close();
            super.finalize();

        } catch (Exception ex) {
            Logger.getLogger(PersonMapper.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Throwable ex) {
            Logger.getLogger(PersonMapper.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}

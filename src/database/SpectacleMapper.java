/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Spectacle;
import buisness_logic.SpectacleStatus;
import buisness_logic.RejPost;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel_User
 */
public class SpectacleMapper {
    
    private static Map<Integer, Spectacle> loadedMap = new HashMap<>();
    private static Connection connection;
    private static SpectacleMapper instance;

    private SpectacleMapper() throws IOException, SQLException {
        DataGateway gateway = DataGateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public static SpectacleMapper getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new SpectacleMapper();
        }
        return instance;
    }
    
    public boolean addSpectacle(Spectacle spectacle) throws SQLException {
        String query = "INSERT INTO spectacle (cost,time,rejpostid,spectaclestatus,name,info) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, spectacle.getSpectacleProkatCost());
        statement.setInt(2, spectacle.getTime());
        statement.setInt(3, spectacle.getKinkomp().getId());
        statement.setString(4, SpectacleStatus.statusToStr(spectacle.getStatus()));
        statement.setString(5, spectacle.getName());
        statement.setString(6, spectacle.getInfo());

        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            spectacle.setId(id);
        } else {
            return false;
            //ToDO exception
        }

        loadedMap.put(spectacle.getId(), spectacle);
        return true;
    }
    
    public ArrayList<Spectacle> getSpectacles() throws SQLException, IOException {

        

        String query = "SELECT * FROM spectacle ;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        ArrayList<Spectacle> list = dbRecordsToSpectacle(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
    
    public Spectacle getById(int id) throws SQLException, IOException {

        Spectacle fil = loadedMap.get(id);
        if (fil != null) {
            return fil;
        }

        String query = "SELECT * FROM spectacle WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        ArrayList<Spectacle> list = dbRecordsToSpectacle(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
    
    private ArrayList<Spectacle> dbRecordsToSpectacle(ResultSet rs) throws SQLException, IOException {

        ArrayList<Spectacle> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");

            Spectacle spectacle = loadedMap.get(id);
            if (spectacle != null) {
                list.add(spectacle);
                continue;
            }

            int cost = rs.getInt("cost");
            int time = rs.getInt("time");
            int rejpostid = rs.getInt("rejpostid");
            String name = rs.getString("name");
            String info = rs.getString("info");
            String status = rs.getString("spectaclestatus");
            
            RejPost rejpost = (RejPost) PersonMapper.getInstance().getById(rejpostid);
            
            
            spectacle = new Spectacle(rejpost, cost, name, info, time);
            spectacle.setId(id);
            spectacle.setStatus(SpectacleStatus.strToStatus(status));

            loadedMap.put(spectacle.getId(), spectacle);
            list.add(spectacle);
        }
        return list;
    }
    
    public int update() throws SQLException {
        for (Map.Entry<Integer, Spectacle> entry : loadedMap.entrySet()) {
            this.update(entry.getValue());
        }
        return 0;
    }

    public int update(int id) throws SQLException {

        Spectacle spectacle = loadedMap.get(id);
        if (spectacle == null) {
            return -1;
        }

        this.update(spectacle);
        return 0;
    }
    
    
    private int update(Spectacle spectacle) throws SQLException {
        if (spectacle == null) {
            return -1;
        }

        String query = "UPDATE spectacle SET rejpostid = ?, "
                + "cost = ?, spectaclestatus = ?, name = ?, info = ?, time = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, spectacle.getKinkomp().getId());
        statement.setInt(2, spectacle.getSpectacleProkatCost());
        statement.setString(3, SpectacleStatus.statusToStr(spectacle.getStatus()));
        statement.setString(4, spectacle.getName());
        statement.setString(5, spectacle.getInfo());
        statement.setInt(6, spectacle.getTime());
        
        statement.setInt(7, spectacle.getId());

        statement.executeUpdate();
        return 0;
    }
    
    
    
     public void sync() throws SQLException, IOException {

        for (Map.Entry<Integer, Spectacle> entry : loadedMap.entrySet()) {
            Integer id = entry.getKey();
            sync(id);
        }
    }

    public void sync(int id) throws SQLException, IOException {
        Spectacle spectacle = loadedMap.get(id);
        Spectacle actalSpectacle = getActualCopy(id);
        spectacle.setStatus(actalSpectacle.getStatus());
    }

    private Spectacle getActualCopy(int id) throws SQLException, IOException {
        

            String query = "SELECT * FROM spectacle WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Spectacle> list = dbActualToSpectacle(rs);

            if (list.isEmpty()) {
                return null;
            }

            Spectacle spectacle = list.get(0);
            return spectacle;
        
    }

    private ArrayList<Spectacle> dbActualToSpectacle(ResultSet rs) throws SQLException, IOException {

        ArrayList<Spectacle> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");

            Spectacle spectacle;

            int cost = rs.getInt("cost");
            int time = rs.getInt("time");
            int rejpostid = rs.getInt("rejpostid");
            String name = rs.getString("name");
            String info = rs.getString("info");
            String status = rs.getString("spectaclestatus");
            
            RejPost rejpost = (RejPost) PersonMapper.getInstance().getById(rejpostid);
            
            
            spectacle = new Spectacle(rejpost, cost, name, info, time);
            spectacle.setId(id);
            spectacle.setStatus(SpectacleStatus.strToStatus(status));


            list.add(spectacle);
        }
        return list;
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

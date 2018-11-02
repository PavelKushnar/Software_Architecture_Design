/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Bron;
import buisness_logic.Mesto;
import buisness_logic.Seans;
import buisness_logic.BronStatus;
import buisness_logic.Client;
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
public class BronMapper {
    
    private static Map<Integer, Bron> loadedMap = new HashMap<>();
    private static Connection connection;
    private static BronMapper instance;

    private BronMapper() throws IOException, SQLException {
        DataGateway gateway = DataGateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public static BronMapper getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new BronMapper();
        }
        return instance;
    }
    
    public boolean addBron(Bron bron) throws SQLException {
        String query = "INSERT INTO bron (clientid,seansid,mestoid,bronstatus,cost) "
                + "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, bron.getClient().getId());
        statement.setInt(2, bron.getSeans().getId());
        statement.setInt(3, bron.getMesto().getId());
        statement.setString(4, BronStatus.statusToStr(bron.getStat()));
        statement.setInt(5, (int) bron.getCost());
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            bron.setId(id);
        } else {
            return false;
            //ToDO exception
        }

        loadedMap.put(bron.getId(), bron);
        return true;
    }
    
    public ArrayList<Bron> getBrons() throws SQLException, IOException {

        

        String query = "SELECT * FROM bron ;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        ArrayList<Bron> list = dbRecordsToBron(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
    
    public Bron getById(int id) throws SQLException, IOException {

        Bron comp = loadedMap.get(id);
        if (comp != null) {
            return comp;
        }

        String query = "SELECT * FROM bron WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        ArrayList<Bron> list = dbRecordsToBron(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
    
    private ArrayList<Bron> dbRecordsToBron(ResultSet rs) throws SQLException, IOException {

        ArrayList<Bron> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");

            Bron bron = loadedMap.get(id);
            if (bron != null) {
                list.add(bron);
                continue;
            }

            int clientid = rs.getInt("clientid");
            int mestoid = rs.getInt("mestoid");
            int seansid = rs.getInt("seansid");
            int cost = rs.getInt("cost");
            String status = rs.getString("bronstatus");
            
            Client client = (Client) PersonMapper.getInstance().getById(clientid);
            Mesto mesto = MestoMapper.getInstance().getById(mestoid);
            Seans seans = SeansMapper.getInstance().getById(seansid);
            
            bron = new Bron(client, mesto, seans);
            bron.setId(id);
            bron.setStat(BronStatus.strToStatus(status));
            bron.setCost((double)cost);
            loadedMap.put(bron.getId(), bron);
            list.add(bron);
        }
        return list;
    }
    
    public int update() throws SQLException {
        for (Map.Entry<Integer, Bron> entry : loadedMap.entrySet()) {
            this.update(entry.getValue());
        }
        return 0;
    }

    public int update(int id) throws SQLException {

        Bron bron = loadedMap.get(id);
        if (bron == null) {
            return -1;
        }

        this.update(bron);
        return 0;
    }
    private int update(Bron bron) throws SQLException {
        if (bron == null) {
            return -1;
        }

        String query = "UPDATE bron SET  clientid = ? , seansid = ? ,mestoid = ? ,bronstatus = ?  , cost = ?  "
                + "WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, bron.getClient().getId());
        statement.setInt(2, bron.getSeans().getId());
        statement.setInt(3, bron.getMesto().getId());
        int cost =  (int) bron.getCost();
        statement.setInt(5,cost);
        statement.setString(4, BronStatus.statusToStr(bron.getStat()));

            
        statement.setInt(6, bron.getId());
        statement.executeUpdate();
        return 0;
    }
    
    
    
     public void sync() throws SQLException, IOException {

        for (Map.Entry<Integer, Bron> entry : loadedMap.entrySet()) {
            Integer id = entry.getKey();
            sync(id);
        }
    }

    public void sync(int id) throws SQLException, IOException {
        
        Bron bron = loadedMap.get(id);
        
        Bron actalPatment = getActualCopy(id);
        
        //
        bron.setStat(actalPatment.getStat());
        
        bron.setCost(actalPatment.getCost());
    }

    private Bron getActualCopy(int id) throws SQLException, IOException {
        

            String query = "SELECT * FROM bron WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Bron> list = dbActualBron(rs);

            if (list.isEmpty()) {
                return null;
            }

            Bron bron = list.get(0);
            return bron;
        
    }

    private ArrayList<Bron> dbActualBron(ResultSet rs) throws SQLException, IOException {

        ArrayList<Bron> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");

            Bron bron;

            int clientid = rs.getInt("clientid");
            int mestoid = rs.getInt("mestoid");
            int seansid = rs.getInt("seansid");
            int cost = rs.getInt("cost");
            String status = rs.getString("bronstatus");
            
            Client client = (Client) PersonMapper.getInstance().getById(clientid);
            Mesto mesto = MestoMapper.getInstance().getById(mestoid);
            Seans seans = SeansMapper.getInstance().getById(seansid);
            
            bron = new Bron(client, mesto, seans);
            bron.setId(id);
            bron.setStat(BronStatus.strToStatus(status));
            bron.setCost((double)cost);

            list.add(bron);
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

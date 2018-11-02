/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Mesto;
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
public class MestoMapper {
    private static Map<Integer, Mesto> loadedMap = new HashMap<>();
    private static Connection connection;
    private static MestoMapper instance;

    private MestoMapper() throws IOException, SQLException {
        DataGateway gateway = DataGateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public static MestoMapper getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new MestoMapper();
        }
        return instance;
    }
    
    public boolean addMesto(Mesto mesto) throws SQLException {
        String query = "INSERT INTO `mesto` (`ryad`,`number`,`cost`) "
                + "VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, mesto.getRyad());
        statement.setInt(2, mesto.getNumber());
        statement.setInt(3, (int) mesto.getCost());
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            mesto.setId(id);
        } else {
            return false;
            //ToDO exception
        }

        loadedMap.put(mesto.getId(), mesto);
        return true;
    }
    public ArrayList<Mesto> getMestos() throws SQLException, IOException {

        

        String query = "SELECT * FROM `mesto` ;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        ArrayList<Mesto> list = dbRecordsToMesto(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
    public Mesto getById(int id) throws SQLException, IOException {

        Mesto pers = loadedMap.get(id);
        if (pers != null) {
            return pers;
        }

        String query = "SELECT * FROM `mesto` WHERE `id` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        ArrayList<Mesto> list = dbRecordsToMesto(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
    private ArrayList<Mesto> dbRecordsToMesto(ResultSet rs) throws SQLException, IOException {

        ArrayList<Mesto> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");

            Mesto check = loadedMap.get(id);
            if (check != null) {
                list.add(check);
                continue;
            }

            int ryad = rs.getInt("ryad");
            int number = rs.getInt("number");
            int cost = rs.getInt("cost");
            check = new Mesto(number, ryad, cost);
            check.setId(id);

            loadedMap.put(check.getId(), check);
            list.add(check);
        }
        return list;
    }
    
    public int update() throws SQLException {
        for (Map.Entry<Integer, Mesto> entry : loadedMap.entrySet()) {
            this.update(entry.getValue());
        }
        return 0;
    }

    public int update(int id) throws SQLException {

        Mesto check = loadedMap.get(id);
        if (check == null) {
            return -1;
        }

        this.update(check);
        return 0;
    }

    private int update(Mesto check) throws SQLException {
        if (check == null) {
            return -1;
        }
        
        String query = "UPDATE `mesto` SET `ryad` = ?,`number` = ?"
                + " WHERE `id` = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, check.getRyad());
        statement.setInt(2, check.getNumber() );
        statement.setInt(3, check.getId());

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

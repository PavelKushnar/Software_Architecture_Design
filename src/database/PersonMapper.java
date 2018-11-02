/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Cashier;
import buisness_logic.Client;
import buisness_logic.RejPost;
import buisness_logic.Manager;
import buisness_logic.Person;
import buisness_logic.PersonType;
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
public class PersonMapper {
    
    private static Map<Integer, Person> loadedMap = new HashMap<>();
    private static Connection connection;
    private static PersonMapper instance;

    private PersonMapper() throws IOException, SQLException {
        DataGateway gateway = DataGateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public static PersonMapper getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new PersonMapper();
        }
        return instance;
    }
    
    public boolean addPerson(Person pers) throws SQLException {
        String query = "INSERT INTO person (name, login, pass, persontype, cash) "
                + "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, pers.getName());
        statement.setString(2, pers.getLogin());
        statement.setString(3, pers.getPass());
        statement.setString(4, PersonType.typeToStr(pers.getType()));
        statement.setInt(5, pers.getCash());
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            pers.setId(id);
        } else {
            return false;
            //ToDO exception
        }

        loadedMap.put(pers.getId(), pers);
        return true;
    }

    public Person getByLogin(String alogin) throws SQLException {
        for (Map.Entry<Integer, Person> entry : loadedMap.entrySet()) {
            if (entry.getValue().getLogin().equals(alogin)) {
                return entry.getValue();
            }
        }

        String query = "SELECT * FROM person WHERE login = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, alogin);
        ResultSet rs = statement.executeQuery();

        ArrayList<Person> list = dbRecordsToPerson(rs);
        if (list.isEmpty()) {
            return null;
        }
        loadedMap.put(list.get(0).getId(), list.get(0));
        return list.get(0);
    }

    public Person getById(int id) throws SQLException {

        Person pers = loadedMap.get(id);
        if (pers != null) {
            return pers;
        }

        String query = "SELECT * FROM person WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        ArrayList<Person> list = dbRecordsToPerson(rs);
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public ArrayList<Person> getPersons(PersonType type) throws SQLException {
        String query = "SELECT * FROM person WHERE persontype = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, PersonType.typeToStr(type));
        ResultSet rs = statement.executeQuery();

        return dbRecordsToPerson(rs);
    }

    public ArrayList<Person> getPersons() throws SQLException {
        String query = "SELECT * FROM person;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        return dbRecordsToPerson(rs);
    }

    public int update() throws SQLException {
        for (Map.Entry<Integer, Person> entry : loadedMap.entrySet()) {
            this.update(entry.getValue());
        }
        return 0;
    }

    public int update(int id) throws SQLException {
        Person pers = loadedMap.get(id);
        if (pers == null) {
            return -1;
        }

        this.update(pers);
        return 0;
    }

    private int update(Person pers) throws SQLException {
        if (pers == null) {
            return -1;
        }

        String query = "UPDATE person SET name = ?, login = ?,\n"
                + "	pass = ?, persontype = ?, cash = ?\n"
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, pers.getName());
        statement.setString(2, pers.getLogin());
        statement.setString(3, pers.getPass());
        statement.setString(4, PersonType.typeToStr(pers.getType()));
        statement.setInt(5, pers.getCash());
        statement.setInt(6, pers.getId());

        statement.executeUpdate();
        return 0;
    }

    private ArrayList<Person> dbRecordsToPerson(ResultSet rs) throws SQLException {

        ArrayList<Person> list = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");

            Person pers = loadedMap.get(id);
            if (pers != null) {
                list.add(pers);
                continue;
            }

            String name = rs.getString("name");
            String login = rs.getString("login");
            String pass = rs.getString("pass");
            String perstype=rs.getString("persontype");
            //System.out.println("perstype="+perstype);
            PersonType type = PersonType.strToType(perstype);
            
            int cash = rs.getInt("cash");

            switch (type) {
                case Client:
                    pers = new Client();
                    break;
                case RejPost:
                    pers = new RejPost();
                    break;
                case Manager:
                    pers = new Manager();
                    break;
                case Cashier:
                    pers = new Cashier();
                    break;
                default:
                    continue;
            }

            pers.setId(id);
            pers.setName(name);
            pers.setLogin(login);
            pers.setType(type);
            pers.setPass(pass);
            
            pers.setCash(cash);

            loadedMap.put(pers.getId(), pers);
            list.add(pers);
        }
        return list;
    }

    public void sync() {

        for (Map.Entry<Integer, Person> entry : loadedMap.entrySet()) {
            Integer id = entry.getKey();
            sync(id);
        }
    }

    public void sync(int id) {
        Person pers = loadedMap.get(id);
        //System.out.println(id);
        Person actyalPers = getActualCopy(id);
        //System.out.println(actyalPers.getCash());
        pers.setCash(actyalPers.getCash());
    }

    private Person getActualCopy(int id) {
        try {
            Person pers;

            String query = "SELECT * FROM person WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            ArrayList<Person> list = dbActualPers(rs);
            if (list.isEmpty()) {
                return null;
            }

            return list.get(0);
        } catch (SQLException ex) {
            //Logger.getLogger(OrderMapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Person> dbActualPers(ResultSet rs) throws SQLException {

        ArrayList<Person> list = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");

            Person pers;
            
            String name = rs.getString("name");
            String login = rs.getString("login");
            String pass = rs.getString("pass");
            
            String perstype=rs.getString("persontype");
            //System.out.println("perstype="+perstype);
            PersonType type = PersonType.strToType(perstype);
            int cash = rs.getInt("cash");
            
            
            System.out.println(id);
            
             switch (type) {
                case Client:
                    pers = new Client();
                    break;
                case RejPost:
                    pers = new RejPost();
                    break;
                case Manager:
                    pers = new Manager();
                    break;
                case Cashier:
                    pers = new Cashier();
                    break;
                default:
                    continue;
            }

            pers.setId(id);
            pers.setName(name);
            pers.setLogin(login);
            pers.setType(type);
            pers.setPass(pass);
            pers.setCash(cash);

            list.add(pers);
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
            Logger.getLogger(PersonMapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(PersonMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}

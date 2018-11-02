/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Bron;
import buisness_logic.BronStatus;
import buisness_logic.Client;
import buisness_logic.Spectacle;
import buisness_logic.SpectacleStatus;
import buisness_logic.Mesto;
import buisness_logic.Person;
import buisness_logic.PersonType;
import buisness_logic.Seans;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel_User
 */
public class DBMemberRepository implements MemberRepository{
    private static PersonMapper personMapper;
    private static MestoMapper mestoMapper;
    private static SpectacleMapper spectacleMapper;
    private static SeansMapper seansMapper;
    private static BronMapper bronMapper;
    /*
    private static CheckMapper checkMapper;*/
    
    public DBMemberRepository() {
        try {
            if (personMapper == null) {
                personMapper = PersonMapper.getInstance();
            }
            if (mestoMapper == null) {
                mestoMapper = MestoMapper.getInstance();
            }
            if (spectacleMapper == null) {
                spectacleMapper = SpectacleMapper.getInstance();
            }
            if (seansMapper == null) {
                seansMapper = SeansMapper.getInstance();
            }
            
            if (bronMapper == null) {
                bronMapper = BronMapper.getInstance();
            }
            /*
            if (checkMapper == null) {
                checkMapper = CheckMapper.getInstance();
            }*/
        } catch (IOException | SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Boolean addPerson(Person per) {

        try {
            if (personMapper.getByLogin(per.getLogin()) != null)
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        try {
            personMapper.addPerson(per);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;   
    }

    @Override
    public Person getPerson(String login) {
        try {
            return personMapper.getByLogin(login);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean addSpectacle(Spectacle spectacle) {
        
        try {
            spectacleMapper.addSpectacle(spectacle);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Spectacle> getSpectacles() {
        ArrayList<Spectacle> list = new ArrayList<>();
        try {
            list = spectacleMapper.getSpectacles();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    @Override
    public Boolean addSeans(Seans se) {
        try {
            seansMapper.addSeans(se);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;   
    }

    @Override
    public ArrayList<Seans> getSeanses() {
        ArrayList<Seans> list = new ArrayList<>();
        try {
            list = seansMapper.getSeanss();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

        }

    @Override
    public Boolean addMesto(Mesto mes) {
        try {
            mestoMapper.addMesto(mes);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Mesto> getMesto() {
        ArrayList<Mesto> list = new ArrayList<>();
        
        try {
            list = mestoMapper.getMestos();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public Boolean addBron(Bron bron) {
        try {
            bronMapper.addBron(bron);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;   
        }

    @Override
    public ArrayList<Bron> getBrons() {
        ArrayList<Bron> list = new ArrayList<>();
        
        try {
            list = bronMapper.getBrons();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
        }

    @Override
    public ArrayList<Bron> getBronsbySeans(Seans se) {
        ArrayList<Bron> ret = new ArrayList<>();
        if(getBrons()!=null)
        for (Bron br : getBrons()) {
            if (br.getSeans().equals(se)) {
                ret.add(br);
            }
        }
        return ret;
    }

    @Override
    public Boolean update(Object obj) {
        try {
        String name = obj.getClass().getSimpleName();
            if (name.equals("Client") || name.equals("RejPost")
                    || name.equals("Manager")|| name.equals("Cashier")) {
                name = "Person";
            }
            switch (name) {
                
                case "Person":
                    Person per = (Person) obj;
                    personMapper.update(per.getId());
                    break;
                case "Mesto":
                    Mesto mesto = (Mesto) obj;
                    mestoMapper.update(mesto.getId());
                    break;
                case "Spectacle":
                    Spectacle spectacle = (Spectacle) obj;
                    spectacleMapper.update(spectacle.getId());
                    break;
                case "Seans":
                    Seans order2 = (Seans) obj;
                    seansMapper.update(order2.getId());
                    break;
                case "Bron":
                    Bron order3 = (Bron) obj;
                    bronMapper.update(order3.getId());
                    break;
                /*case "check":
                    check order4 = (check) obj;
                    checkMapper.update(order4.getId());
                    break;*/
                default:
                    return false;
            }
             } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            
            return true;
    }

    @Override
    public Boolean updateAll() {
        try {
            personMapper.update();
            mestoMapper.update();
            spectacleMapper.update();
            seansMapper.update();
            bronMapper.update();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public Boolean clearAll() {
        try {
            DataGateway.getInstance().dropAll();
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        personMapper.clear();
        mestoMapper.clear();
        spectacleMapper.clear();
        bronMapper.clear();
        seansMapper.clear();
        
        
        return true;
    }
    
    public void clearCache() {
            personMapper.clear();
            mestoMapper.clear();
            spectacleMapper.clear();
            bronMapper.clear();
            seansMapper.clear();
            
            
    }

    @Override
    public void sync() {
        personMapper.sync();
        try {
            spectacleMapper.sync();
            bronMapper.sync();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ArrayList<Person> getPersons() {
    ArrayList<Person> list = new ArrayList<>();
        try {
            list = personMapper.getPersons();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ArrayList<Person> getPersons(PersonType type) {
    ArrayList<Person> list = new ArrayList<>();
        try {
            list = personMapper.getPersons(type);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;    
    }

    @Override
    public Person getPersonById(int id) {
        try {
            return personMapper.getById(id);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Spectacle getSpectacleById(int id) {
        try {
            return spectacleMapper.getById(id);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Seans getSeansById(int id) {
        try {
            return seansMapper.getById(id);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Mesto getMestoById(int id) {
        try {
            return mestoMapper.getById(id);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        }

    @Override
    public Bron getBronById(int id) {
        try {
            return bronMapper.getById(id);
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        }

    @Override
    public ArrayList<Spectacle> getSpectaclesStatus(SpectacleStatus stat) {
        ArrayList<Spectacle> list = new ArrayList<>();
        try {
            for(Spectacle fil:spectacleMapper.getSpectacles() ){
                if(fil.getStatus().equals(stat))
                    list.add(fil);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ArrayList<Seans> getSeanses(LocalDate dt) {
        ArrayList<Seans> ret = new ArrayList<>();
        if(getSeanses()!=null)
        for (Seans se : getSeanses()) {
            if (se.getTime().getYear()==dt.getYear()&&
                    se.getTime().getDayOfYear()==dt.getDayOfYear()) {
                ret.add(se);
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Bron> getBrons(Client cl) {
        ArrayList<Bron> ret = new ArrayList<>();
        if(getBrons()!=null)
        for (Bron br : getBrons()) {
            if (br.getClient().getId()==(cl.getId())) {
                ret.add(br);
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Bron> getBronsStatus(BronStatus stat) {
        ArrayList<Bron> ret = new ArrayList<>();
        if(getBrons()!=null)
        for (Bron br : getBrons()) {
            if (br.getStat().equals(stat)) {
                ret.add(br);
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Bron> getBronsStatus(Client cl, BronStatus stat) {
        ArrayList<Bron> ret = new ArrayList<>();
        if(getBrons()!=null)
        for (Bron br : getBrons()) {
            if (br.getClient().getId()==(cl.getId())&&br.getStat().equals(stat)) {
                ret.add(br);
            }
        }
        return ret;
    }

    @Override
    public Mesto getMestoByRyadNum(int ryad, int num) {
        Mesto mesto = null;
        
        ArrayList<Mesto> list = new ArrayList<>();
        
        try {
            list = mestoMapper.getMestos();
        } catch (SQLException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBMemberRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Mesto ms : list){
            if(ms.getRyad()==ryad && ms.getNumber() == num)
                mesto = ms;
        }
        
        
        return mesto;
    }
    
}

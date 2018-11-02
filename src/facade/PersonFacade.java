/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import buisness_logic.Person;
import buisness_logic.PersonType;
import com.google.gson.Gson;
import database.DBMemberRepository;
import database.MemberRepository;
import java.util.ArrayList;

/**
 *
 * @author Pavel_User
 */
public class PersonFacade {
    
    MemberRepository rep = new DBMemberRepository();
    private Person pers;

    public PersonFacade(int id) {
        pers = rep.getPersonById(id);
    }

    public PersonFacade(String login) {
        pers = rep.getPerson(login);
    }

    public String getCash() {
        return Integer.toString(pers.getCash());
    }

    
    public String getName() {
        return pers.getName();
    }
    
    public String getType() {
        return pers.getType().toString();
    }
    
    public String getId() {
        return Integer.toString(pers.getId());
    }

    public void addCash(int cash) {
        pers.addCash(cash);
        rep.updateAll();
    }
    
}

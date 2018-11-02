/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

import database.ArrayMemberRepository;
import database.DBMemberRepository;
import database.MemberRepository;

/**
 *
 * @author Pavel_User
 */
public abstract class Person {

    protected int id;
    protected String login;
    protected String name;
    protected String pasw;
    protected int money = 0;
    protected PersonType type;
    
    //protected MemberRepository rep = new ArrayMemberRepository();
    protected MemberRepository rep = new DBMemberRepository();
    
    public Boolean autentification(String Login, String pass) {
        return Login.equals(getLogin()) && pass.equals(getPass());
    }
    
    public Boolean addCash (int num){
        money += num;
        update();
        return true;
    }
    public void update() {
        rep.update(this);
    }
    public Boolean writeOffCash (int num) {
        if (money < num)
            return false;
        
        money -= num;
        update();
        return true;
    } 
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pasw;
    }

    public void setPass(String pasw) {
        this.pasw = pasw;
    }

    public int getCash() {
        return money;
    }

    public void setCash(int money) {
        this.money = money;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

}

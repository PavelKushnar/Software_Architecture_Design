/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Pavel_User
 */
public class Client extends Person{

    
   // private int Client_money;
    //private String clientName;
    private Spectacle flm;
    private ArrayList<String> spectacles;

    private Seans se;
    private ArrayList<String> seanses;

    private Mesto mes;
    private int[] places;

    private Ticket tik;

    public Client(String name, String login, String pass) {
        this.name = name;
        this.login = login;
        this.pasw = pass;
        this.type = PersonType.Client;
        
    } 

    public Client() {
    }

    public int CreateBrons(ArrayList<Mesto> mesta, Seans seans){
        if(mesta.size()>9){
            
            for (Mesto mesto : mesta) {
            Bron bron1 =new Bron(this, mesto, seans);
            int rez = bron1.addbronCorp();
                //System.out.println("rez =" + rez);
            if(rez !=0)
                return rez;
            
        }
        
            
        }else
        for (Mesto mesto : mesta) {
            Bron bron1 =new Bron(this, mesto, seans);
            int rez = bron1.addbron();
            if(rez !=0)
                return rez;
        }
        return 0;
    }
    public boolean PayBron(Bron bron){
        
        if(bron.getStat()!=BronStatus.WaitPay)
            return false;
        
        bron.wantPayBron();
        return true;
    }
}

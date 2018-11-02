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
public class Cashier extends Person{
    //private String clientName;
  //  private Spectacle flm;
  // int selectedSpectacle
    
    private Seans se;
    private ArrayList<String> seanses;
    
    private Mesto mes;
    private  HashMap plases;
    
    private Ticket tik;
    
    
     public Cashier (String name, String login, String pass) {
        this.name = name;
        this.login = login;
        this.pasw = pass;
        this.type = PersonType.Cashier;
        
    } 

    public Cashier() {
    }
     
    public boolean FIlmPay(Spectacle spectacle){
        
        if(spectacle.getStatus()!=SpectacleStatus.WaitAccessCashier)
            return false;
        
        if(!this.writeOffCash(spectacle.getSpectacleProkatCost()))
            return false;
        
        if(!spectacle.getKinkomp().addCash(spectacle.getSpectacleProkatCost()))
            return false;
        
        spectacle.setStatus(SpectacleStatus.Payed);
        return true;
    } 
    
    public boolean BronPay(Bron bron){
        
        if(bron.getStat()!=BronStatus.WaitCashierPay)
            return false;
        
        if(!bron.getClient().writeOffCash((int)bron.getCost()))
            return false;
        
        if(!this.addCash((int)bron.getCost()))
            return false;
        
        bron.PayedBron();
        return true;
    } 
         
}

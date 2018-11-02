/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

import java.time.LocalDateTime;

/**
 *
 * @author Pavel_User
 */

public class Manager extends Person{
    
    private RejPost rejpost;
    
    public Manager (String name, String login, String pass) {
        this.name = name;
        this.login = login;
        this.pasw = pass;
        this.type = PersonType.Manager;
        
    }   

    public Manager() {
    }
  
    public int AceptCorpBron(Bron br, float skidka){
        
        return br.acessBron(skidka);
    }   
    
    public boolean NewSeans(LocalDateTime time, Spectacle seansspectacle){
        
        Seans se = new Seans(time, seansspectacle);
        
        if(seansspectacle.getStatus()!=SpectacleStatus.Payed)
            return false;
        if(rep.getSeanses()!=null)
        for (Seans sefor : rep.getSeanses()) {
            if (se.getTime().isBefore(sefor.getTimeend())   && se.getTimeend().isAfter(sefor.getTime())) {
                
                return false;
                
            }
        }
        rep.addSeans(se);
        return true;
    }
    
    public boolean FIlmToBuy(Spectacle spectacle){
        
        if(spectacle.getStatus()!=SpectacleStatus.New)
            return false;
        spectacle.setStatus(SpectacleStatus.WantBuy);
        return true;
    }
    
    public boolean FIlmToCasshierpay(Spectacle spectacle){
        
        if(spectacle.getStatus()!=SpectacleStatus.WaitPay)
            return false;
        
        spectacle.setStatus(SpectacleStatus.WaitAccessCashier);
        return true;
    }
    
    
}

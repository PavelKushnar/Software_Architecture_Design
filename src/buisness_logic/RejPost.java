/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

/**
 *
 * @author Pavel_User
 */
public class RejPost extends Person {
    
    public RejPost(String name, String login, String pass) {
        this.name = name;
        this.login = login;
        this.pasw = pass;
        this.type = PersonType.RejPost;
        
    } 

    public RejPost() {
    }
    
    public boolean addSpectacle(int spectacleProkatCost, String name, String info, int time){
        Spectacle fil = new Spectacle(this,spectacleProkatCost, name, info, time);
        return rep.addSpectacle(fil);
        
    }
    
    public boolean FIlmAcsessBuy(Spectacle spectacle){
        
        if(spectacle.getStatus()!=SpectacleStatus.WantBuy)
            return false;
        
        spectacle.setStatus(SpectacleStatus.WaitPay);
        return true;
    }
    
}

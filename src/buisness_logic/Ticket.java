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
public class Ticket {
    private int plase,seans,cost;
    private String cashir,spectacleName;
  
   
    public Ticket (int plase,int seans,int cost,String cashir,String spectacleName)    
    {
        this.cashir = cashir;
        this.plase = plase;
        this.seans = seans;
        this.spectacleName = spectacleName;
        this.cost = cost;
        
    };
    public String getSpectacleName ()
    {
        return spectacleName;
    };
    public void setSpectacleName (String spectacleName)
    {
        this.spectacleName=spectacleName;
    };
    
    public int getTicketCost ()
    {
         return cost;
    };
    
    public void setTicketCost (int cost)
    {
        this.cost=cost;
    };
    
     public int getSeans ()
    {
         return seans;
    };
     
     public void setSeans (int seans)
    {
        this.seans=seans;
    };
     
     
      public String getCashirName ()
    {
         return cashir;
    };
      
      public void setCashirName (String cashir)
    {
        this.cashir=cashir;
    };
}

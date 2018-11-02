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
public class Mesto {
   
    private int id;
    private int ryad;
    private int number;
    private double cost;
    private boolean bron = false;

    public Mesto( int number, int ryad, double cost) {
       this.ryad = ryad;
       this.number = number;
       this.cost = cost;
    }

    public Mesto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getRyad() {
        return ryad;
    }

    public void setRyad(int ryad) {
        this.ryad = ryad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isBron() {
        return bron;
    }

    public void setBron(boolean bron) {
        this.bron = bron;
    }

 

   
}

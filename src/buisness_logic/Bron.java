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
public class Bron {

    private int id;
    private Client client;
    private Mesto mesto;
    private Seans seans;
    private BronStatus stat;
    private double cost;
    //protected MemberRepository rep = new ArrayMemberRepository();
    protected MemberRepository rep = new DBMemberRepository();
    
    public Bron(Client client, Mesto mesto, Seans seans) {
        this.client = client;
        this.mesto = mesto;
        this.seans = seans;
        stat = BronStatus.New;
        cost = mesto.getCost();
    }
    public void update() {
        rep.update(this);
    }
    // 0 - успешно
    // 1 2 3 4 - бронь уже есть
    // 10 - неизвестная ошибка
    public int addbron() {
        this.stat = BronStatus.WaitPay;
        for (Bron br : rep.getBronsbySeans(this.getSeans())) {
            if (br.getMesto().getId()==(this.getMesto().getId())) {
                if(br.getStat().equals(BronStatus.New))
                    return  1;
                if(br.getStat().equals(BronStatus.WaitCashierPay))
                    return  2;
                if(br.getStat().equals(BronStatus.Payed))
                    return  3;
                if(br.getStat().equals(BronStatus.WaitPay))
                    return  4;
                if(br.getStat().equals(BronStatus.WaitAccessManager))
                    return  5;
            }
        }
        if(!rep.addBron(this))
            return 10;
        return 0;
        
    }
    
    // 0 - успешно
    // 1 2 3 4 - бронь уже есть
    // 10 - неизвестная ошибка
    public int addbronCorp() {
        this.stat = BronStatus.WaitAccessManager;
        for (Bron br : rep.getBronsbySeans(this.getSeans())) {
            if (br.getMesto().getId()==(this.getMesto().getId())) {
                if(br.getStat().equals(BronStatus.New))
                    return  1;
                if(br.getStat().equals(BronStatus.WaitCashierPay))
                    return  2;
                if(br.getStat().equals(BronStatus.Payed))
                    return  3;
                if(br.getStat().equals(BronStatus.WaitPay))
                    return  4;
                if(br.getStat().equals(BronStatus.WaitAccessManager))
                    return  5;
            }
        }
        if(!rep.addBron(this))
            return 10;
        return 0;
        
    }
    
    public int acessBron(double skidka){
        if(this.stat!=BronStatus.WaitAccessManager)
            return 1;
        if(skidka<1||skidka>99)//расчет скидки
            return 2;
        this.cost = this.cost - this.cost*(skidka/100);
        this.stat = BronStatus.WaitPay;//теперь статус брони - "оплачена"
        update();
        return 0;
    }
    public int wantPayBron(){
        this.stat = BronStatus.WaitCashierPay;
        update();
        return 0;
    }
    
    public int PayedBron(){
        this.stat = BronStatus.Payed;
        update();
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    public Seans getSeans() {
        return seans;
    }

    public void setSeans(Seans se) {
        this.seans = se;
    }

    public BronStatus getStat() {
        return stat;
    }

    public void setStat(BronStatus stat) {
        this.stat = stat;
        //update();
    }
    
    

}

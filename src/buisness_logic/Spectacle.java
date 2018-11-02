/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

import database.DBMemberRepository;
import database.MemberRepository;
import java.util.ArrayList;

/**
 *
 * @author Pavel_User
 */
public class Spectacle {

    private int spectacleProkatCost;
    private String name;
    private String info;
    private int id;
    private RejPost rejpost;
    private SpectacleStatus status;
    private int time;
    protected MemberRepository rep = new DBMemberRepository();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Spectacle(RejPost rejpost,int spectacleProkatCost, String name, String info,int time) {
        this.rejpost = rejpost;
        this.spectacleProkatCost = spectacleProkatCost;
        this.name = name;
        this.info = info;
        this.status = SpectacleStatus.New;
        this.time = time;
    }

    public RejPost getKinkomp() {
        return rejpost;
    }

    public void setKinkomp(RejPost rejpost) {
        this.rejpost = rejpost;
    }

        
    
    public SpectacleStatus getStatus() {
        return status;
    }
    public void update() {
        rep.update(this);
    }
    public void setStatus(SpectacleStatus status) {
        this.status = status;
        this.update();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSpectacleProkatCost() {
        return spectacleProkatCost;
    }

    public void setSpectacleProkatCost(int spectacleProkatCost) {
        this.spectacleProkatCost = spectacleProkatCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}

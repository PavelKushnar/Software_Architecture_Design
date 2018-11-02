/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Pavel_User
 */
public class Seans {

    private int id;
    private int costOnePlase; // более не используется
    private LocalDateTime time;
    private LocalDateTime timeend;
    private Spectacle seansspectacle;

    public Seans( LocalDateTime time, Spectacle spectacle) {
       // this.id = id;
        this.time = time;
        this.seansspectacle = spectacle;
        LocalDateTime timebuf = time;
        this.timeend = timebuf.plusMinutes(spectacle.getTime());
    }

    ;  

    public LocalDateTime getTimeend() {
        return timeend;
    }

    public void setTimeend(LocalDateTime timeend) {
        this.timeend = timeend;
    }

    public int getCostOnePlase() {
        return costOnePlase;
    }

    public void setCostOnePlase(int costOnePlase) {
        this.costOnePlase = costOnePlase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Spectacle getSeansspectacle() {
        return seansspectacle;
    }

    public void setSeansspectacle(Spectacle seansspectacle) {
        this.seansspectacle = seansspectacle;
    }

    

}

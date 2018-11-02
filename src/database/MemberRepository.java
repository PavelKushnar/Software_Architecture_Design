/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import buisness_logic.Bron;
import buisness_logic.BronStatus;
import buisness_logic.Client;
import buisness_logic.Spectacle;
import buisness_logic.SpectacleStatus;
import buisness_logic.Mesto;
import buisness_logic.Person;
import buisness_logic.PersonType;
import buisness_logic.Seans;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Pavel_User
 */
public interface MemberRepository {
    
    ArrayList<Person> getPersons();
    ArrayList<Person> getPersons(PersonType type);
    Boolean addPerson(Person per);
    Person getPersonById(int id);
    Person getPerson (String login);
   
    Boolean addSpectacle (Spectacle spectacle);
    ArrayList<Spectacle> getSpectacles ();
    ArrayList<Spectacle> getSpectaclesStatus (SpectacleStatus stat);
    Spectacle getSpectacleById(int id);
    
    Boolean addSeans(Seans se);
    ArrayList<Seans>  getSeanses ();
    ArrayList<Seans>  getSeanses (LocalDate dt);
    Seans getSeansById(int id);
    
 // мест ограниченное количество но все равно сначала необходимо 
 //получить информацию о свободных местах    
    Boolean addMesto    (Mesto mes);
    ArrayList<Mesto>    getMesto();
    Mesto getMestoById(int id);
    Mesto getMestoByRyadNum(int ryad,int num);
    
    Boolean addBron    (Bron bron);
    ArrayList<Bron>    getBrons();
    ArrayList<Bron>    getBrons(Client cl);
    ArrayList<Bron>    getBronsStatus(BronStatus stat);
    ArrayList<Bron>    getBronsStatus(Client cl, BronStatus stat);
    ArrayList<Bron>    getBronsbySeans(Seans se);
    Bron getBronById (int id);
    
    Boolean update(Object obj);
    Boolean updateAll();
    Boolean clearAll();
    
    void sync();
}

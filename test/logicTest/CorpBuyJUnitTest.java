package logicTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import buisness_logic.BronStatus;
import buisness_logic.Cashier;
import buisness_logic.Client;
import buisness_logic.SpectacleStatus;
import buisness_logic.RejPost;
import buisness_logic.Manager;
import buisness_logic.Mesto;
import database.ArrayMemberRepository;
import database.DBMemberRepository;
import database.MemberRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pavel_User
 */
public class CorpBuyJUnitTest {
      MemberRepository rep;
    public CorpBuyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
           //rep = new ArrayMemberRepository();
           rep = new DBMemberRepository();
           rep.clearAll();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    
    
    // Бизнес процесс  покупка корпоративным клиентом
     @Test
     public void hello() 
     {
         int spectacleProkatCost =5000;
        String name ="Spectacle1";
        String info ="Info about F1";
        int timef = 90;
        RejPost rejpost = new RejPost("Rejpost", "log1", name);
        Manager manager = new Manager("Manager", "log2", name);
        Cashier cashier = new Cashier("Cashier", "log3", name);
        rep.addPerson(cashier);
        rep.addPerson(manager);
        rep.addPerson(rejpost);
        assertTrue(rejpost.addSpectacle(spectacleProkatCost, name, info, timef));

        assertEquals(name, rep.getSpectacles().get(0).getName());
        rep.getSpectacles().get(0).setStatus(SpectacleStatus.Payed);
        rep.updateAll();
        LocalDateTime time = LocalDateTime.of(2017,Month.AUGUST,1,18,00);
        LocalDateTime timebad1 = LocalDateTime.of(2017,Month.AUGUST,1,16,32);
        LocalDateTime timebad2 = LocalDateTime.of(2017,Month.AUGUST,1,19,28);
        LocalDateTime timegood = LocalDateTime.of(2017,Month.AUGUST,1,19,32);
        assertTrue(manager.NewSeans(time, rep.getSpectacles().get(0)));
        assertFalse(manager.NewSeans(timebad1, rep.getSpectacles().get(0)));
        assertFalse(manager.NewSeans(timebad2, rep.getSpectacles().get(0)));
        assertTrue(manager.NewSeans(timegood, rep.getSpectacles().get(0)));
        //Seans se = new Seans(time, rep.getSpectacles().get(0));
        //assertTrue(rep.addSeans(se));
        //assertEquals(se, rep.getSeanses().get(0));
        //assertEquals(18,rep.getSeanses().get(0).getTime().getHour());
        /*
        for(int i = 1 ; i < 17 ; i++){
            for ( int k = 1 ; k < 21 ; k++){
                int cost = 100;
                if(k>3||k<18)
                    cost = 150;
                if(i<3)
                    cost = 50;
                Mesto ms = new Mesto(k, i, cost);
                assertTrue(rep.addMesto(ms));
            }
        }*/
        int i = (int) rep.getMesto().get(45).getCost();
       assertEquals(150, i);
        
        String namecl = "Pat";
        String login ="Patty";
        String pass ="qwerty";
        
        Client cl = new Client(namecl,login,pass);
        assertTrue(rep.addPerson(cl)); 
        
        ArrayList<Mesto> mesta = new ArrayList();
        mesta.add(rep.getMesto().get(41));
        mesta.add(rep.getMesto().get(42));
        mesta.add(rep.getMesto().get(43));
        mesta.add(rep.getMesto().get(44));
        mesta.add(rep.getMesto().get(45));
        mesta.add(rep.getMesto().get(46));
        mesta.add(rep.getMesto().get(47));
        mesta.add(rep.getMesto().get(48));
        mesta.add(rep.getMesto().get(49));
        mesta.add(rep.getMesto().get(1));
        assertEquals(0,cl.CreateBrons(mesta, rep.getSeanses().get(0)));
        assertEquals(BronStatus.WaitAccessManager,rep.getBrons().get(2).getStat());
        assertEquals(2,manager.AceptCorpBron(rep.getBrons().get(2), 101));
        
         System.out.println((int) rep.getBrons().get(2).getCost());
         i = (int)rep.getBrons().get(2).getCost();
         assertEquals(150,  i);
        
        assertEquals(0,manager.AceptCorpBron(rep.getBrons().get(2), 50));
        i = (int) rep.getBrons().get(2).getCost();
         System.out.println((int) rep.getBrons().get(2).getCost());
        assertEquals(75, i);
        
        assertFalse(cl.PayBron(rep.getBrons().get(1)));
        assertTrue(cl.PayBron(rep.getBrons().get(2)));
        
        assertFalse(cashier.BronPay(rep.getBrons().get(2)));
        cl.addCash(500);
        assertTrue(cashier.BronPay(rep.getBrons().get(2)));
        assertEquals(BronStatus.Payed, rep.getBrons().get(2).getStat());
        
        assertEquals(75, cashier.getCash());
        assertEquals(425, cl.getCash());
        rep.updateAll();
        
     }
}

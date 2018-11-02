/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicTest;

import buisness_logic.Cashier;
import buisness_logic.Spectacle;
import buisness_logic.SpectacleStatus;
import buisness_logic.RejPost;
import buisness_logic.Manager;
import database.ArrayMemberRepository;
import database.DBMemberRepository;
import database.MemberRepository;
import java.time.LocalDateTime;
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
public class SpectacleBuyJUnitTest {

    MemberRepository rep;

    public SpectacleBuyJUnitTest() {
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
    
    
    
    @Test
    public void hello() {
        String name ="Spectacle1";
        String info ="Info about F1";
        int timef = 90;
        RejPost rejpost = new RejPost("Rejpost", "log1", name);
        Manager manager = new Manager("Manager", "log2", name);
        Cashier cashier = new Cashier("Cashier", "log3", name);
        rep.addPerson(cashier);
        rep.addPerson(manager);
        rep.addPerson(rejpost);
        assertTrue(rejpost.addSpectacle(200, name, info, timef));
        assertEquals(name, rep.getSpectacles().get(0).getName());
        Spectacle spectacle = rep.getSpectacles().get(0);
        cashier.addCash(500);
        assertFalse(manager.NewSeans(LocalDateTime.now(), spectacle));
        assertTrue(manager.FIlmToBuy(spectacle));
        assertTrue(rejpost.FIlmAcsessBuy(spectacle));
        assertTrue(manager.FIlmToCasshierpay(spectacle));
        assertTrue(cashier.FIlmPay(spectacle));
        assertEquals(300,cashier.getCash());
        assertEquals(200,rejpost.getCash());
        assertEquals(SpectacleStatus.Payed,spectacle.getStatus());
        rep.updateAll();
        
    }

   
}

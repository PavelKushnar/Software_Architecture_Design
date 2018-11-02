/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databasetests;

import buisness_logic.Client;
import buisness_logic.Manager;
import database.DBMemberRepository;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Pavel_User
 */
public class PersonMapperTest {
    
    DBMemberRepository rep = new DBMemberRepository();
    
    public PersonMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rep.clearAll();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testUpdate()
    {
        String custName ="name1";
        String custLogin = "login1";
        String custPass = "pass1";
        Client cust = new Client(custName, custLogin, custPass);

        if (!rep.addPerson(cust))
            cust = (Client) rep.getPerson(custLogin);
        
        int newCash = cust.getCash()+1;
        cust.setCash(newCash);
        cust.update();
        
        rep.clearCache();
        
        cust = (Client) rep.getPerson(custLogin);
        assertEquals(newCash, cust.getCash());
        
        custName ="Store2";
        custLogin = "login4";
        custPass = "pass1";
        Manager cust2 = new Manager(custName, custLogin, custPass);

        if (!rep.addPerson(cust2))
            cust2 = (Manager) rep.getPerson(custLogin);
        
        newCash = cust.getCash()+1;
        cust2.setCash(newCash);
        cust2.update();
        
        rep.clearCache();
        
        cust2 = (Manager) rep.getPerson(custLogin);
        assertEquals(newCash, cust2.getCash());
        
    }
    
}

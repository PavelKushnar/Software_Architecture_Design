/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databasetests;

import buisness_logic.Client;
import buisness_logic.Manager;
import buisness_logic.Mesto;
import database.DBMemberRepository;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Pavel_User
 */
public class MestoMapperTest {
    
    
    DBMemberRepository rep = new DBMemberRepository();
    
    public MestoMapperTest() {
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
        for(int i = 1 ; i < 17 ; i++){
            for ( int k = 1 ; k < 21 ; k++){
                int cost = 100;
                if(k>3&&k<18)
                    cost = 150;
                if(i<3)
                    cost = 50;
                Mesto ms = new Mesto(k, i, cost);
                assertTrue(rep.addMesto(ms));
            }
        }
    }
    
}

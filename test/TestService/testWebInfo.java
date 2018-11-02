/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestService;

import database.DBMemberRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.WebInfo;
import service.WebInfo2;
import service.WebInfoService;
import service.WebInfoService2;

/**
 *
 * @author Pavel_User
 */
public class testWebInfo {
    DBMemberRepository rep = new DBMemberRepository();
    public testWebInfo() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetWeather() throws IOException {
        /*WebInfoService service = new WebInfoService ();
        String filname = rep.getSpectacles().get(1).getName();
        //assertEquals(filname, "Matrix");
        //WebInfo res = service.getWebInfo(filname);
        WebInfo res = service.getWebInfo("swx");
        assertNotNull(res);
        System.out.println(res.getActors());
        System.out.println(res.getKp_rating());
        System.out.println(res.getImdb_rating());
        System.out.println(res.getPoster_big());*/
        WebInfoService2 service = new WebInfoService2 ();
        WebInfo2 res = service.getWebInfo("матрица");
        System.out.println(res.getBudget());
        System.out.println(res.getGenres());
        System.out.println(res.getOriginal_language());
        System.out.println(res.getOriginal_title());
        System.out.println(res.getOverview());
        System.out.println(res.getPopularity());
        System.out.println(res.getPoster_path());
        System.out.println(res.getProduction_companies());
        System.out.println(res.getProduction_countries());
        System.out.println(res.getRelease_date());
        System.out.println(res.getRevenue());
        System.out.println(res.getTagline());
        System.out.println(res.getVote_average());
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

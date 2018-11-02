/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import buisness_logic.Bron;
import buisness_logic.BronStatus;
import buisness_logic.Cashier;
import buisness_logic.Client;
import buisness_logic.Spectacle;
import buisness_logic.SpectacleStatus;
import buisness_logic.RejPost;
import buisness_logic.Manager;
import buisness_logic.Mesto;
import buisness_logic.Person;
import buisness_logic.PersonType;
import buisness_logic.Seans;
import database.DBMemberRepository;
import database.MemberRepository;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.WebInfo;
import service.WebInfo2;
import service.WebInfoService;
import service.WebInfoService2;

/**
 *
 * @author Pavel_User
 */
public class Facade {
    MemberRepository rep;
    static Facade facade;
    private int costsum = 0;
    public Facade() {
        rep = new DBMemberRepository();
    }

    public int getCostsum() {
        return costsum;
    }

    public void setCostsum(int costsum) {
        this.costsum = costsum;
    }
    
    public static Facade getInstance() {
        if (facade != null) {
            return facade;
        }

        facade = new Facade();
        return facade;
    }
    
    public boolean authentication(String login, String password) {
        Person pers = rep.getPerson(login);
        return pers.autentification(login, password);
    }
    private String[] getEnumNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
    public String[] getPersonesTypes() {
        return getEnumNames(PersonType.class);
    }
    public ArrayList<String> getSpectacleInfodop(String spectacle) throws IOException{
        ArrayList<String> res = new ArrayList<>();
        WebInfoService service = new WebInfoService ();
        WebInfo res2 = service.getWebInfo(spectacle);
        res.add(res2.getTitle_ru());
        res.add(res2.getCountry());
        res.add(res2.getYear());
        res.add(res2.getComposer());
        res.add(res2.getActors());
        res.add(res2.getScreenwriter());
        res.add(res2.getDirector());
        res.add(res2.getProducer());
        res.add(res2.getOperator());
        res.add(res2.getDesign());
        res.add(res2.getGenre());
        res.add(res2.getKp_rating());
        res.add(res2.getDescription());
        res.add(res2.getPoster_big());
        res.add(res2.getDescription());
        
        
        return res;
        
    }
    
    public ArrayList<String> getSpectacleInfodop2(String spectacle) throws IOException{
        ArrayList<String> res = new ArrayList<>();
        WebInfoService2 service = new WebInfoService2 ();
        WebInfo2 res2 = service.getWebInfo(spectacle);
        res.add(res2.getOriginal_title());
        res.add(res2.getProduction_countries());
        res.add(res2.getRelease_date());
        res.add(res2.getTagline());
        res.add(res2.getProduction_companies());
        res.add(res2.getPopularity());
        res.add(res2.getBudget());
        res.add(res2.getRevenue());
        res.add("");
        res.add(res2.getOriginal_language());
        res.add(res2.getGenres());
        res.add(res2.getVote_average());
        res.add(res2.getOverview());
        res.add(res2.getPoster_path());
        
        return res;
        
    }
    
    public ArrayList<String> getSpectacleInfo(String idspectacle) throws IOException{
        ArrayList<String> res = new ArrayList<>();
        Spectacle fil = rep.getSpectacleById(Integer.parseInt(idspectacle));
        res.add(fil.getName());
        res.add(fil.getInfo());
        res.add(String.valueOf(fil.getTime()));
        res.add(String.valueOf(fil.getSpectacleProkatCost()));
        return res;
        
    }
    
    public String getSpectacleStatus(String idspectacle) throws IOException{
        String res;
        Spectacle fil = rep.getSpectacleById(Integer.parseInt(idspectacle));
        res = SpectacleStatus.statusToStr(fil.getStatus());
        return res;
        
    }
    public String getBronName(String idbron) throws IOException{
        String res = "";
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        res = br.getSeans().getSeansspectacle().getName();
        return res;
        
    }
    public String getBronCost(String idbron) throws IOException{
        String res = "";
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        int cost = (int)br.getCost();
        res = String.valueOf(cost);
        System.out.println("BronCost " + res);
        return res;
        
    }
    public String getBronDate(String idbron) throws IOException{
        String res = "";
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        //res = String.valueOf(br.getSeans().getTime().getDayOfMonth()+"."+br.getSeans().getTime().getMonthValue()+"."+br.getSeans().getTime().getYear());
        res = (br.getSeans().getTime().format(formatter)); 
        return res;
        
    }
    public String getBronTime(String idbron) throws IOException{
        String res = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        //res = String.valueOf(br.getSeans().getTime().getHour()+":"+br.getSeans().getTime().getMinute());
        res = (br.getSeans().getTime().format(formatter));
        return res;
        
    }
     public String getBronRyad(String idbron) throws IOException{
        String res = "";
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        res = String.valueOf(br.getMesto().getRyad());
        return res;
        
    }
      public String getBronNum(String idbron) throws IOException{
        String res = "";
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        res = String.valueOf(br.getMesto().getNumber());
        return res;
        
    }
    
    
    
    public boolean createSpectacle(String login,String name, String info, String timef,String cost){
        RejPost pers = (RejPost) rep.getPerson(login);
        return pers.addSpectacle(Integer.parseInt( cost), name, info, Integer.parseInt(timef));
    } 
    
    public boolean spectacleWantBuy(String login, String idspectacle){
        Manager manager = (Manager) rep.getPerson(login);
        Spectacle spectacle = rep.getSpectacleById(Integer.parseInt(idspectacle));
        return manager.FIlmToBuy(spectacle);
    }
    public boolean spectacleAcessbuy(String login, String idspectacle){
        RejPost rejpost = (RejPost) rep.getPerson(login);
        Spectacle spectacle = rep.getSpectacleById(Integer.parseInt(idspectacle));
        return rejpost.FIlmAcsessBuy(spectacle);
    }
    public boolean spectacleToCashier(String login, String idspectacle){
        Manager manager = (Manager) rep.getPerson(login);
        Spectacle spectacle = rep.getSpectacleById(Integer.parseInt(idspectacle));
        return manager.FIlmToCasshierpay(spectacle);
    }
    
    public boolean spectaclePay(String login, String idspectacle){
        
        Cashier cashier = (Cashier) rep.getPerson(login);
        Spectacle spectacle = rep.getSpectacleById(Integer.parseInt(idspectacle));
        return cashier.FIlmPay(spectacle);
    }
    
    public String CreateSeans(String login, String idspectacle, LocalDate dateSeans,
            String hour, String minutes){
        Manager manager = (Manager) rep.getPerson(login);
        Spectacle spectacle = rep.getSpectacleById(Integer.parseInt(idspectacle));
        
         if (hour.equals("") || minutes.equals("")) {
            return "Write Hours and minutes please";
        }

        if (!hour.matches("[-+]?\\d+")||!minutes.matches("[-+]?\\d+")) {
            return "Write Hours and minutes integer";
        }
        int hour1 = Integer.parseInt(hour);
        int minutes1 = Integer.parseInt(minutes);
        if (hour1<0||hour1>24) {
            return "Hours 0 - 24";
        }
        if (minutes1<0||minutes1>60) {
            return "minutes 0 - 60";
        }
        
        if(manager.NewSeans(LocalDateTime.of(dateSeans.getYear(),dateSeans.getMonth(),dateSeans.getDayOfMonth(), hour1,minutes1), spectacle))
        return "Seans create";
        
        return "Wrong data and time";
    }
    
    public String CreateBrons(String login, String idseans, ArrayList<String> ids){
        ArrayList<Mesto> mesta = new ArrayList();
        Client cl = (Client) rep.getPerson(login);
        Seans se = rep.getSeansById(Integer.parseInt(idseans));
        for(String ms: ids){
            //System.out.println("STRING MS: "+ ms);
            String ryad = ms.substring(0,1);
            String num = ms.substring(1, ms.length());
            //System.out.println("PARSE ryad:" + ryad + " PARSE num:" + num);
            mesta.add(rep.getMestoByRyadNum(Integer.parseInt(ryad),Integer.parseInt(num)));
        }
        int res = cl.CreateBrons(mesta, se);
        if(res ==0)
            return "Success Bron";
        return "Brons Erroro";
    }
    public String PlusCostMesto(int r,int n){
            int sum;
            
            costsum = costsum + (int) rep.getMestoByRyadNum(r+1,n+1).getCost();
        
        return String.valueOf(costsum);
    }
    public String MinusCostMesto(int r,int n){
            int sum;
            
            costsum = costsum - (int) rep.getMestoByRyadNum(r+1,n+1).getCost();
        
        return String.valueOf(costsum);
    }
    
    public String WantPayBron(String login, String idbron){
        Client cl = (Client) rep.getPerson(login);
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        if(cl.PayBron(br))
            return "Success Bron";
        return "Brons Erroro";
    }
    
    public String AcessmanageBron(String login, String idbron, String skidka){
        Manager mg = (Manager) rep.getPerson(login);
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        int res = mg.AceptCorpBron(br,Integer.parseInt(skidka));
        if(res == 0)
            return "Success Bron";
        if(res == 2)
            return "Wrong Skidka";
        return "Error status bron";
    }
    
    public String PayBron(String login, String idbron){
        Cashier cs = (Cashier) rep.getPerson(login);
        Bron br = rep.getBronById(Integer.parseInt(idbron));
        if(cs.BronPay(br))
            return "Success Bron";
        return "Error status bron";
    }
    
    
    public int registerPerson(String login, String name,
            String pass, String type) {
        Person pers = null;
        PersonType t = PersonType.strToType(type);
        switch (t) {
            case Client:
                pers = new Client(name, login, pass);
                break;
            case Manager:
                pers = new Manager(name, login, pass);
                break;
            case Cashier:
                pers = new Cashier(name, login, pass);
                break;
            case RejPost:
                pers = new RejPost(name, login, pass);
                break;
            default:
                break;

        }
        if (pers == null) {
            return -1;
        }

        if (rep.addPerson(pers) == false) {
            return -2;
        }
        rep.updateAll();
        return 0;
    }

    public String getPersonType(String login) {
        Person pers = rep.getPerson(login);
        //System.out.println(pers.getLogin());
        //System.out.println(pers.getType().toString());
        return pers.getType().toString();
    }

    public String getPersonName(String login) {
        Person pers = rep.getPerson(login);
        return pers.getName();
    }
    
    public String[] getSpectacleStatuses() {
        return getEnumNames(SpectacleStatus.class);
    }
    
    public String[] getBronStatuses() {
        return getEnumNames(BronStatus.class);
    }
    public ObservableList<Seans> getSeanses(LocalDate dt) {
        ArrayList<Seans> fil = rep.getSeanses(dt);
        ObservableList<Seans> list = FXCollections.observableArrayList(fil);
        return list;
    }
    public ObservableList<Bron> getBrons(String login, String status) {
        ArrayList<Bron> fil = new ArrayList<>();
        Client cl = (Client) rep.getPerson(login);
        if(status == "All")
            fil = rep.getBrons(cl);
        else
            fil = rep.getBronsStatus(cl,BronStatus.strToStatus(status));
        ObservableList<Bron> list = FXCollections.observableArrayList(fil);
        return list;
    }
    public ObservableList<Bron> getBrons( String status) {
        ArrayList<Bron> fil = new ArrayList<>();
        if(status == "All")
            fil = rep.getBrons();
        else
            fil = rep.getBronsStatus(BronStatus.strToStatus(status));
        ObservableList<Bron> list = FXCollections.observableArrayList(fil);
        return list;
    }
    public ArrayList<Bron> getBronsSe( String seansid) {
        ArrayList<Bron> fil = new ArrayList<>();
        Seans se = rep.getSeansById(Integer.valueOf(seansid));
        fil = rep.getBronsbySeans(se);
        return fil;
    }
    public ObservableList<Spectacle> getSpectacles(String login, String status) {
        ArrayList<Spectacle> fil = new ArrayList<>();
        if(status == "All")
            fil = rep.getSpectacles();
        else
            fil = rep.getSpectaclesStatus(SpectacleStatus.strToStatus(status));
        ObservableList<Spectacle> list = FXCollections.observableArrayList(fil);
        return list;
    }
    
    public void sync() {
        rep.sync();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teatrapp;

import Controller.ControllerWithIdentificator;
import buisness_logic.Spectacle;
import facade.Facade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.SimpleHttpServer;

/**
 *
 * @author Pavel_User
 */
public class TeatrApp extends Application {

    static Stage stage;
    static Parent panel;
    static Facade facade;
    static String title;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //SimpleHttpServer server = new SimpleHttpServer();
        //Runtime.getRuntime().addShutdownHook(new Thread(SimpleHttpServer::stop));

        SimpleHttpServer.start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        facade = Facade.getInstance();
        title = "TeatrApp";
        stage = primaryStage;
        stage.setTitle(title);
        stage.setResizable(false);
        //String testCustomerLogin = "trLogin";//"custLogin";//
        setAuthenticationView();
        //setPersonView(testCustomerLogin);

        //createCreateTripView(testCustomerLogin);
        //createCustomerTripView("1");
        //createChooseTenderView("1", "TransporterTo");
        //createInterviewView("1", "1");
        stage.show();
    }

    public static void setAuthenticationView() {
        String fxmlFile = "/View/AuthenticationFXML.fxml";
        setSmallView(fxmlFile);
    }

    public static void setRegisterView() {
        String fxmlFile = "/View/RegisterFXML.fxml";
        setSmallView(fxmlFile);
    }

    private static void setSmallView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(TeatrApp.class.getClass().getResource(fxmlFile));
            panel = (Parent) loader.load();
            //AuthenticationFXMLController controller =
            //        loader.<AuthenticationFXMLController>getController();

            //controller.init();
            Scene scene = new Scene(panel/*, 400, 400*/);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(TeatrApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPersonView(String login) {
        String type = facade.getPersonType(login);
        String fxml = null;
        switch (type) {
            case "RejPost":
                fxml = "/View/RejPostFXML.fxml";
                break;
            case "Cashier":
                fxml = "/View/CashierFXML.fxml";
                break;
            case "Client":
                fxml = "/View/ClientFXML.fxml";
                break;
            case "Manager":
                fxml = "/View/ManagerFXML.fxml";
                break;
            default:
                return;
        }
        setViewWithLogin(fxml, login, "", "");

    }

    public static void SpectacleView(String login, String stat1, String stat2) {
        String fxml = "/View/SpectacleFXML.fxml";
        createStageWithIdentificator(fxml, login, stat1, stat2);
    }

    public static void BronView(String login, String stat1, String stat2) {
        String fxml = "/View/CreateBronsFXML.fxml";
        createStageWithIdentificator(fxml, login, stat1, stat2);
    }
    
    public static void BiletView(String login, String stat1, String stat2) {
        String fxml = "/View/BronFXML.fxml";
        createStageWithIdentificator(fxml, login, stat1, stat2);
    }

    private static void createStageWithIdentificator(String fxmlFile,
            String identificator1, String identificator2, String identificator3) {
        try {
            FXMLLoader loader = new FXMLLoader(TeatrApp.class.getClass().getResource(fxmlFile));
            panel = (Parent) loader.load();
            ControllerWithIdentificator controller
                    = loader.<ControllerWithIdentificator>getController();

            controller.init(identificator1, identificator2, identificator3);

            Scene scene = new Scene(panel/*, 600, 500*/);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(TeatrApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void setViewWithLogin(String fxmlFile, String iden1, String iden2, String iden3) {
        try {
            FXMLLoader loader = new FXMLLoader(TeatrApp.class.getClass().getResource(fxmlFile));
            panel = (Parent) loader.load();
            ControllerWithIdentificator controller
                    = loader.<ControllerWithIdentificator>getController();

            controller.init(iden1, iden2, iden3);

            Scene scene = new Scene(panel, 830, 500);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(TeatrApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

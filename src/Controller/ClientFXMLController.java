/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.RejPostFXMLController.login;
import static Controller.ManagerFXMLController.login;
import buisness_logic.Bron;
import buisness_logic.Spectacle;
import buisness_logic.Seans;
import facade.Facade;
import facade.PersonFacade;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import teatrapp.TeatrApp;

/**
 * FXML Controller class
 *
 * @author Pavel_User
 */
public class ClientFXMLController implements Initializable, ControllerWithIdentificator{
private Facade facade;
PersonFacade compF;
static String login;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button btnLogOut;

    @FXML
    private TableView<Seans> tblViewTrips;

    @FXML
    private Label lblName;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblType;

    @FXML
    private ComboBox<String> cmbBoxStatus;

    @FXML
    private Label lblCash;

    @FXML
    private Button btnAddCash;

    @FXML
    private TextField txtAddCash;

    @FXML
    private Label lblAnswer;

    @FXML
    private TableView<Bron> tblViewBrons;

    @FXML
    private DatePicker dpDateSeans;


    @FXML
    void onClickLogOutButton(ActionEvent event) {
        TeatrApp.setAuthenticationView();
    }


    @FXML
    void onStatusChoosed(ActionEvent event) {
        facade.sync();
        String status = cmbBoxStatus.getValue();
        //initTableView1(status);
        initTableView(dpDateSeans.getValue());
        initTableViewBrons(status);
        lblCash.setText(compF.getCash());
    }
    void onStatusChoosed() {
        facade.sync();
        String status = cmbBoxStatus.getValue();
        //initTableView1(status);
        initTableView(dpDateSeans.getValue());
        initTableViewBrons(status);
        lblCash.setText(compF.getCash());
    }
    @FXML
    void onDatechoosedChoosed(ActionEvent event) {
        facade.sync();
        String status = cmbBoxStatus.getValue();
        //initTableView1(status);
        initTableView(dpDateSeans.getValue());
        initTableViewBrons(status);
        lblCash.setText(compF.getCash());
    }
    @FXML
    void onClickAddCashButton(ActionEvent event) {
        try{
            int cash = Integer.parseInt(txtAddCash.getText());
            compF.addCash(cash);
            lblAnswer.setText("Seccesful addition");
            
            initLabels();
        }
        catch (NumberFormatException e) {
            lblAnswer.setText("Bad cash. Try again");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @Override
    public void init(String login, String empty, String empty2) {
        this.login = login;
        compF = new PersonFacade(login);
        facade = Facade.getInstance();
        initLabels();
        initTableView(LocalDate.now());
        initComboBox();
        dpDateSeans.setValue(LocalDate.now());
        this.onStatusChoosed();
    }
    
    private void initComboBox() {
        cmbBoxStatus.getItems().add("All");
        cmbBoxStatus.getItems().addAll(facade.getBronStatuses());
        cmbBoxStatus.setValue("All");
    }
    
    private void initLabels() {
        lblName.setText(facade.getPersonName(login));
        lblLogin.setText(login);
        lblType.setText(facade.getPersonType(login));
        lblAnswer.setText("");
        lblCash.setText(compF.getCash());
        
    }
        
    private void initTableView(LocalDate dt) {
        tblViewTrips.getColumns().clear();
        
        
        TableColumn<Seans, String> name = new TableColumn<>("Spectacle Name");
        name.setMaxWidth(300);
        name.setCellValueFactory(cell -> 
                new SimpleStringProperty(cell.getValue().getSeansspectacle().getName()));
        
        TableColumn<Seans, String> data = new TableColumn<>("Data");
        data.setMaxWidth(300);
        data.setCellValueFactory(cell -> 
                new SimpleStringProperty(cell.getValue().getTime().toString()));
        
        
        tblViewTrips.setItems(facade.getSeanses(dt));
        tblViewTrips.getColumns().addAll(name, data);
        
        tblViewTrips.setRowFactory(tv -> {
            TableRow<Seans> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Seans rowData = row.getItem();
                        TeatrApp.BronView(login,"",Integer.toString(rowData.getId()));
                        //TeatrApp.createCustomerTripView(Integer.toString(rowData.getId()));
                    }
                }
            });
            return row;
        });
    }
    
    private void initTableViewBrons(String status) {
        tblViewBrons.getColumns().clear();
        
        TableColumn<Bron, String> clientName = new TableColumn<>("Client");
        clientName.setMaxWidth(300);
        clientName.setCellValueFactory(cell -> 
                new SimpleStringProperty(cell.getValue().getClient().getName()));
        
        TableColumn<Bron, String> spectacleName = new TableColumn<>("Spectacle");
        spectacleName.setMaxWidth(300);
        spectacleName.setCellValueFactory(cell -> 
                new SimpleStringProperty(cell.getValue().getSeans().getSeansspectacle().getName()));
        
        TableColumn<Bron, String> data = new TableColumn<>("Data");
        data.setMaxWidth(300);
        data.setCellValueFactory(cell -> 
                new SimpleStringProperty(cell.getValue().getSeans().getTime().toString()));
        
        TableColumn<Bron, String> statusColumn1 = new TableColumn<>("Mesto");
        statusColumn1.setMaxWidth(300);
        statusColumn1.setCellValueFactory(cell -> 
                new SimpleStringProperty("Ryad:"+cell.getValue().getMesto().getRyad()+", Mesto:"+ cell.getValue().getMesto().getNumber()));
        
                
        
        TableColumn<Bron, Integer> dateFrom = new TableColumn<>("Cost");
        dateFrom.setMaxWidth(300);
        dateFrom.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        
        TableColumn<Bron, String> targetCity = new TableColumn<>("Status");
        targetCity.setMaxWidth(300);
        targetCity.setCellValueFactory(new PropertyValueFactory<>("stat"));
        
        tblViewBrons.setItems(facade.getBrons(login,status));
        tblViewBrons.getColumns().addAll(clientName,spectacleName,data,statusColumn1,
                dateFrom,targetCity);
        
        tblViewBrons.setRowFactory(tv -> {
            TableRow<Bron> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Bron rowData = row.getItem();
                        lblAnswer.setText(facade.WantPayBron(login, String.valueOf(rowData.getId())));
                        
                        TeatrApp.BiletView(login,"",Integer.toString(rowData.getId()));
                        //TeatrApp.SpectacleView(login,"",Integer.toString(rowData.getId()));
                        //TeatrApp.createCustomerTripView(Integer.toString(rowData.getId()));
                    }
                }
            });
            return row;
        });
    }
   

}


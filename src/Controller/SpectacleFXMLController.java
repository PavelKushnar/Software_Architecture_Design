/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import facade.Facade;
import java.awt.event.InputMethodEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.WebInfo;
import service.WebInfoService;

/**
 * FXML Controller class
 *
 * @author Pavel_User
 */
public class SpectacleFXMLController implements Initializable, ControllerWithIdentificator {

    String login;
    String idSpectacle;
    Facade facade;

    @FXML
    private Label lblAnswer;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnCancel;

    @FXML
    private ImageView imgSpectacle;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private Label txtCountry;

    @FXML
    private Text txtnamerus;

    @FXML
    private Label txtYear;

    @FXML
    private Text txtScreenwriter;

    @FXML
    private Text txtDirector;

    @FXML
    private Text txtProducer;

    @FXML
    private TextArea txtInfo;

    @FXML
    private TextField txtTime;

    @FXML
    private Text txtOperator;

    @FXML
    private TextField txtCost;

    @FXML
    private Text txtDesigin;

    @FXML
    private Text txtGenre;

    @FXML
    private Text txtComposer;

    @FXML
    private TextArea txtActors;

    @FXML
    private Label txtRaiting;

    @FXML
    private Button btnWantPay;

    @FXML
    private Button btnAccesPay;

    @FXML
    private Button btnToCashierPay;

    @FXML
    private Button btnPay;

    @FXML
    private Text txtStatusSpectacle;

    @FXML
    private Label txtDate;

    @FXML
    private DatePicker dpDateSeans;

    @FXML
    private Text txtHourt;

    @FXML
    private Text txtMinutest;

    @FXML
    private TextField txtMinutes;

    @FXML
    private TextField txtHour;

    @FXML
    private Button btnCreateSeans;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    void onClickCreateButton(ActionEvent event) {
        String name = txtName.getText();
        String type = txtTime.getText();
        String info = txtInfo.getText();
        String cost = txtCost.getText();
        //String num = txtNum.getText();

        boolean res = facade.createSpectacle(login, name, info, type, cost);
        int ress = -6;
        if(res==true)
            ress = 0;
        switch (ress) {
            case 0:
                lblAnswer.setText("Successful creation");
                break;
            case -1:
                lblAnswer.setText("Please fill all fields");
                break;
            case -2:
                lblAnswer.setText("Cost and num must be integer");
                break;
            case -3:
                lblAnswer.setText("Can't finde company");
                return;
            case -5:
                lblAnswer.setText("Cost and num must be > 0");
                break;
            default:
                lblAnswer.setText("Unexpected error");
                break;

        }

    }

    @FXML
    void onNamechange(ActionEvent event) {
        //txtRaiting.setText(txtTime.getText());
    }

    @FXML
    void onNamechange2(KeyEvent event) throws IOException {
        
                
    }

    @FXML
    void onNamechange3(MouseEvent event) throws IOException {
        //txtRaiting.setText(txtCost.getText());
        
        //assertEquals(filname, "Matrix");
        if(!"".equals(txtName.getText())){
        ArrayList<String> res = facade.getSpectacleInfodop2(txtName.getText());
        txtnamerus.setText(res.get(0));
        txtCountry.setText(res.get(1));
        txtYear.setText(res.get(2));
        txtComposer.setText(res.get(3));
        txtActors.setText(res.get(4));
        txtScreenwriter.setText(res.get(5));
        txtDirector.setText(res.get(6)+"$");
        txtProducer.setText(res.get(7)+"$");
        txtOperator.setText(res.get(8));
        txtDesigin.setText(res.get(9));
        txtGenre.setText(res.get(10));
        txtRaiting.setText(res.get(11));
        txtDescription.setText(res.get(12));
        
        Image image = new Image(res.get(13));
        imgSpectacle.setImage(image);
        }
    }

    @FXML
    void onClickCancelButton(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
        @FXML
    void onClickAccesPayButton(ActionEvent event) {
            if(facade.spectacleAcessbuy(login, idSpectacle)){
            lblAnswer.setText("Success");
            btnWantPay.setVisible(false);
        }else
            lblAnswer.setText("Somsing wrong");
    }

    

    @FXML
    void onClickCashierPayButton(ActionEvent event) {
        if(facade.spectacleToCashier(login, idSpectacle)){
            lblAnswer.setText("Success");
            btnWantPay.setVisible(false);
        }else
            lblAnswer.setText("Somsing wrong");
    }

    @FXML
    void onClickCreateSeansButton(ActionEvent event) {
        LocalDate dateSeans = dpDateSeans.getValue();
        lblAnswer.setText(facade.CreateSeans(login, idSpectacle, dateSeans, txtHour.getText(), txtMinutes.getText()));
    }

    @FXML
    void onClickPayButton(ActionEvent event) {
            if(facade.spectaclePay(login, idSpectacle)){
            lblAnswer.setText("Success");
            btnWantPay.setVisible(false);
        }else
            lblAnswer.setText("Somsing wrong");
    }

    @FXML
    void onClickWantPayButton(ActionEvent event) {
        if(facade.spectacleWantBuy(login, idSpectacle)){
            lblAnswer.setText("Success");
            btnWantPay.setVisible(false);
        }else
            lblAnswer.setText("Somsing wrong");
    }

    @Override
    public void init(String login, String identificator2, String spectacleid) {
        facade = Facade.getInstance();
        this.login = login;
        this.idSpectacle = spectacleid;
        String type = facade.getPersonType(login);
        if(type.equals("RejPost")){
            if(identificator2.equals("create")){
                btnWantPay.setVisible(false);
                btnPay.setVisible(false);
                btnToCashierPay.setVisible(false);
                btnAccesPay.setVisible(false);
                
                txtDate.setVisible(false);
                dpDateSeans.setVisible(false);
                txtHour.setVisible(false);
                txtHourt.setVisible(false);
                txtMinutes.setVisible(false);
                txtMinutest.setVisible(false);
                btnCreateSeans.setVisible(false);
            }
            if(identificator2.equals("")){
                btnWantPay.setVisible(false);
                btnPay.setVisible(false);
                btnCreate.setVisible(false);
                btnToCashierPay.setVisible(false);
                txtName.setEditable(false);
                txtCost.setEditable(false);
                txtInfo.setEditable(false);
                txtTime.setEditable(false);
                
                txtDate.setVisible(false);
                dpDateSeans.setVisible(false);
                txtHour.setVisible(false);
                txtHourt.setVisible(false);
                txtMinutes.setVisible(false);
                txtMinutest.setVisible(false);
                btnCreateSeans.setVisible(false);
                try {
                    txtStatusSpectacle.setText(facade.getSpectacleStatus(spectacleid));
                } catch (IOException ex) {
                    Logger.getLogger(SpectacleFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    ArrayList<String> res = facade.getSpectacleInfo(spectacleid);
                    txtName.setText(res.get(0));
                    txtInfo.setText(res.get(1));
                    txtTime.setText(res.get(2));
                    txtCost.setText(res.get(3));
                } catch (IOException ex) {
                    Logger.getLogger(SpectacleFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            
        }
        if(type.equals("Manager")){
            if(identificator2.equals("")){
                btnWantPay.setVisible(true);
                btnPay.setVisible(false);
                btnCreate.setVisible(false);
                btnToCashierPay.setVisible(true);
                btnAccesPay.setVisible(false);
                txtName.setEditable(false);
                txtCost.setEditable(false);
                txtInfo.setEditable(false);
                txtTime.setEditable(false);
                txtDate.setVisible(true);
                try {
                    txtStatusSpectacle.setText(facade.getSpectacleStatus(spectacleid));
                } catch (IOException ex) {
                    Logger.getLogger(SpectacleFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    ArrayList<String> res = facade.getSpectacleInfo(spectacleid);
                    txtName.setText(res.get(0));
                    txtInfo.setText(res.get(1));
                    txtTime.setText(res.get(2));
                    txtCost.setText(res.get(3));
                } catch (IOException ex) {
                    Logger.getLogger(SpectacleFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            
        }
        if(type.equals("Cashier")){
            if(identificator2.equals("")){
                btnWantPay.setVisible(false);
                btnPay.setVisible(true);
                btnCreate.setVisible(false);
                btnToCashierPay.setVisible(false);
                btnAccesPay.setVisible(false);
                txtName.setEditable(false);
                txtCost.setEditable(false);
                txtInfo.setEditable(false);
                txtTime.setEditable(false);
                
                txtDate.setVisible(false);
                dpDateSeans.setVisible(false);
                txtHour.setVisible(false);
                txtHourt.setVisible(false);
                txtMinutes.setVisible(false);
                txtMinutest.setVisible(false);
                btnCreateSeans.setVisible(false);
                try {
                    txtStatusSpectacle.setText(facade.getSpectacleStatus(spectacleid));
                } catch (IOException ex) {
                    Logger.getLogger(SpectacleFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    ArrayList<String> res = facade.getSpectacleInfo(spectacleid);
                    txtName.setText(res.get(0));
                    txtInfo.setText(res.get(1));
                    txtTime.setText(res.get(2));
                    txtCost.setText(res.get(3));
                } catch (IOException ex) {
                    Logger.getLogger(SpectacleFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            
        }
        System.out.println("tap1");
    }

}

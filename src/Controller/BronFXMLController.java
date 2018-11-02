/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import facade.Facade;
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

/**
 * FXML Controller class
 *
 * @author Pavel_User
 */
public class BronFXMLController implements Initializable, ControllerWithIdentificator {

    String login;
    String idSpectacle;
    Facade facade;

    @FXML
    private Label txtName;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtTime;

    @FXML
    private Label txtRyad;

    @FXML
    private Label txtNum;

    @FXML
    private Label txtCost;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void init(String login, String identificator2, String bronid) {
        facade = Facade.getInstance();
        this.login = login;
        this.idSpectacle = bronid;
        String type = facade.getPersonType(login);
        if (type.equals("Client")) {
                try {
                txtName.setText(facade.getBronName(bronid));
                String cost = facade.getBronCost(bronid);
                    System.out.println(cost);
                txtCost.setText(cost);
                txtDate.setText(facade.getBronDate(bronid));
                txtNum.setText(facade.getBronNum(bronid));
                txtRyad.setText(facade.getBronRyad(bronid));
                txtTime.setText(facade.getBronTime(bronid));
                } catch (IOException ex) {
                    Logger.getLogger(BronFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (type.equals("Manager")) {
                if (identificator2.equals("")) {

                }

            }
            if (type.equals("Cashier")) {
                if (identificator2.equals("")) {

                }

            

        }
        System.out.println("tap1");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import facade.Facade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import teatrapp.TeatrApp;

/**
 * FXML Controller class
 *
 * @author Pavel_User
 */
public class RegisterFXMLController implements Initializable{

    Facade facade;
    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox<String> choiceBType;

    @FXML
    private TextField txtPass;

    @FXML
    private Label lblRegistration;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblAnswer;

    @FXML
    private TextField txtLogin;

    @FXML
    private Button btnCreate;
    
    @FXML
    private Button btnAuth;

    @FXML
    void onClickCreateBtn(ActionEvent event) {
        String name = txtName.getText();
        String login = txtLogin.getText();
        String pass = txtPass.getText();
        
        if (name.isEmpty() || login.isEmpty() || pass.isEmpty())
        {
            lblAnswer.setText("Please fill all fields");
            return;
        }
        int res = facade.registerPerson(login, name, pass, choiceBType.getValue());
        
        switch (res) {
            case -1:
                lblAnswer.setText("Error in program");
                break;
            case -2:
                lblAnswer.setText("Person alredy exist");
                break;
            case 0:
                lblAnswer.setText("Person registered");
                break;
            default:
                lblAnswer.setText("unexpected error");
        }
    }

    @FXML
    void onCLickAuthButton(ActionEvent event) {
        TeatrApp.setAuthenticationView();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        facade = Facade.getInstance();
        initChoiseBox();
        // TODO
    }   
    
    private void initChoiseBox() {
        String[] array = facade.getPersonesTypes();
        choiceBType.getItems().setAll(array);
        choiceBType.setValue(array[0]);
    }
    
    
}

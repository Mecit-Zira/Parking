package ch.parking.address.view;

import ch.parking.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mecit
 */
public class LoginController {
    
     @FXML
    private Label Statuslabel;
    
    @FXML
    private TextField Loginfield;
    
     @FXML
    private TextField Passwordfield;
     
    private Stage dialogStage;
    private boolean okClicked = false;
    private MainApp mainApp; 
 @FXML
    private void initialize() {
        
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
 
    }


    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
       public boolean isOkClicked() {
        return okClicked;
    }

       
    
     @FXML
    private void handleLogin() {
        if (Loginfield.getText().equals("") && Passwordfield.getText().equals("")
                || Loginfield.getText().equals("employee2") && Passwordfield.getText().equals("qwerty") ){
         okClicked = true;
         dialogStage.close();
    }   else if (Loginfield.getText().equals("supervisor") && Passwordfield.getText().equals("bestparking")) {
            okClicked = true;
         dialogStage.close();
         mainApp.supervisor = true;
    }   else if (Loginfield.getText().equals("manager") && Passwordfield.getText().equals("management")) {
            okClicked = true;
         dialogStage.close();
         mainApp.manager = true;
    }
        else {
            Statuslabel.setText("Login Failed");
    }
        
    }
     
}
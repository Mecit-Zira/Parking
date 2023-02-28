package ch.parking.address.view;

import ch.parking.address.MainApp;
import ch.parking.address.model.Parking;
import ch.parking.address.util.DateUtil;
import ch.parking.address.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mecit
 */
public class AutoController {
    @FXML
    private TextField vehiculeArivalField;
    @FXML
    private TextField vehiculeExitField;
    @FXML
    private TextField barcodeField;
    @FXML
    private TextField receivedMoneyField;
    @FXML
    private TextField renderedMoneyField;
    @FXML
    private Label dateLabel;
    @FXML
    private Label date2Label;

    private MainApp mainApp;
    private Stage dialogStage;
    private Parking parking;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
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

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param parking
     *
     */
    public void setParking(Parking parking) {
        this.parking = parking;
        
              int theOne = 0;
            
          for (Parking parking1 : mainApp.getParkingData()) {
    if(parking1.getBarcode() > theOne ) {
        theOne = parking1.getBarcode();

      }
    }
            theOne++;
            
            
        barcodeField.setText(Integer.toString(theOne));
        receivedMoneyField.setText(Integer.toString(10));
        renderedMoneyField.setText(Integer.toString(0));
       
        Date d = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
       String date=sdf.format(new Date());
       dateLabel.setText(sdf.format(d));
       date2Label.setText(sdf.format(d));
        SimpleDateFormat s = new SimpleDateFormat("HH:mm");
       vehiculeExitField.setText(s.format(d));

         
       vehiculeArivalField.setText(s.format(d));

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            
   
            parking.setVehiculeArival(TimeUtil.parse(vehiculeArivalField.getText()));
            parking.setVehiculeExit(TimeUtil.parse(vehiculeExitField.getText()));
            parking.setBarcode(Integer.parseInt(barcodeField.getText()));
            parking.setReceivedMoney(Integer.parseInt(receivedMoneyField.getText()));
            parking.setRenderedMoney(Integer.parseInt(renderedMoneyField.getText()));
            parking.setDate(DateUtil.parse(dateLabel.getText()));
            parking.setDate2(DateUtil.parse(date2Label.getText()));
           
            
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (vehiculeArivalField.getText() == null || vehiculeArivalField.getText().length() == 0) {
            errorMessage += "No valid time!\n";
        }
        if (vehiculeExitField.getText() == null || vehiculeExitField.getText().length() == 0) {
            errorMessage += "No valid time!\n";
        } 
        if (barcodeField.getText() == null || barcodeField.getText().length() == 0) {
            errorMessage += "No valid barcode!\n";
        }
        
        else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(barcodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Barcode !\n";
            }
        }

        if (receivedMoneyField.getText() == null || receivedMoneyField.getText().length() == 0) {
            errorMessage += "No valid !\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(receivedMoneyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid !\n";
            }
        }

        if (renderedMoneyField.getText() == null || renderedMoneyField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(renderedMoneyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid !\n";
            }
        }

 

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

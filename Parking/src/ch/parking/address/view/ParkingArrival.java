package ch.parking.address.view;


import ch.parking.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.parking.address.model.Parking;
import ch.parking.address.util.DateUtil;
import ch.parking.address.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;

/**
 * Dialog to edit details of a person.
 *
 * @author Mecit Zira
 */
public class ParkingArrival {



    @FXML
    private TextField barcodeField;
    @FXML
    private TextField receivedMoneyField;


    @FXML
    private Label dateLabel;
    
     @FXML
    private Label timeLabel;

    private Stage dialogStage;
    private Parking parking;
    private boolean okClicked = false;
    private MainApp mainApp;
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

  
    public void setParking(Parking parking) {
        this.parking = parking;
        /*timeLabel.setText(TimeUtil.format(parking.getVehiculeArival()));
        barcodeField.setText(Integer.toString(parking.getBarcode()));
        receivedMoneyField.setText(Integer.toString(parking.getReceivedMoney()));
        dateLabel.setText(DateUtil.format(parking.getDate()));*/
       Date d = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
       String date=sdf.format(new Date());
       dateLabel.setText(sdf.format(d));
    
       SimpleDateFormat s = new SimpleDateFormat("HH:mm");
       timeLabel.setText(s.format(d));
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
          boolean noDouble = true;
            
          for (Parking parking : mainApp.getParkingData()) {
    if(parking.getBarcode()==Integer.parseInt(barcodeField.getText())) {
        noDouble = false;
    }
    
    
}
             if (noDouble) {
            parking.setVehiculeArival(TimeUtil.parse(timeLabel.getText()));
            parking.setBarcode(Integer.parseInt(barcodeField.getText()));
            parking.setReceivedMoney(Integer.parseInt(receivedMoneyField.getText()));
            parking.setDate(DateUtil.parse(dateLabel.getText()));

            okClicked = true;
            dialogStage.close();
             } else {
                 
                  Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Barcode already here");
        alert.setHeaderText("Barcode already existing");
        alert.setContentText("Please change Barcode.");

        alert.showAndWait();
             }
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

        
     
        if (barcodeField.getText() == null || barcodeField.getText().length() == 0) {
            errorMessage += "No valid barcode!\n";
        }
        
        else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(barcodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Barcode (must be an integer)!\n";
            }
        }

        if (receivedMoneyField.getText() == null || receivedMoneyField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(receivedMoneyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid!\n";
            }
        }  

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
package ch.parking.address.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.parking.address.model.Parking;
import ch.parking.address.util.DateUtil;
import ch.parking.address.util.TimeUtil;

/**
 * Dialog to edit details of a person.
 *
 * @author Mecit Zira
 */
public class ParkingEditDialogController {

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
    private TextField dateField;
     @FXML
    private TextField date2Field;


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


        vehiculeArivalField.setText(TimeUtil.format(parking.getVehiculeArival()));
        vehiculeArivalField.setPromptText("HH:mm");
        
         vehiculeExitField.setText(TimeUtil.format(parking.getVehiculeExit()));
        vehiculeExitField.setPromptText("HH:mm");
        
        barcodeField.setText(Integer.toString(parking.getBarcode()));
        receivedMoneyField.setText(Integer.toString(parking.getReceivedMoney()));
        renderedMoneyField.setText(Integer.toString(parking.getRenderedMoney()));
        dateField.setText(DateUtil.format(parking.getDate()));
        dateField.setPromptText("dd.mm.yyyy");
        date2Field.setText(DateUtil.format(parking.getDate2()));
        dateField.setPromptText("dd.mm.yyyy");
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
            parking.setDate(DateUtil.parse(dateField.getText()));
            parking.setDate2(DateUtil.parse(date2Field.getText()));

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

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "No valid date!\n";
        } else {
            if (!DateUtil.validDate(dateField.getText())) {
                errorMessage += "No valid date. Use the format dd.mm.yyyy!\n";
            }
        }
        if (date2Field.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "No valid date!\n";
        } else {
            if (!DateUtil.validDate(dateField.getText())) {
                errorMessage += "No valid date. Use the format dd.mm.yyyy!\n";
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
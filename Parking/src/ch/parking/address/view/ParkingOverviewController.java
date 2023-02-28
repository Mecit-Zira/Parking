package ch.parking.address.view;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.parking.address.MainApp;
import ch.parking.address.model.Parking;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class ParkingOverviewController {
    @FXML
    private TableView<Parking> ParkingTable;
    @FXML
    private TableColumn<Parking, String> VehiculeArivalColumn;
    @FXML
    private TableColumn<Parking, String> VehiculeExitColumn;
     @FXML
    private TableColumn<Parking, Integer> BarcodeColumn;
   
     @FXML
    private TableColumn<Parking, Integer> ReceivedMoneyColumn;
    
     @FXML
    private TableColumn<Parking, Integer> RenderedMoneyColumn;
    
     @FXML
    private TableColumn<Parking, String> DateColumn;
     
     @FXML
    private TableColumn<Parking, String> Date2Column;

    
    
    
    
    // Reference to the main application.
    private MainApp mainApp;
  
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ParkingOverviewController() {
          
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the 5 columns.
 
   
         VehiculeExitColumn.setCellValueFactory(cellData -> cellData.getValue()
                 .VehiculeExitProperty().asString());
         VehiculeArivalColumn.setCellValueFactory(cellData -> cellData.getValue()
                 .VehiculeArivalProperty().asString());
         BarcodeColumn.setCellValueFactory(cellData -> 
                 cellData.getValue().BarcodeProperty().asObject());
         ReceivedMoneyColumn.setCellValueFactory(cellData ->
                 cellData.getValue().ReceivedMoneyProperty().asObject()); 
         RenderedMoneyColumn.setCellValueFactory(cellData ->
                 cellData.getValue().RenderedMoneyProperty().asObject());
         DateColumn.setCellValueFactory(cellData ->
                 cellData.getValue().DateProperty().asString()); 
         Date2Column.setCellValueFactory(cellData ->
                 cellData.getValue().Date2Property().asString()); 
    


     
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
       
        this.mainApp = mainApp;

        // Add observable list data to the table
        ParkingTable.setItems(mainApp.getParkingData());
        
    }
     
 
    
    
    @FXML
private void handleDeletePerson() {
    int selectedIndex = ParkingTable.getSelectionModel().getSelectedIndex();
    if (mainApp.manager) {
       
   if (selectedIndex >= 0) {
        ParkingTable.getItems().remove(selectedIndex);
        handleSave();
    } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");

        alert.showAndWait();
    }}
    else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Error");
        alert.setHeaderText("No pass");
        alert.setContentText("Only manager can delete !");

        alert.showAndWait();
    }
}

@FXML
private void handleNewParking() {
    Parking tempParking = new Parking();
    boolean okClicked = mainApp.showParkingEditDialog(tempParking);
    if (okClicked) {
        mainApp.getParkingData().add(tempParking);
        handleSave();
    }
}

/**
 * Called when the user clicks the edit button. Opens a dialog to edit
 * details for the selected person.
 */
@FXML
private void handleEditParking() {
   Parking selectedParking = ParkingTable.getSelectionModel().getSelectedItem();
   if (mainApp.supervisor || mainApp.manager) {
   if (selectedParking != null) {
        boolean okClicked = mainApp.showParkingEditDialog(selectedParking);
        
        if (okClicked) {
            handleSave();
            // showParkingDetails(selectedParking); est le code à ne pas oublier
        }

    } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Parking Selected");
        alert.setContentText("Please select a Parking in the table.");

        alert.showAndWait();
    }} else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Error");
        alert.setHeaderText("No pass");
        alert.setContentText("Only supervisor or manager can edit !");

        alert.showAndWait();
    }
}

public int receiveAuto = 0;
@FXML
private void handleAuto() {
    
    Parking tempParking = new Parking();
    boolean okClicked = mainApp.showAutoController(tempParking);
    if (okClicked) {
        mainApp.getParkingData().addAll(tempParking);
        handleAuto();
        handleSave();
    }
}

@FXML
private void handleDataprint() {
   
    boolean okClicked = mainApp.showDataPrintController();
    if (okClicked) {
       
    }
}

@FXML
private void handleNewParkingArrival() {
    Parking tempParking = new Parking();
    boolean okClicked = mainApp.showParkingArrival(tempParking);
    if (okClicked) {
        
        mainApp.getParkingData().add(tempParking);
        handleSave();
    }
}

@FXML
private void handleNewParkingExit() { 

     
        boolean okClicked = mainApp.showParkingExit();
        if (okClicked) {
           handleSave();
            
        }
  }



    
     


@FXML
public void handleReview() {
    Parking tempParking = new Parking();
 
    boolean okClicked = mainApp.showReviewController(tempParking);
    if (okClicked) {
        handleSave();
    }
}


    private void handleSave() {
        File parkingFile = mainApp.getParkingFilePath();
        if (parkingFile != null) {
            mainApp.saveParkingDataToFile(parkingFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveParkingDataToFile(file);
        }
    }

}



//  for (int i = 0; i < tableView.getItems().size(); i++) {
 //       if (tableView.getItems().get(i).getBarcode () == "ValeurRecherchée") {
 //           // modifies la valeur des colonnes VE, Rendered Money et Date de la ligne en cours
  //      }
 //   }
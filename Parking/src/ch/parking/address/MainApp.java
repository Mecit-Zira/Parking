package ch.parking.address;

import ch.parking.address.model.Parking;
import ch.parking.address.model.ParkingListWrapper;
import ch.parking.address.view.AutoController;
import ch.parking.address.view.DataPrintController;
import ch.parking.address.view.LoginController;
import ch.parking.address.view.ParkingArrival;
import ch.parking.address.view.ParkingEditDialogController;
import ch.parking.address.view.ParkingExit;
import ch.parking.address.view.ParkingOverviewController;
import ch.parking.address.view.ReviewController;
import ch.parking.address.view.RootLayoutController;
import javafx.scene.image.Image ;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public boolean manager = false;
    public boolean supervisor = false;
  
    
    
    private ObservableList<Parking> parkingData
            = FXCollections.observableArrayList();

  
    
    public ObservableList<Parking> getParkingData() {
        return parkingData;
    }

    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("manageParking");
        this.primaryStage.getIcons().add( new Image("/images/logo.png"));

        boolean okClicked = showLogin();
    if (okClicked) {
        
        initRootLayout();
        showParkingOverview();
    }
        
    }
    
    /**
     * Initializes the root layout. VNBB9N
     */
    public void initRootLayout() {
    try {
        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class
                .getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        this.primaryStage.getIcons().add( new Image("/images/logo3.png"));
     
        // Give the controller access to the main app.
        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Try to load last opened person file.
    File file = getParkingFilePath();
    if (file != null) {
        loadParkingDataFromFile(file);
    }
}

    /**
     * Shows the person overview inside the root layout.
     */
    public void showParkingOverview() {
    try {
        // Load person overview.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ParkingOverview.fxml"));
        AnchorPane parkingOverview = (AnchorPane) loader.load();
        
        // Set person overview into the center of root layout.
        rootLayout.setCenter(parkingOverview);
           
        // Give the controller access to the main app.
        ParkingOverviewController controller = loader.getController();
        controller.setMainApp(this);
        // bloque le changement de taille de initRootLayout
        primaryStage.setResizable(false); 

    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
 
    public boolean showLogin() {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        // Set the person into the controller.
        LoginController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public boolean showParkingEditDialog(Parking parking) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
   loader.setLocation(MainApp.class.getResource("view/ParkingEditDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        // Set the person into the controller.
        ParkingEditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setParking(parking);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

      public boolean showParkingArrival(Parking parking) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/ParkingArrival.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        
        // Set the person into the controller.
        ParkingArrival controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        controller.setParking(parking);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean showParkingExit() {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ParkingExit.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        
        // Set the person into the controller.
        ParkingExit controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        controller.setParking();

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
   
}    

public boolean showReviewController ( Parking parking ) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/Review.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        
        // Set the person into the controller.
        
        ReviewController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        controller.setParking(parking);
     
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean showAutoController(Parking parking) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
   loader.setLocation(MainApp.class.getResource("view/Auto.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        // Set the person into the controller.
        AutoController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        controller.setParking(parking);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean showDataPrintController () {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/DataPrint.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Parking");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add( new Image("/images/logo.png"));
        dialogStage.setResizable(false);
        
        // Set the person into the controller.
        
        DataPrintController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        controller.setParking();
    
     
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}


public File getParkingFilePath() {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    String filePath = prefs.get("filePath", null);
    if (filePath != null) {
        return new File(filePath);
    } else {
        return null;
    }
}

/**
 * Sets the file path of the currently loaded file. The path is persisted in
 * the OS specific registry.
 * 
 * @param file the file or null to remove the path
 */
public void setParkingFilePath(File file) {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    if (file != null) {
        prefs.put("filePath", file.getPath());

        // Update the stage title.
        primaryStage.setTitle("AddressApp - " + file.getName());
    } else {
        prefs.remove("filePath");

        // Update the stage title.
        primaryStage.setTitle("AddressApp");
    }
}

public void loadParkingDataFromFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(ParkingListWrapper.class);
        Unmarshaller um = context.createUnmarshaller();

        // Reading XML from the file and unmarshalling.
        ParkingListWrapper wrapper = (ParkingListWrapper) um.unmarshal(file);

        parkingData.clear();
        parkingData.addAll(wrapper.getParkings());

        // Save the file path to the registry.
        setParkingFilePath(file);

    } catch (Exception e) { // catches ANY exception
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load data");
      alert.setContentText("Could not load data from file:\n" + file.getPath());

        alert.showAndWait();
    }
}

/**
 * Saves the current person data to the specified file.
 * 
 * @param file
 */
public void saveParkingDataToFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(ParkingListWrapper.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Wrapping our person data.
        ParkingListWrapper wrapper = new ParkingListWrapper();
        wrapper.setParkings(parkingData);

        // Marshalling and saving XML to the file.
        m.marshal(wrapper, file);

        // Save the file path to the registry.
        setParkingFilePath(file);
    } catch (Exception e) { // catches ANY exception
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not save data");
        alert.setContentText("Could not save data to file:\n" + file.getPath());

        alert.showAndWait();
    }
}
  
}
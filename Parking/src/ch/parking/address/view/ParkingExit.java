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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Dialog to edit details of a person.
 *
 * @author Mecit Zira
 */
public class ParkingExit  {



    @FXML
    private TextField barcodeField;
    @FXML
    private TextField renderedMoneyField;


    @FXML
    private Label dateLabel;
    
     @FXML
    private Label timeLabel;
     
     @FXML 
    private TextArea area; 

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

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param parking
     *
     */
    public void setParking() {
      

      
        barcodeField.setText(Integer.toString(0));
        renderedMoneyField.setText(Integer.toString(0));
        

        
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
          boolean noBarcode = true;
          
          for (Parking parking : mainApp.getParkingData()) {
    if(parking.getBarcode()==Integer.parseInt(barcodeField.getText())) {
            parking.setBarcode(Integer.parseInt(barcodeField.getText()));
            parking.setVehiculeExit(TimeUtil.parse(timeLabel.getText()));
            parking.setRenderedMoney(Integer.parseInt(renderedMoneyField.getText()));
            parking.setDate2(DateUtil.parse(dateLabel.getText()));
            
               
          noBarcode = false;
          okClicked = true; 
            dialogStage.close();
    }   
}
         
          if  (noBarcode ) {
             Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Existing barcode");
        alert.setContentText("Please select a Parking in the table.");

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
            errorMessage += "No valid street!\n";
        }
        
        else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(barcodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Barcode (must be an integer)!\n";
            }
        }

        if (renderedMoneyField.getText() == null || renderedMoneyField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(renderedMoneyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
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
    
    
    //partie pour imprimer
    public PageFormat getPageFormat(PrinterJob pj)
{
    
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double middleHeight =8.0;  
    double headerHeight = 2.0;                  
    double footerHeight = 2.0;                  
    double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
    double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(                    
        0,
        10,
        width,            
        height - convert_CM_To_PPI(1)
    );   //define boarder size    after that print area width is about 180 points
            
    pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
    pf.setPaper(paper);    

    return pf;
}
    
    protected static double convert_CM_To_PPI(double cm) {            
	        return toPPI(cm * 0.393600787);            
}
 
protected static double toPPI(double inch) {            
	        return inch * 72d;            
}
public class BillPrintable implements Printable {
    
   
    
    
  public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
  throws PrinterException 
  {    
      
                
        
      int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    

            double width = pageFormat.getImageableWidth();                    
           
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

            FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        //    int idLength=metrics.stringWidth("000000");
            //int idLength=metrics.stringWidth("00");
            int idLength=metrics.stringWidth("000");
            int amtLength=metrics.stringWidth("000000");
            int qtyLength=metrics.stringWidth("00000");
            int priceLength=metrics.stringWidth("000000");
            int prodLength=(int)width - idLength - amtLength - qtyLength - priceLength-17;

        //    int idPosition=0;
        //    int productPosition=idPosition + idLength + 2;
        //    int pricePosition=productPosition + prodLength +10;
        //    int qtyPosition=pricePosition + priceLength + 2;
        //    int amtPosition=qtyPosition + qtyLength + 2;
            
            int productPosition = 0;
            int discountPosition= prodLength+5;
            int pricePosition = discountPosition +idLength+10;
            int qtyPosition=pricePosition + priceLength + 4;
            int amtPosition=qtyPosition + qtyLength;
            
            
              
        try{
            /*Draw Header*/
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;
            int headerRectHeighta=40;
            int totalPrice= 0;
            
                         for (Parking parking : mainApp.getParkingData()) {
    if(parking.getBarcode()==Integer.parseInt(barcodeField.getText())) {
        totalPrice= parking.getReceivedMoney() - parking.getRenderedMoney();
    }
             }
           
            ///////////////// Product names Get ///////////


            ///////////////// Product names Get ///////////
                
            
            ///////////////// Product price Get ///////////
                
            ///////////////// Product price Get ///////////
                
             g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
            g2d.drawString("-------------------------------------",12,y);y+=yShift;
            g2d.drawString("         Parking Bill Receipt        ",12,y);y+=yShift;
            g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
      
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" "+totalPrice*0.25+"       Taxe      ",10,y);y+=yShift;
            g2d.drawString(" "+totalPrice*0.75+"       T.Price   ",10,y);y+=yShift;
            g2d.drawString(" "+totalPrice+"            T.Price   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
            g2d.drawString(" "+timeLabel.getText()+"             ",10,y);y+=yShift;
            g2d.drawString(" "+dateLabel.getText()+"             ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;

            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("          Free Home Delivery         ",10,y);y+=yShift;
            g2d.drawString("             03111111111             ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("      THANKS TO VISIT OUR PARKING    ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
                   
           
             
           
            
//            g2d.setFont(new Font("Monospaced",Font.BOLD,10));
//            g2d.drawString("Customer Shopping Invoice", 30,y);y+=yShift; 
          

    }
    catch(Exception r){
    r.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }
   }

}
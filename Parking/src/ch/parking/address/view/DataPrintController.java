package ch.parking.address.view;

import ch.parking.address.MainApp;
import ch.parking.address.model.Parking;
import ch.parking.address.util.DateUtil;
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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mecit
 */
public class DataPrintController {
    
    @FXML
    private TextField Date;
    @FXML
    private TextField Date2;
    
    
    private Stage dialogStage;
    private Parking parking;
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
    
    public void setParking() {
      
       Date d = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
       String date=sdf.format(new Date());
       Date2.setText(sdf.format(d));
    
    }
    
     public boolean isOkClicked() {
        
        return okClicked;
    }
     
      @FXML
    private void handleOk() {
            
               PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
          
          okClicked = true; 
          dialogStage.close();
     
}
         
      @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    
    public PageFormat getPageFormat(PrinterJob pj)
{
    
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double middleHeight =20.0;  
    double headerHeight = 5.0;                  
    double footerHeight = 5.0;                  
    double width = convert_CM_To_PPI(20);      //printer know only point per inch.default value is 72ppi
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

            ////////// code by alqama//////////////

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

                         
             
           
            ///////////////// Product names Get ///////////


            ///////////////// Product names Get ///////////
                
            
            ///////////////// Product price Get ///////////
                
            ///////////////// Product price Get ///////////
                
             g2d.setFont(new Font("Monospaced",Font.PLAIN,10));
            g2d.drawString("----------------------------------------------------------------------------------------",12,y);y+=yShift;
            g2d.drawString("                                      Parking Report                                    ",12,y);y+=yShift;
            g2d.drawString("----------------------------------------------------------------------------------------",12,y);y+=headerRectHeight;
            
            g2d.drawString("Barcode       Received    Rendered     Arival      Exit         Date          Date    ",12,y);y+=yShift;
            g2d.drawString("              money       money                                 in            out     ",12,y);y+=yShift;
            
            for (Parking parking : mainApp.getParkingData()) {
            g2d.drawString("  "+parking.getBarcode()+"    -    "+parking.getReceivedMoney()+"    -    "+parking.getRenderedMoney()+""
                    + "    -    "+parking.getVehiculeArival()+"    -    "+parking.getVehiculeExit()+"    -  "+parking.getDate()+"  -  "+parking.getDate2()+"  -",12,y);y+=yShift;
            g2d.drawString("--------------------------------------------------------------------------------",12,y);y+=yShift;
            }
 
            
            
            
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
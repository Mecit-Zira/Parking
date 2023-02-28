package ch.parking.address.model;

import ch.parking.address.util.LocalDateAdapter;
import ch.parking.address.util.LocalTimeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Model class for a Person.
 *
 * @author Mecit Zira
 */
public class Parking {

    private final ObjectProperty<LocalTime> VehiculeArival;
    private final ObjectProperty<LocalTime> VehiculeExit;
    private final IntegerProperty Barcode;
    private final IntegerProperty ReceivedMoney ;
    private final IntegerProperty RenderedMoney;
    private final ObjectProperty<LocalDate> Date;
    private final ObjectProperty<LocalDate> Date2;
   
    
   
    /**
     * Default constructor.
     */
    public Parking() {
        this(0, 0,0,0,0,0,0);
    }
    
    
    
    /**
     * Constructor with some initial data.
     * 
     * @param Barcode
     * @param VehiculeArival
     * @param VehiculeExit
     * @param ReceivedMoney    
     * @param RenderedMoney
     * @param Date
     * @param Date2
     */
    public Parking( Integer Barcode , Object VehiculeArival, Integer ReceivedMoney , Object VehiculeExit, Integer RenderedMoney,Object Date,Object Date2  ) {
        
        this.Barcode = new SimpleIntegerProperty(Barcode);
        this.VehiculeArival = new SimpleObjectProperty<LocalTime>(LocalTime.of(10, 00));
        this.VehiculeExit = new SimpleObjectProperty<LocalTime>(LocalTime.of(00, 00));
        this.ReceivedMoney = new SimpleIntegerProperty(ReceivedMoney);
        this.RenderedMoney = new SimpleIntegerProperty(RenderedMoney);
        this.Date = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.Date2 = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }
    
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getVehiculeArival() {
        return VehiculeArival.get();
    }

    public void setVehiculeArival(LocalTime VehiculeArival) {
        this.VehiculeArival.set(VehiculeArival);
    }
    
    public ObjectProperty<LocalTime> VehiculeArivalProperty() {
        return VehiculeArival;
    }
    
   @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getVehiculeExit() {
        return VehiculeExit.get();
    }

    public void setVehiculeExit(LocalTime VehiculeExit) {
        this.VehiculeExit.set(VehiculeExit);
    }
    
    public ObjectProperty<LocalTime> VehiculeExitProperty() {
        return VehiculeExit;
    }


    public int getBarcode() {
        return Barcode.get();
    }

    public void setBarcode(int Barcode) {
        this.Barcode.set(Barcode);
    }
    
    public IntegerProperty BarcodeProperty() {
        return Barcode;
    }

    public int getReceivedMoney() {
        return ReceivedMoney.get();
    }

    public void setReceivedMoney(int ReceivedMoney) {
        this.ReceivedMoney.set(ReceivedMoney);
    }
    
    public IntegerProperty ReceivedMoneyProperty() {
        return ReceivedMoney;
    }

    public Integer getRenderedMoney() {
        return RenderedMoney.get();
    }

    public void setRenderedMoney(Integer RenderedMoney) {
        this.RenderedMoney.set(RenderedMoney);
    }
    
    public IntegerProperty RenderedMoneyProperty() {
        return RenderedMoney;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return Date.get();
    }

    public void setDate(LocalDate Date) {
        this.Date.set(Date);
    }
    
    public ObjectProperty<LocalDate> DateProperty() {
        return Date;
    }
    
     @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate2() {
        return Date2.get();
    }

    public void setDate2(LocalDate Date2) {
        this.Date2.set(Date2);
    }
    
    public ObjectProperty<LocalDate> Date2Property() {
        return Date2;
    }

    
}
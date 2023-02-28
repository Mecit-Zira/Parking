package ch.parking.address.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of parking. This is used for saving the
 * list of persons to XML.
 * 
 * @author Mecit Zira
 */
@XmlRootElement(name = "parking")
public class ParkingListWrapper {

    private List<Parking> parkings;

    @XmlElement(name = "parking")
    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }
}
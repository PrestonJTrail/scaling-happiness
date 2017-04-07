package monitoringoffice;
import java.io.Serializable;
/**
 * @author Preston J. Trail
 */
public class TrafficReport implements Serializable
{
    String Time;
    String Location;
    String NoLanes;
    String TotalVehicles;
    String AvgVehicles;
    String AvgVelocity;

    // Constructor for when the object is required but no data is sent.
    public TrafficReport()
    {
        Time = "12:00 AM";
        Location = "0";
        NoLanes = "0";
        TotalVehicles = "0";
        AvgVehicles = "0";
        AvgVelocity = "0";
    }
    
    // For if six separate pieces of data are sent to the object.
    public TrafficReport(String D1, String D2, String D3, String D4, String D5, String D6)
    {
        Time = D1;
        Location = D2;
        NoLanes = D3;
        TotalVehicles = D4;
        AvgVehicles = D5;
        AvgVelocity = D6;
    }
    
    // Used when data is received as a comma delimited line.
    // It "unpacks" a single string received from one of the
    // monitoring stations, which will just be all the data
    // separated by commas.
    public TrafficReport(String str)
    {
        String temp[] = str.split(",");
        Time = temp[0];
        Location = temp[1];
        NoLanes = temp[2];
        TotalVehicles = temp[3];
        AvgVehicles = temp[4];
        AvgVelocity = temp[5];
    }
    
    public TrafficReport(String str1, String str2, String str3, String str4)
    {
        Time = str1;
        Location = str2;
        AvgVehicles = str3;
        AvgVelocity = str4;
    }
    
    // Get all the data from the report.
    public String getTrafficReport()
    {
        return Time + "," + Location + "," + AvgVehicles + "," + AvgVelocity;
    }
    
    // Get only the data which displays in the table on the main window.
    public String getTableDataRow()
    {
        return Time + "," + Location + "," + AvgVehicles + "," + AvgVelocity;
    }
    
    // Get only the data which displays in the linked list.
    public String getLinkedListOutput()
    {
        return Time + "," + AvgVehicles + "," + AvgVelocity;
    }
    
    // When a user searches for a report, they will search by the time and location,
    // separated by an underscore. 
    // The time and location are put into one string separated by this underscore,
    // and is searched for against the user's search query.
    public String getTimeLocation()
    {
        return Time + "_" + Location;
    }

//<editor-fold defaultstate="collapsed" desc="getsAndSets">
    
    public String getTime()
    {
        return Time;
    }

    public void setTime(String Time)
    {
        this.Time = Time;
    }

    public String getLocation()
    {
        return Location;
    }

    public void setLocation(String Location)
    {
        this.Location = Location;
    }

    public String getNoLanes()
    {
        return NoLanes;
    }

    public void setNoLanes(String NoLanes)
    {
        this.NoLanes = NoLanes;
    }

    public String getTotalVehicles()
    {
        return TotalVehicles;
    }

    public void setTotalVehicles(String TotalVehicles)
    {
        this.TotalVehicles = TotalVehicles;
    }

    public String getAvgVehicles()
    {
        return AvgVehicles;
    }

    public void setAvgVehicles(String AvgVehicles)
    {
        this.AvgVehicles = AvgVehicles;
    }

    public String getAvgVelocity()
    {
        return AvgVelocity;
    }

    public void setAvgVelocity(String AvgVelocity)
    {
        this.AvgVelocity = AvgVelocity;
    }
    
//</editor-fold> 
}
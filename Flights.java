/**
 * This the flight class representing flight objects
 * author - A K M NAHARUL HAYAT; 1750583; K1764014
 */
public class Flights
{
    //variables which make up the flight class
    private final String code;
    private final String origin;
    private final String destination;
    private final String date;
    private final String departureTime;
    private final String arrivalTime;
    private final String airline;
    private final String type;
    private final double price;
    //constructor for creating flight objects
    public Flights(String code, String origin, String destination,String date, String departureTime, String arrivalTime, String airline, double price,String type)
    {
        this.code = code;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airline = airline;
        this.price = price;
        this.type = type;
    }
    /**
     * @return the date of the flight
     */
    public String getDate() {return date;}
    /**
     * @return the origin or the starting airport of the flight
     */
    public String getOrigin(){return origin;}
    /**
     * @return full detail of a particular flight
     */
    public String getDetails() 
    {
        return "code = "  +code + 
        ", origin = " + origin + 
        ", destination = " + destination + 
        ", date = " + date +
        ", departure time = " + departureTime + 
        ", arrival time = " + arrivalTime + ", airline = " + airline + ", price = " + price + ", type = " + type;
    }
}

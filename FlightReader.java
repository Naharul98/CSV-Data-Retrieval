import java.io.IOException;
import java.nio.file.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;
/**
 * This the  class, responsible for reading data off the csv file and creating flight objects
 * this class also the method called searchFlight
 * which enables searching of flight based on the criteria input
 * to run this program, call the MAIN method
 * author - A K M NAHARUL HAYAT; 1750583; K1764014
 */
public class FlightReader
{
    //number of fields in the csv data file
    private static final int NUMBER_OF_FIELDS = 9;
    //index of the respective columns in the csv data file
    private static final int CODE = 0,ORIGIN = 1,DESTINATION = 2,DATE = 3,DEPARTURETIME = 4,ARRIVALTIME = 5,AIRLINE = 6,PRICE = 7,TYPE = 8;
    //the list of flight objects
    private List<Flights> flights;
    //the csv data file name
    private String dataFileName = "Flights.csv";
    /**
     * this is the constructor, this method calls the getFlights method,
     * with the appropriate root data file name
     * and in effect, flight objects from the csv file is created
     */
    public FlightReader()
    {
        // initialise instance variable
        flights = new ArrayList<Flights>();
        flights.addAll(getFlights(dataFileName));
    }
    /**
     * this is the main method, pops up a commandline for taking input from the user
     * takes airportName and date input from the user, and displays matching flight criteria
     * if data input not valid, then a messege is displayed accordingly
     */
    public static void main(String[] args)
    {
        boolean quit = false;
        FlightReader flightReader = new FlightReader();
        Scanner inputReader = new Scanner(System.in);
        while(quit==false)
        {
            System.out.println("Enter starting airport: ");
            String airport = inputReader.next().trim();
            System.out.println("Enter date String: ");
            String date = inputReader.next().trim();
            if(checkDateValidity(date) == false)
            {System.out.println("error - date format/range is invalid, please input date from 1 to 7");}
            else {flightReader.searchFlights(airport, date);}

        }
    }
    /**
     * this method reads data file and creates flight objects from the csv file
     * @return arraylist of flight objects
     */
    public ArrayList<Flights> getFlights(String filename)
    {
        Function<String, Flights> createFlights = 
            record -> {
                String[] parts = record.split(",");
                if(parts.length == NUMBER_OF_FIELDS) {
                    try { 
                        String code = parts[CODE].trim();
                        String origin = parts[ORIGIN].trim();
                        String destination = parts[DESTINATION].trim();
                        String date = parts[DATE].trim();
                        String departureTime = parts[DEPARTURETIME].trim();
                        String arrivalTime = parts[ARRIVALTIME].trim();
                        String airline = parts[AIRLINE].trim();
                        double price = Double.parseDouble(parts[PRICE].trim());
                        String type = parts[TYPE].trim();
                        return new Flights(code, origin, destination,date, departureTime, arrivalTime,airline,price,type);
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Flight records have a malformed data index: " + record);
                        return null;
                    }
                }
                else {
                    System.out.println("Flight record has the wrong number of fields: " + record);
                    return null;
                }
            };
        ArrayList<Flights> flights;
        try {

            flights = Files.lines(Paths.get(filename))// Get file
            .filter(record -> record.length() > 0 && record.charAt(0) != '#')        //  Check if line contains data
            .map(createFlights)// Create object
            .filter(x -> x != null)// Filter null objects
            .collect(Collectors.toCollection(ArrayList::new));// Initiate ArrayList with objects
        }
        catch(IOException e) {
            System.out.println("Unable to open " + filename);
            flights = new ArrayList<>();
        }
        return flights;
    }
    /**
     * this is the method for searching the flight, according to the airportname and the date criteria
     */
    public void searchFlights(String airportName, String date)
    {
        List<Flights> flightToDisplay = new ArrayList<>();
        for(Flights flight:flights)
        {   //filter flight with the criteria input and put it in the arraylist above
            if(airportName.toUpperCase().equals(flight.getOrigin())  && dateComparison(date,flight.getDate()))
            {
                flightToDisplay.add(flight);
            }
        }
        if(flightToDisplay.isEmpty() == true)
        {   //check if the flightToDisplay is empty, if it is display a messsage 
            System.out.println("no matching flight criteria found");
        }
        else
        {   //if there is actually flights to display which matches the criteria, then display it
            for(Flights flight:flightToDisplay)
            {
                System.out.println(flight.getDetails());
            }
        }
    }
    /**
     * this is a helper method, to compare the date input with the actual date in the data
     * @return true if any part of the date input is matched with any part of the actual 'date' data, false if otherwise
     */
    private Boolean dateComparison(String dateInput,String actualDate)
    {
        String[] literals = dateInput.split("");
        for(String x:literals)
        {
            if(actualDate.contains(x))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * this is a helper method to check if date input is within the valid range or not
     * @return true, if the date is in a valid data format, otherwise returns false
     */
    private static boolean checkDateValidity(String date)
    {   
        if(date.contains("0") || date.contains("8") ||date.contains("9"))
        {return false;}
        try
        {
            Integer dateInput = Integer.parseInt(date);
            return true;
        }
        catch(Exception e) {return false;}

    }
}

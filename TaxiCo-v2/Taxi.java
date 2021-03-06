/**
 * A taxi.
 * Taxis have a unique ID, a location and sometimes a destination.
 * They are either free or occupied.
 * 
 * @author David J. Barnes 
 * @version 2010.12.03
 */
public class Taxi extends Vehicle
{
   
    private boolean free;

    /**
     * Constructor for objects of class Taxi.
     * @param base The name of the company's base.
     * @param id This taxi's unique id.
     */
    public Taxi(String base, String id)
    {
        super(id);
        location = base;
        destination = null;
        free = true;
    }
    
    /**
     * Book this taxi to the given destination.
     * The status of the taxi will no longer be free.
     * @param destination The taxi's destination.
     */
    public void book(String destination)
    {
        setDestination(destination);
        free = false;
    }

    /**
     * Return the status of this taxi.
     * @return The status.
     */
    public String getStatus()
    {
        String string = getID() + " at " + getLocation() + " headed for ";
        if(getDestination()==null) 
        {
        	string = string + "nowhere (it's free)";
        }
        else
        {
        	string = string + getDestination();
        }
        return string;
    }
    
    
    /**
     * Indicate that this taxi has arrived at its destination.
     * As a result, it will be free.
     */
    public void arrived()
    {
        location = destination;
        destination = null;
        free = true;
    }
}

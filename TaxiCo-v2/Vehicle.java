public class Vehicle{
	 // A unique ID for this taxi.
    protected String id;
    // The destination of this taxi.
    protected String destination;
    // The location of this taxi.
    protected String location;
    // Whether it is free or not.
	
    Vehicle(String id){
    	this.id=id;
    }
    
    /**
     * Return the status of this vehicle.
     * @return The status.
     */
    public String getStatus()
    {
        return "Vehicle "+getID();
    }
    
    /**
     * Return the ID of the vehicle.
     * @return The ID of the vehicle.
     */
    public String getID()
    {
        return id;
    }
    
    /**
     * Return the location of the vehicle.
     * @return The location of the vehicle.
     */
    public String getLocation()
    {
        return location;
    }
    
    /**
     * Return the destination of the vehicle.
     * @return The destination of the vehicle.
     */
    public String getDestination()
    {
        return destination;
    }
    
    /**
     * Set the intented destination of the vehicle
     * @param destination The intended destination.
     */
    public void setDestination(String destination)
    {
        this.destination = destination;
    }
}
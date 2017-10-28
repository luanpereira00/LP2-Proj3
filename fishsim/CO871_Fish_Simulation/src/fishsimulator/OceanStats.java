package fishsimulator;
import java.util.HashMap;

/**
 * This class collects and provides some statistical data on the state 
 * of a ocean. It is flexible: it will create and maintain a counter 
 * for any class of object that is found within the ocean.
 * 
 * @author David J. Barnes and Michael Kolling
 * @author Richard Jones
 * @version 2006.9.20
 */
public class OceanStats
{
    @SuppressWarnings("rawtypes")
	private HashMap<Class,Counter> counters;
    private boolean countsValid;

    /**
     * Construct a ocean-statistics object.
     */
    @SuppressWarnings("rawtypes")
	public OceanStats()
    {
        counters = new HashMap<Class,Counter>();
        countsValid = true;
    }

    /**
     * @return A string describing what animals are in the ocean.
     */
    public String getPopulationDetails(Ocean ocean)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(ocean);
        }

        for(@SuppressWarnings("rawtypes") Class key : counters.keySet()) {
            Counter info = counters.get(key);
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Invalidate the current set of statistics; reset all 
     * counts to zero.
     */
    public void reset()
    {
        countsValid = false;
        for(@SuppressWarnings("rawtypes") Class key : counters.keySet()) {
            Counter cnt = counters.get(key);
            cnt.reset();
        }
    }
    
    /**
     * Return the part of the string after the final '.'
     * @param s
     * @return
     */
    private String tail(String s)
    {
        int n = s.lastIndexOf(".");
        if (n < 0)
            return s;
        return s.substring(n + 1);
    }
    /**
     * Increment the count for one class of animal.
     */
    public void incrementCount(@SuppressWarnings("rawtypes") Class animalClass)
    {
        Counter cnt = counters.get(animalClass);
        if(cnt == null) {
            cnt = new Counter(tail(animalClass.getName()));
            counters.put(animalClass, cnt);
        }
        cnt.increment();
    }

    /**
     * Indicate that an animal count has been completed.
     */
    public void countFinished()
    {
        countsValid = true;
    }

    /**
     * Determine whether the simulation is still viable.
     * I.e., should it continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Ocean ocean)
    {
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(ocean);
        }
        for(@SuppressWarnings("rawtypes") Class key : counters.keySet()) {
            Counter info = counters.get(key);
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Generate counts of the number each different kind of fish
     * in the ocean.
     * @param ocean The ocean to generate the stats for.
     */
    private void generateCounts(Ocean ocean)
    {
        reset();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                Fish animal = ocean.getFishAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}

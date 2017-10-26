package fishsimulator;

import java.io.Serializable;
import java.util.List;

/**
 * This is the superclass for all kinds of fish
 * 
 * @author jdb 
 * @version 28/11/2008
 */
public abstract class Fish implements Serializable
{
	protected double weight;
	protected double viableWeight;
	protected double weightReduce;
    protected double breedWeight;
    protected int breedAge;
    protected int maxAge;
    protected int status; // how high up the food chain
	protected int age = 0;
	protected int step = -1;
	protected final FishParams initialParams;
	
    /**
     * Create a new fish
     * @param cell fish location
     * @param params parameters for the new fish
     */
    public Fish(FishParams params)
    {
    	initialParams = params;
        weight = params.getInitWeight();
        viableWeight = params.getViableWeight();
        weightReduce = params.getWeightReduce();
        breedWeight = params.getBreedWeight();
        breedAge = params.getBreedAge();
        maxAge = params.getMaxAge();
		age = 0;
	}
        
    /**
     * Create a new fish of the same kind
     * @param cell - location of spawned fish
     * @return the fish spawned
     */
    abstract Fish spawn(Cell cell);
    
    public boolean isAlive() {
    	return !(weight < viableWeight || age > maxAge);
    	
    }    
        
	/**
	 * Consume a given weight of food
	 * @param w weight of food consumed
	 */
	public void eat(double w)
	{
		weight += w;
	}
	
    /**
     * Accessor
     * @return status of this fish
     */
    public int getStatus() {
        return status;
    }
        
    /**
     * Accessor
     * @return weight of this fish
     */
	public double getWeight()
	{
		return weight;
	}
	
    /**
     * Mutator
     * @param weight new wightn of this fish
     */
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	/**
	 * Move the fish to a new cell
	 * @param cell destination
	 */
	public void move(Cell currentCell, Cell nextCell)
	{
		nextCell.setFish(this);
		//System.out.println("Movendo");
		currentCell.setFish(null);
	}
	
	protected abstract void eat(Cell currentCell, List<Cell> neighbours);
	
	protected abstract void breed(Cell currentCell, List<Cell> asList);
	
    /**
     * Called for each fish once per simulator step
     * @param step incrementing step number
     */
	//public abstract void act(int step, Cell currentCell);
}

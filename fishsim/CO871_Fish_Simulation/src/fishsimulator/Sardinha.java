package fishsimulator;

import java.util.Arrays;
import java.util.List;

/**
 * The Sardinha fish class
 * @author jdb
 * @version 28/11/2008
 */
public class Sardinha extends Fish {
	private double planctonEaten;
	
    /**
     * Construct a new herring
     * @param cell fish location
     * @param params parameters for the new fish
     */
    public  Sardinha(FishParams params) {
        super(params);
        planctonEaten = params.getPlanctonEaten();
        status = 1;
    }
    
    /**
     * Return a new fish of the same kind
     * @param cell fish location
     * @return new fish
     */
    public Fish spawn(Cell cell) {
        cell.createFish("sardinha", initialParams);
        return cell.getFish();
    }
    
//FIXME is List<Cell> neighbours necessary?
    protected void eat(Cell currentCell, List<Cell> neighbours) {
    	double p = currentCell.getPlancton();
    	if (p > planctonEaten) {
    		eat(planctonEaten);
    		p -= planctonEaten;
    	} else {
    		eat(p);
    		p = 0; 
    	}
    	
    	currentCell.setPlancton(p);
    	
    	// burn some weight and see if we are still viable
		weight *= weightReduce;
	}
    
    /**
     * @param cell
     * @param cells
     * @return
     */
    private Cell moviment(Cell cell, List<Cell> cells) {
		// TODO Auto-generated method stub
    	Cell bestNeighbour = null;
    	//Cell cells[] = cell.neighbours(1, true);
        for (Cell c: cells) {
        	//if (c.getFish() != null) continue;
			if (bestNeighbour == null && c.getFish()==null) {
				// || c.getPlancton() > bestNeighbour.getPlancton()) bestNeighbour = c;
				bestNeighbour = c;
			}else if(bestNeighbour != null && c.getFish() == null && (bestNeighbour.getPlancton()<c.getPlancton())) {
				bestNeighbour = c;
			}
		}
        return bestNeighbour;
	}
    
    /* (non-Javadoc)
     * @see fishsimulator.Fish#breed(java.util.List)
     */
    @Override
    protected void breed(Cell currentCell, List<Cell> neighbours) {
		// TODO Auto-generated method stub
    	
    	// look for the neighboring cell with the most plancton
	    // and no other fish
		Cell bestNeighbour = null;
		bestNeighbour = moviment(currentCell, neighbours);
		if (bestNeighbour == null) return;
		//System.out.println("Breeding");
    	if (weight >= breedWeight && age > breedAge) {
			Fish child = spawn(bestNeighbour);
			//System.out.println("Child");
            child.setWeight(weight * 0.4);
			weight *= 0.6;
		} else {
			
			if (bestNeighbour.getPlancton() > currentCell.getPlancton()) move(currentCell, bestNeighbour);
		}
	}
        
    /**
     * Iterate this fish through one simulator cycle
     * @param step counter that should be incremented for each
     * call. Used to avoid the same fish acting more than once
     * in a cycle
     */

	public void act(int step, Cell currentCell) {
		
	}	
}

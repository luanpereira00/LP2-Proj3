package fishsimulator;

import java.util.Arrays;
import java.util.List;

/**
 * A fish that eats other fish - either a Atum or a Tubarao
 * @author jdb
 */
public abstract class Predator extends Fish {

    protected int huntDistance;
    protected double maxEat;

    /**
     * Construct a new predator
     * @param cell location
     * @param params prameters
     */
    public Predator(FishParams params) {
        super(params);
        huntDistance = params.getHuntDistance();
        maxEat = params.getMaxEat();
    }
    
    public int getHuntDistance() {
    	return huntDistance;
    }
    
	@Override
    protected void breed(Cell currentCell, List<Cell> cells) {
		// TODO Auto-generated method stub
		if (weight > breedWeight && age >= breedAge) {
            Cell c = Cell.random(cells);
            if (c != null) {
                Fish child = spawn(c);
                child.setWeight(weight / 2);
                weight /= 2;
            }
        } else {
            // Otherwise just swim to a neighbouring empty cell
            Cell c = Cell.random(cells);
            if (c != null)
                move(currentCell, c);
        }
	}
	
    /**
     * Called once for each iteration step
     * @param step iteration counter
     */
   // @Override
    /*public void act(int step, Cell currentCell) {
        if (this.step == step) {
            return;
        }
        this.step = step;
        this.age++;

        // Eat as many fish as are in the neighbourhood
        // in decreasing order by status
        Cell neighbours[] = currentCell.neighbours(huntDistance);
        Arrays.sort(neighbours);
        eat(currentCell, Arrays.asList(neighbours));

        // Apply our weightloss and see if we are too light to live
        weight *= weightReduce;
        if (!isAlive()) {
            currentCell.setFish(null);
            return;
        }
        
        // If we are qualified to breed then do so
        // by splitting in two.
        breed(currentCell, Arrays.asList(currentCell.neighbours(huntDistance, true)));
    }*/
}

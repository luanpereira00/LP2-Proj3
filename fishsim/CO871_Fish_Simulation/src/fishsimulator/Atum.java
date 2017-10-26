package fishsimulator;

import java.util.List;

/**
 * Blue groper fish class
 * @author jdb
 */
public class Atum extends Predator {

    /**
     * Create a new blue groper
     * @param cell location of the fish
     * @param params initial parameters
     */
    public Atum(FishParams params)
    {
        super(params);
        status = 2;
    }

    /**
     * Create another groper
     * @param cell location for the new fish
     * @return the fish spawned
     */
    public Fish spawn(Cell cell)
    {
        cell.createFish("atum", initialParams);
        return cell.getFish();
    }
    
    @Override
	protected void eat(Cell currentCell, List<Cell> neighbours) {
		double eaten = 0.0;
        for (Cell c : neighbours) {
            if (c.getStatus() == 0) {
                break;
            }

            if (c.getStatus() < status) {
            	if(c.getFish() instanceof Sardinha) {
                    double w = c.getFish().getWeight();
                    eat(w);
                    c.setFish(null);
                    eaten += w;
                    if (eaten >= maxEat) break;
            	}
            }
        }
	}

}

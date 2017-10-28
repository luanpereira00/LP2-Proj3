package fishsimulator;

import java.util.List;


/**
 * Representa um predador do oceano
 * @author luanpereira00
 *
 */
public abstract class Predator extends Fish {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int huntDistance;
    protected double maxEat;

    /**
     * Construtor do predador
     * @param params Os parametros dele
     */
    public Predator(FishParams params) {
        super(params);
        huntDistance = params.getHuntDistance();
        maxEat = params.getMaxEat();
    }
    
    /**
     * @return A distância que pode caçar
     */
    public int getHuntDistance() {
    	return huntDistance;
    }
    
	/* (non-Javadoc)
	 * @see fishsimulator.Fish#breed(fishsimulator.Cell, java.util.List)
	 */
	@Override
    protected void breed(Cell currentCell, List<Cell> cells) {
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
}

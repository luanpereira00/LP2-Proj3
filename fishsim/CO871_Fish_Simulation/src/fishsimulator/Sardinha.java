package fishsimulator;
import java.util.List;

/**
 * Representa uma pequena sardinha no oceano
 * @author luanpereira00
 *
 */
public class Sardinha extends Fish {
	/**
	 * I've no idea
	 */
	private static final long serialVersionUID = 1L;
	private double planctonEaten;
	

    /**
     * Construtor de uma sardinha
     * @param params Os parametros da sardinha
     */
    public  Sardinha(FishParams params) {
        super(params);
        planctonEaten = params.getPlanctonEaten();
        status = 1;
    }
    

    /* (non-Javadoc)
     * @see fishsimulator.Fish#spawn(fishsimulator.Cell)
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
		weight *= weightReduce;
	}
    

    /**
     * Escolhe qual a melhor localização para mover-se
     * @param cell A célula atual
     * @param cells A lista de células vizinhas
     * @return A melhor célula para mover-se
     */
    private Cell moviment(Cell cell, List<Cell> cells) {
    	Cell bestNeighbour = null;
        for (Cell c: cells) {
			if (bestNeighbour == null && c.getFish()==null) {
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
		Cell bestNeighbour = null;
		bestNeighbour = moviment(currentCell, neighbours);
		if (bestNeighbour == null) return;
    	if (weight >= breedWeight && age > breedAge) {
			Fish child = spawn(bestNeighbour);
            child.setWeight(weight * 0.4);
			weight *= 0.6;
		} else {
			if (bestNeighbour.getPlancton() > currentCell.getPlancton()) move(currentCell, bestNeighbour);
		}
	}
}

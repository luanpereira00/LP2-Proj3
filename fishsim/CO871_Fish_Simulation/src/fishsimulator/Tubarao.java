package fishsimulator;

import java.util.List;

/**
 * Define um tubarão no oceano
 * @author luanpereira00
 *
 */
public class Tubarao extends Predator {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Construtor do tubarão
     * @param params Os parametros do tubarão
     */
    public Tubarao(FishParams params)
    {
        super(params);
        status = 3;
    }
    
    /* (non-Javadoc)
     * @see fishsimulator.Fish#spawn(fishsimulator.Cell)
     */
    public Fish spawn(Cell cell)
    {
        cell.createFish("tubarao", initialParams);
        return cell.getFish();
    }
    
    /* (non-Javadoc)
     * @see fishsimulator.Fish#eat(fishsimulator.Cell, java.util.List)
     */
    @Override
	protected void eat(Cell currentCell, List<Cell> neighbours) {
		double eaten = 0.0;
        for (Cell c : neighbours) {
            if(c!=null) {
            	if(c.getFish() instanceof Atum) {
            		double w = c.getFish().getWeight();
                    eat(w);
                    c.setFish(null);
                    eaten += w;
                    if (eaten >= maxEat)
                        return;
            	}
            }
        }
        int iter = 0;
        for (Cell c : neighbours) {
            if(c!=null) {
            	if(c.getFish() instanceof Sardinha) {
            		double w = c.getFish().getWeight();
                    eat(w);
                    c.setFish(null);
                    eaten += w;
                    iter++;
                    if (eaten >= maxEat || iter>3)
                        break;
            	}
            }
        }
	}
}

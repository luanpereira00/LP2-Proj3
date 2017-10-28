package fishsimulator;

import java.util.List;

/**
 * Um atum azul
 * @author jdb
 */
public class Atum extends Predator {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Cria um novo atum
     * @param params Os parametros do atum
     */
    public Atum(FishParams params)
    {
        super(params);
        status = 2;
    }

   
    /* (non-Javadoc)
     * @see fishsimulator.Fish#spawn(fishsimulator.Cell)
     */
    public Fish spawn(Cell cell)
    {
        cell.createFish("atum", initialParams);
        return cell.getFish();
    }
    
    /* (non-Javadoc)
     * @see fishsimulator.Fish#eat(fishsimulator.Cell, java.util.List)
     */
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

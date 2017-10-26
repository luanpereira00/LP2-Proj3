package fishsimulator;

import java.util.List;

public class Tubarao extends Predator {
        
        public Tubarao(FishParams params)
        {
            super(params);
            status = 3;
        }
        
        public Fish spawn(Cell cell)
        {
            cell.createFish("tubarao", initialParams);
            return cell.getFish();
        }
        
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
            	
            	/*if (c.getStatus() == 0) {
                    break;
                }

                if (c.getStatus() < status) {
                	if(!(c.getFish() instanceof Tubarao)) {
	                    double w = c.getFish().getWeight();
	                    eat(w);
	                    c.setFish(null);
	                    eaten += w;
	                    if (eaten >= maxEat)
	                        break;
                	}
                }*/
            //}
    	}
}

package fishsimulator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manage the rectangle of cells representing an ocean
 */
public class Ocean implements Serializable
{
    // Configurable parameters
    private double initialPlancton = 6.0;
    private double maxPlancton = 10.0;
    private double incPlancton = 0.25;
    
    FishParams sardinhaParams, atumParams, tubaraoParams;
    private int width, height;
    private ArrayList<Cell> cells;
    
    /**
     * Create a new ocean
     * @param height height in cells
     * @param width width in cells
     * @param sardinhaParams provides parameters for any herrings created
     * @param atumParams provides parameters for any gropers created
     * @param tubaraoParams provides parameters for any sharks created
     */
    public Ocean(int height, int width, FishParams sardinhaParams, FishParams atumParams, FishParams tubaraoParams) {
        this.width = width;
        this.height = height;
        this.sardinhaParams = sardinhaParams;
        this.atumParams = atumParams;
        this.tubaraoParams = tubaraoParams;
        cells = new ArrayList<Cell>(width * height);
        //System.out.println(cells.size());
        //fishes = new ArrayList<Fish>(width * height);
        //plancton = new double[width * height];
        
        for(int i=0; i<(width * height); i++) {
        	cells.add(i, new Cell(i/width, i%width, initialPlancton));
     
        }
        
       
    }
    
    /**
     * Return an array of cells in a rectangle surrounding this cell. Cells
     * are included if there row and collumn distance from here are both
     * less than or equal to r
     * @param r the maximum distance from here of cells returned.
     * @param empty if true only empty cessl are returned
     * @return array of neighbouring cells
     */
    public Cell[] neighbours(Cell cell, int r, boolean empty)
    {
    	int col = cell.getCol();
    	int row = cell.getRow();
    	int left = Math.max(0, col - r);
    	int right = Math.min(width, col + r + 1);
    	int top = Math.max(0, row - r);
    	int bottom = Math.min(height, row + r + 1);
    	Cell cellsArr[] = new Cell[(bottom - top)*(right - left) - 1];
    	//System.out.println(cells.length);   	
    	int n = 0;

    	for (int y = top; y < bottom; y++)
    		for (int x = left; x < right; x++) {
    			if (empty && getFishAt(y, x) != null) continue;
    			if (x != col || y != row) {
    				//el++;
    				cellsArr[n++] = getCellAt(y,x);//new Cell(y, x);
    			}
    			//if(el==cells.length) break;
    			
    		}
        if (n < cellsArr.length)
            return Arrays.copyOf(cellsArr, n);
        else
            return cellsArr;
    }
    
    
    /**
     * Fish creation factory
     * Create a new fish of the named type
     * @param cell
     * @param fishType string with the name of the kind of fish
     * @return created fish
     */
   /* public Fish createFish(Cell cell, String fishType)
    {
        if (fishType.equals("sardinha"))
            return new Sardinha(cell, sardinhaParams);
        if (fishType.equals("atum"))
            return new Atum(cell, atumParams);
        if (fishType.equals("tubarao"))
            return new Tubarao(cell, tubaraoParams);
        return null;
    }*/
    
    public void act(Cell cell, int step) {
    	Fish fish = cell.getFish();
    	if (fish.step == step) return;
		fish.step = step;
		fish.age++;
        if(fish instanceof Sardinha) { //--------------------------------------------------------------------------
			fish.eat(cell, Arrays.asList(neighbours(cell, 1, true)));                            
			if (!fish.isAlive()) {
				cell.setFish(null);
				return;
			}   
			fish.breed(cell, Arrays.asList(neighbours(cell, 1, true)));
        } 
        if(fish instanceof Atum) { //--------------------------------------------------------------------------
			fish.eat(cell, Arrays.asList(neighbours(cell, 2, false)));                             
			if (!fish.isAlive()) {
				cell.setFish(null);
				return;
			}   
			fish.breed(cell, Arrays.asList(neighbours(cell, 2, true)));
        } 
        if(fish instanceof Tubarao) { //--------------------------------------------------------------------------
			fish.eat(cell, Arrays.asList(neighbours(cell, 5, false)));                        
			if (!fish.isAlive()) {
				cell.setFish(null);
				return;
			}   
			fish.breed(cell, Arrays.asList(neighbours(cell, 5, false)));
        }
    }
    
    /**
     * Put the ocean through one iteraction of the simulator
     * @param step number of this iteration
     */
    public void act(int step) {
        /*
         * Seed the ocean with new fish occasionally
         */
    	if (step % 50 == 0) {
    		getCellAt(10,10).setFish(null);
    		getCellAt(10,10).createFish("sardinha", sardinhaParams);
    		getCellAt(height-20,width-10).setFish(null);
    		getCellAt(height-20,width-10).createFish("sardinha", sardinhaParams);
    	}
    	if (step % 100 == 50) {
    		getCellAt(20,20).setFish(null);
    		getCellAt(20,20).createFish("atum", atumParams);
    		getCellAt(30,60).setFish(null);
    		getCellAt(30,60).createFish("atum", atumParams);
    	}
    	if (step % 100 == 75) {
    		getCellAt(40,40).setFish(null);
    		getCellAt(40,40).createFish("tubarao", tubaraoParams);
    	}
   
    	for (Cell cl : cells) {
    		if (cl.getFish() != null) {			
    			act(cl, step);	
    		}else {
    			cl.plancInfluence(Arrays.asList(neighbours(cl, 1, false)), incPlancton, maxPlancton);
    		}
    		// Grow the plancton
        	
    	}
    	
    	/*
        Cell cells[] = Cells();
    	for (int n = 0; n < cells.length; n++)
    		if (cells[n].getFish() != null)
    			cells[n].getFish().act(step);
        // Grow the plancton
    	for (int n = 0; n < plancton.length; n++)
    		plancton[n] = Math.min(plancton[n] * incPlancton, maxPlancton);
    	 */
    	
    }
    
    /**
     * Get all the cells in the ocean
     * @return array of cells
     */
    //public ArrayList<Cell> Cells()
   // {
        /*Cell cells[] = new Cell[width * height];
        for (int n = 0; n < cells.length; n++)
            cells[n] = new Cell(n / width, n % width);
        return cells;*/
    	
    //	return cells;
   // }
    
    /**
     * Return the fish at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(int row, int col)
    {
        return getCellAt(row, col).getFish();
    }
    
    public Cell getCellAt(int row, int col)
    {
        return cells.get(width * row + col);
    }
    
    /**
     * Low-level method to add the fish to the ocean. Used by cells
     * @param fish added
     * @param row cell location
     * @param col cell location
     */
    public void setCellAt(Cell cell, int row, int col)
    {
        cells.set(width * row + col, cell);
    }
    
    /**
     * Low-level method to add the fish to the ocean. Used by cells
     * @param fish added
     * @param row cell location
     * @param col cell location
     */
    public void setFishAt(Fish fish, int row, int col)
    {
        getCellAt(row, col).setFish(fish);
    }
    
    /**
     * Get the plancton level
     * @param row location
     * @param col location
     * @return level
     */
    public double getPlanctonAt(int row, int col)
    {
    	return getCellAt(row, col).getPlancton();
    }
    
    /**
     * Mutator
     * @param p new plancton level
     * @param row location
     * @param col location
     */
    public void setPlanctonAt(double p, int row, int col)
    {
    	getCellAt(row, col).setPlancton(p);
    }
    
    /**
     * @return The height of the ocean.
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * @return The width of the ocean.
     */
    public int getWidth()
    {
        return width;
    }
}

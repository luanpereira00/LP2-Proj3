package fishsimulator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a single location in the ocean
 * @author jdb
 * @version 28/11/2008
 */
public class Cell implements Comparable<Cell>, Serializable
{
    private int row, col;
    //private Ocean ocean;
    private Fish fish;
    private double plancton;

    /**
     * Constructor for objects of class Cell
     * @param initialPlancton 
     */
    public Cell(int row, int col, double initialPlancton)
    {
        fish = null;
        plancton = initialPlancton;
    	//this.ocean = ocean;
        this.row = row;
        this.col = col;
    }
    
    /**
     * Compare the status of the fish in a pair of cells
     * Used when sorting neighbouring cells to decide which fish
     * to eac first
     * @param cell
     * @return
     */
    public int compareTo(Cell cell) {
        return cell.getStatus() - getStatus();
    }
    
    /**
     * Add a new fish to this cell. Any existing fish at this cell will
     * be destroyed
     * @param fishType
     * @return the new fish
     */
    public void createFish(String fishType, FishParams fishParams)
    {
		if(fish==null) {
    		if (fishType.equals("sardinha")) {
				fish =  new Sardinha(fishParams);
				return;
			}
	        if (fishType.equals("atum"))  {
	        	fish = new Atum(fishParams);
	        	return;
	        }
	        if (fishType.equals("tubarao")) {
	        	fish = new Tubarao(fishParams);	
	        	return;
	        }
		}
    }

    /**
     * Accessor
     * @return row of this cell
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Accessor
     * @return column of this cell
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Return all cells within an x and y distance r of here
     * @param rectangular distance
     * @return array of neighbouring cells
     */
   /* public Cell[] neighbours(int r)
    {
    	return neighbours(r, false);
    }*/
    
    
    
    /**
     * Get the status of any fish in the cell
     * @return fish status or 0 if the cell is unoccupied
     */
    public int getStatus()
    {
        Fish f = getFish();
        if (f != null)
            return f.getStatus();
        return 0;
    }
    
    /**
     * Get the fish at this cell
     * @return fish reference or null if the cell is unoccupied
     */
    public Fish getFish()
    {
        return fish;
    }
    
    /**
     * Add a new fish to the cell. Any existing occupant is discarded
     * @param fish the fish to add. Use null to empty the cell
     */
    public void setFish(Fish fish)
    {
       this.fish = fish;
    }
    
    /**
     * Get the plancton level at this location
     * @return plancton level
     */
    public double getPlancton()
    {
    	return plancton;
    }
    
    /**
     * Set the plancton level at this locatopn
     * @param p the new plancton level
     */
    public void setPlancton(double p)
    {
    	plancton = p;
    }
    
    /**
     * Return a random cell from the array of cells
     * @param cells
     * @return chosen cell
     */
    static Cell random(List<Cell> cells) {
        if (cells.size() == 0)
            return null;
        return cells.get((int)(cells.size() * Math.random()));
    }

	public void plancInfluence(List<Cell> asList, double incPlancton, double maxPlancton) {
		double value = 0;
		for(Cell c : asList) {
			value+=c.getPlancton();
		}
		plancton = Math.min((plancton*0.5 + value*0.5)*incPlancton, maxPlancton);
	}
}

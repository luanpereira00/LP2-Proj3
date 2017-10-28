package fishsimulator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representa um oceano
 * @author luanpereira00
 *
 */
public class Ocean implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Configurable parameters
    private double initialPlancton = 6.0;
    private double maxPlancton = 10.0;
    private double incPlancton = 0.25;
    
    FishParams sardinhaParams, atumParams, tubaraoParams;
    private int width, height;
    private ArrayList<Cell> cells;
    
    /**
     * Construtor para o oceano
     * @param height A quantidade de linhas do oceano
     * @param width A quantidade de colunas do oceano
     * @param sardinhaParams Os parâmetros para criar uma sardinha
     * @param atumParams Os parâmetros para criar um atum
     * @param tubaraoParams Os parâmetros para criar um tubarão
     */
    public Ocean(int height, int width, FishParams sardinhaParams, FishParams atumParams, FishParams tubaraoParams) {
        this.width = width;
        this.height = height;
        this.sardinhaParams = sardinhaParams;
        this.atumParams = atumParams;
        this.tubaraoParams = tubaraoParams;
        cells = new ArrayList<Cell>(width * height);
        
        for(int i=0; i<(width * height); i++) {
        	cells.add(i, new Cell(i/width, i%width, initialPlancton));
     
        } 
    }
    

    /**
     * Encontra e retorna os vizinhos num determinado raio
     * @param cell A célula que terá seus vizinhos encontrados
     * @param r O raio da vizinhança
     * @param empty Flag para verificar vizinhos vazios ou não
     * @return Retorna a vizinhança
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
    	int n = 0;

    	for (int y = top; y < bottom; y++)
    		for (int x = left; x < right; x++) {
    			if (empty && getFishAt(y, x) != null) continue;
    			if (x != col || y != row) {
    				cellsArr[n++] = getCellAt(y,x);
    			}
    		}
        if (n < cellsArr.length)
            return Arrays.copyOf(cellsArr, n);
        else
            return cellsArr;
    }
    
    /**
     * Invoca os atores dentro das células do oceano
     * @param cell A célula que terá os atores
     * @param step O passo atual da atuação
     */
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
     * Gerencia a atuação do oceano
     * @param step O passo atual da atuação
     */
    public void act(int step) {
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
    	}
    }
    
    /**
     * Retorna o peixe que está em determinada posição do oceano
     * @param row A linha que o peixe encontra-se
     * @param col A coluna que o peixe encontra-se
     * @return O peixe que está naquela posição
     */
    public Fish getFishAt(int row, int col)
    {
        return getCellAt(row, col).getFish();
    }
    
    /**
     * Retorna a célula que está em determinada posição do oceano
     * @param row A linha que a célula encontra-se
     * @param col A coluna que a célula encontra-se
     * @return A célula que está naquela posição
     */
    public Cell getCellAt(int row, int col)
    {
        return cells.get(width * row + col);
    }
    

    /** 
     * Atualiza a célula numa determinada posição
     * @param cell A nova célula
     * @param row A linha dela no oceano
     * @param col A coluna dela no oceano
     */
    public void setCellAt(Cell cell, int row, int col)
    {
        cells.set(width * row + col, cell);
    }
    
    /**
     * Atualiza o peixe numa determinada posição do oceano
     * @param fish O novo peixe
     * @param row A linha do oceano que o peixe encontra-se
     * @param col A coluna do oceano que o peixe encontra-se
     */
    public void setFishAt(Fish fish, int row, int col)
    {
        getCellAt(row, col).setFish(fish);
    }
    
    /**
     * Retorna a quantidade de planctons numa determinada posição
     * @param row A linha do oceano que os planctons estão
     * @param col A coluna do oceano que os planctons estão
     * @return A quantidade de planctons
     */
    public double getPlanctonAt(int row, int col)
    {
    	return getCellAt(row, col).getPlancton();
    }
    
    /**
     * Atualiza a quantidade de planctons numa determinada posição
     * @param p A nova quantidade de planctons
     * @param row A linha do oceano que os planctons estão
     * @param col A coluna do oceano que os planctons estão
     */
    public void setPlanctonAt(double p, int row, int col)
    {
    	getCellAt(row, col).setPlancton(p);
    }
    

    /**
     * @return Retorna a altura do oceano
     */
    public int getHeight()
    {
        return height;
    }
    
 
    /**
     * @return Retorna a largura do oceano
     */
    public int getWidth()
    {
        return width;
    }
}

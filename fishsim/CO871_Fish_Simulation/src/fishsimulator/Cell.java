package fishsimulator;
import java.io.Serializable;
import java.util.List;


/**
 * Uma pequena c�lula do oceano
 * @author luanpereira00
 *
 */
public class Cell implements Comparable<Cell>, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row, col;
    private Fish fish;
    private double plancton;


    /**
     * Construtor da c�lula
     * @param row A linha que encontra-se no oceano
     * @param col A coluna que encontra-se no oceano
     * @param initialPlancton A sua quantidade inicial de planctons
     */
    public Cell(int row, int col, double initialPlancton)
    {
        fish = null;
        plancton = initialPlancton;
    	//this.ocean = ocean;
        this.row = row;
        this.col = col;
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Cell cell) {
        return cell.getStatus() - getStatus();
    }
    
    
    /**
     * Cria um peixe nesta c�lula
     * @param fishType O tipo do peixe que deve ser criado
     * @param fishParams Os par�metros do peixe
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
     * @return A linha do oceano que encontra-se
     */
    public int getRow()
    {
        return row;
    }
    
    
    /**
     * @return A coluna do oceano que encontra-se
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Captura o estado do peixe desta c�lula
     * @return Retorna o estado do peixe
     */
    public int getStatus()
    {
        Fish f = getFish();
        if (f != null)
            return f.getStatus();
        return 0;
    }
    
    /**
     * Retorna o peixe da c�lula
     * @return O peixe
     */
    public Fish getFish()
    {
        return fish;
    }
    
    /**
     * Atualiza o peixe nesta c�lula
     * @param fish O novo peixe
     */
    public void setFish(Fish fish)
    {
       this.fish = fish;
    }
    
    /**
     * Captura a quantidade de planctons associada a esta c�lula
     * @return Os planctons
     */
    public double getPlancton()
    {
    	return plancton;
    }
    
    /**
     * Atualiza a quantidade de planctons
     * @param p A nova quantidade
     */
    public void setPlancton(double p)
    {
    	plancton = p;
    }
    
    /**
     * Retorna uma c�lula rand�mica da lista de c�lulas
     * @param cells A lista de c�lulas
     * @return A c�lula escolhida
     */
    static Cell random(List<Cell> cells) {
        if (cells.size() == 0)
            return null;
        return cells.get((int)(cells.size() * Math.random()));
    }

	/**
	 * Calcula a influ�ncia dos planctons de acordo com as c�lulas ao redor
	 * @param asList A lista de c�lulas vizinhas
	 * @param incPlancton Um par�metro de atualiza��o
	 * @param maxPlancton A quantidade m�xima de planctons
	 */
	public void plancInfluence(List<Cell> asList, double incPlancton, double maxPlancton) {
		double value = 0;
		for(Cell c : asList) {
			value+=c.getPlancton();
		}
		plancton = Math.min((plancton*0.5 + value*0.5)*incPlancton, maxPlancton);
	}
}

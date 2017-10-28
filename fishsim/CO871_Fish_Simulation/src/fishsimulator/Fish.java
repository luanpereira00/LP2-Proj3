package fishsimulator;

import java.io.Serializable;
import java.util.List;


/**
 * A superclasse de qualquer tipo de peixe
 * @author luanpereira00
 *
 */
public abstract class Fish implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double weight;
	protected double viableWeight;
	protected double weightReduce;
    protected double breedWeight;
    protected int breedAge;
    protected int maxAge;
    protected int status;
	protected int age = 0;
	protected int step = -1;
	protected final FishParams initialParams;
	
  
    /**
     * Construtor de um peixe
     * @param params Os parametros do peixe
     */
    public Fish(FishParams params)
    {
    	initialParams = params;
        weight = params.getInitWeight();
        viableWeight = params.getViableWeight();
        weightReduce = params.getWeightReduce();
        breedWeight = params.getBreedWeight();
        breedAge = params.getBreedAge();
        maxAge = params.getMaxAge();
		age = 0;
	}
        
    /**
     * Cria um peixe do mesmo tipo numa célula passada
     * @param cell A célula do novo peixe
     * @return O peixe criado
     */
    abstract Fish spawn(Cell cell);
    
    /**
     * @return Retorna booleano para o peixe estar vivo ou não
     */
    public boolean isAlive() {
    	return !(weight < viableWeight || age > maxAge);
    	
    }    
        
	/**
	 * Alimenta-se aumentando seu peso de acordo com uma quantidade de alimento passada
	 * @param w A quantidade de alimento
	 */
	public void eat(double w)
	{
		weight += w;
	}
	
    /**
     * @return Retorna o estado do peixe
     */
    public int getStatus() {
        return status;
    }
        
	/**
	 * @return Retorna o peso do peixe
	 */
	public double getWeight()
	{
		return weight;
	}
	
	/**
	 * Atualiza o peso do peixe
	 * @param weight O novo peso
	 */
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	/**
	 * Movimenta o peixe e atualiza as células envolvidas
	 * @param currentCell A célula que o peixe está atualmente
	 * @param nextCell A célula que ele irá
	 */
	public void move(Cell currentCell, Cell nextCell)
	{
		nextCell.setFish(this);
		currentCell.setFish(null);
	}
	
	/**
	 * Ação para alimentação do peixe
	 * @param currentCell A célula atual
	 * @param neighbours A lista de vizinhos
	 */
	protected abstract void eat(Cell currentCell, List<Cell> neighbours);
	
	/**
	 * Ação para a reprodução dos peixes
	 * @param currentCell A célula atual
	 * @param asList A lista de vizinhos
	 */
	protected abstract void breed(Cell currentCell, List<Cell> asList);
}

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
     * Cria um peixe do mesmo tipo numa c�lula passada
     * @param cell A c�lula do novo peixe
     * @return O peixe criado
     */
    abstract Fish spawn(Cell cell);
    
    /**
     * @return Retorna booleano para o peixe estar vivo ou n�o
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
	 * Movimenta o peixe e atualiza as c�lulas envolvidas
	 * @param currentCell A c�lula que o peixe est� atualmente
	 * @param nextCell A c�lula que ele ir�
	 */
	public void move(Cell currentCell, Cell nextCell)
	{
		nextCell.setFish(this);
		currentCell.setFish(null);
	}
	
	/**
	 * A��o para alimenta��o do peixe
	 * @param currentCell A c�lula atual
	 * @param neighbours A lista de vizinhos
	 */
	protected abstract void eat(Cell currentCell, List<Cell> neighbours);
	
	/**
	 * A��o para a reprodu��o dos peixes
	 * @param currentCell A c�lula atual
	 * @param asList A lista de vizinhos
	 */
	protected abstract void breed(Cell currentCell, List<Cell> asList);
}

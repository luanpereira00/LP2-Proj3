package fishsimulator;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * A classe que define o simulador e seus atores
 * @author luanpereira00
 *
 */
public class Simulator implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ocean ocean;
    private SimulatorView simView; 
    private SFPs sardinhaParams;
    private SFPs atumParams;
    private SFPs tubaraoParams;
    int height;
    int width;
    
    /**
     * Método principal de início da simualação
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException 
    {
        Simulator sim = new Simulator(100, 100);
        
        sim.run(10000);
        
    }
    

    /**
     * Construtor padrão do simulador
     * @param height A altura do oceano simulado
     * @param width A largura do oceano simulado
     */
    public Simulator(int height, int width)  {
        this.height=height;
        this.width=width;
    	
        sardinhaParams = new SFPs();
        sardinhaParams.initParams(4.0, 1.0, 0.8, 2.0, 6.0, 3, 30, 0, 0.0);
        atumParams = new SFPs();
        atumParams.initParams(8.0, 1.0, 0.8, 0.0, 16.0, 3, 30, 1, 8.0);
        tubaraoParams = new SFPs();
        tubaraoParams.initParams(16.0, 1.0, 0.8, 0.0, 32.0, 3, 30, 4, 12.0);
        
        ocean = new Ocean(height, width, sardinhaParams, atumParams, tubaraoParams);
        simView = new SimulatorView(height, width);
        
        simView.setColor(Sardinha.class, new Color(160, 45, 41));
        simView.setColor(Atum.class, new Color(10, 11, 243));
        simView.setColor(Tubarao.class, Color.black);
    }
    

    /**
     * Roda a simulação para todos os atores
     * @param steps Número atual de passos da simulação
     * @throws InterruptedException
     */
    public void run(int steps) throws InterruptedException
    {
    	while(!simView.iniciarFlag()) {
    		Thread.sleep(50);
    	}
    	simView.setDownIniciarFlag();

        for (int i = 0; i < steps; i++)
        {
    		while(simView.getWait()) {
    			Thread.sleep(50);
    		}
    		if(simView.reiniciarFlag()) {
    			Thread.sleep(50);	
    			ocean = new Ocean(height, width, sardinhaParams, atumParams, tubaraoParams);
    			simView.resetReiniciarFlag();	
    			i=0;
    		}
    		if(simView.serializeFlag()) {
    			Thread.sleep(50);	
    			System.out.print("Serializando... ");
    			try{
    				
    				FileOutputStream fout = new FileOutputStream("./serialized/ocean.ser");
    				ObjectOutputStream oos = new ObjectOutputStream(fout);   
    				oos.writeObject(ocean);
    				oos.close();
    				
    				FileOutputStream fSimOut = new FileOutputStream("./serialized/iterator.ser");
    				ObjectOutputStream oosIter = new ObjectOutputStream(fSimOut);   
    				oosIter.writeObject(i);
    				oosIter.close();
    				
    				System.out.println("Pronto!");
    			}
    			catch(Exception ex){
				   ex.printStackTrace();
    			} 
    			simView.setDownSerializeFlag();	
    		}
    		
    		if(simView.recoveryFlag()) {
    			Thread.sleep(50);	
    			System.out.print("Desserializando... ");
    			try{
    				FileInputStream fin = new FileInputStream("./serialized/ocean.ser");
    				ObjectInputStream ois = new ObjectInputStream(fin );   
    				ocean = (Ocean) ois.readObject();
    				ois.close();
    				
    				FileInputStream finIter = new FileInputStream("./serialized/iterator.ser");
    				ObjectInputStream oisIter = new ObjectInputStream(finIter);   
    				i = (int) oisIter.readObject();
    				oisIter.close();
    				
    				System.out.println("Pronto!");
    			}
    			catch(Exception ex){
				   ex.printStackTrace();
    			} 
    			
    			simView.setDownRecoveryFlag();	
    		}
    		
    		if(simView.sardinhaAttFlag()) {
    			sardinhaParams.initParams(simView.getSardinhaInfos()[0], 1.0, 0.8, 2.0, 6.0, (int) simView.getSardinhaInfos()[1], (int) simView.getSardinhaInfos()[2], 0, 0.0);
    			simView.resetSardinhaFlag();
    		}
    		
    		if(simView.atumAttFlag()) {
    			atumParams.initParams(simView.getAtumInfos()[0], 1.0, 0.8, 0.0, 16.0, (int) simView.getAtumInfos()[1], (int) simView.getAtumInfos()[2], 1, 8.0);
    			simView.resetAtumFlag();
    		}
    		
    		if(simView.tubaraoAttFlag()) {
    			tubaraoParams.initParams(simView.getTubaraoInfos()[0], 1.0, 0.8, 0.0, 32.0, (int) simView.getTubaraoInfos()[1], (int) simView.getTubaraoInfos()[2], 4, 12.0);
    			simView.resetTubaraoFlag();
    		}
    		
        	ocean.act(i);
        	simView.showStatus(i, ocean);
        	
        	Thread.sleep(100-simView.getVelocity());
        }
        simView.finalizeAtuacao();
    }
}

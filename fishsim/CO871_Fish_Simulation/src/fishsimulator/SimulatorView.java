package fishsimulator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.AncestorEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003.12.22
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;
    private static final Color Plancton_Full_COLOR = new Color(85, 150, 220);

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private OceanView oceanView;
    
    // A map for storing colors for participants in the simulation
    private HashMap<Class,Color> colors;
    // A statistics object computing and storing simulation information
    private OceanStats stats;
    
    private boolean wait;
    private boolean reiniciar;
    private boolean iniciar;
    private boolean serialize;
    private boolean recovery;
    
    private boolean sardinhaFlag;
    private final double sardinhaInfosOriginal[] = {4.0, 3, 30};
    private double sardinhaInfos[];
    
    private boolean atumFlag;
    private final double atumInfosOriginal[] = {8.0, 3, 30};  
    private double atumInfos[];
    
    private boolean tubaraoFlag;
    private final double tubaraoInfosOriginal[] = {16.0, 3, 30};  
    private double tubaraoInfos[];
    
    private int velocidade;
    private final Action action = new SwingAction();
    
    public void finalizeAtuacao(){
    	population.setText("Simulação finalizada!");
    	
    	setVisible(false);
    }
    
    public double[] getSardinhaInfos() {
    	return sardinhaInfos;
    }
    
    public double[] getAtumInfos() {
    	return atumInfos;
    }
    
    public double[] getTubaraoInfos() {
    	return tubaraoInfos;
    }
    
    public int getVelocity() {
    	return velocidade;
    }
    
    public boolean getWait() {
    	return wait;
    }
    
    public boolean reiniciarFlag() {
		// TODO Auto-generated method stub
		return reiniciar;
	}
    
    public void resetReiniciarFlag() {
		// TODO Auto-generated method stub
		reiniciar = false;
	}
    
    public void setUpIniciarFlag() {
		// TODO Auto-generated method stub
		iniciar = true;
	}
    
    public boolean iniciarFlag() {
    	return iniciar;
    }
    
    public void setDownIniciarFlag() {
		iniciar = false;
	}
    
    public boolean serializeFlag() {
    	return serialize;
    }
    
    public void setDownSerializeFlag() {
		serialize = false;
	}
    
    public boolean recoveryFlag() {
    	return recovery;
    }
    
    public void setDownRecoveryFlag() {
		recovery = false;
	}
    
    public boolean sardinhaAttFlag() {
		// TODO Auto-generated method stub
		return sardinhaFlag;
	}
    
    public void resetSardinhaFlag() {
		// TODO Auto-generated method stub
		sardinhaFlag = false;
	}
    
    public boolean atumAttFlag() {
		// TODO Auto-generated method stub
		return atumFlag;
	}
    
    public void resetAtumFlag() {
		// TODO Auto-generated method stub
		atumFlag = false;
	}
    
    public boolean tubaraoAttFlag() {
		// TODO Auto-generated method stub
		return tubaraoFlag;
	}
    
    public void resetTubaraoFlag() {
		// TODO Auto-generated method stub
		tubaraoFlag = false;
	}

    /**
     * Create a view of the given width and height.
     * @param height The simulation height.
     * @param width The simulation width.
     */
    public SimulatorView(int height, int width)
    {
        stats = new OceanStats();
        colors = new HashMap<Class,Color>();
        wait=false;
        reiniciar=false;
        iniciar=false;
        serialize=false;
        recovery=false;
        
        sardinhaFlag=false;
        sardinhaInfos = new double[3];
        
        sardinhaInfos[0]=sardinhaInfosOriginal[0];
		sardinhaInfos[1]=sardinhaInfosOriginal[1];
		sardinhaInfos[2]=sardinhaInfosOriginal[2];
        
        atumFlag=false;
        atumInfos = new double[3];
        
        atumInfos[0]=atumInfosOriginal[0];
		atumInfos[1]=atumInfosOriginal[1];
		atumInfos[2]=atumInfosOriginal[2];
        
		tubaraoFlag=false;
        tubaraoInfos = new double[3];
        
        tubaraoInfos[0]=tubaraoInfosOriginal[0];
		tubaraoInfos[1]=tubaraoInfosOriginal[1];
		tubaraoInfos[2]=tubaraoInfosOriginal[2];
		
        velocidade=0;
        
        setTitle("SimOcean");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        
        setLocation(100, 50);
        
        oceanView = new OceanView(height, width);

        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(oceanView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        constructMenu(contents);
        pack();
    }
    
    private void constructMenu(Container contents) {
    	JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu controlMenu = new JMenu("Control");
        menuBar.add(controlMenu);
        controlMenu.setHorizontalAlignment(SwingConstants.CENTER);
        Box horizontalBoxMenu = Box.createHorizontalBox();
        controlMenu.add(horizontalBoxMenu);
      
        JButton buttonIniciar = new JButton("Iniciar");
        buttonIniciar.setToolTipText("Inicia a simulação");
        
        horizontalBoxMenu.add(buttonIniciar);
        buttonIniciar.setHorizontalAlignment(SwingConstants.LEADING);
        
        JButton botaoReiniciar = new JButton("Reiniciar");
        botaoReiniciar.setToolTipText("Reinicia uma simula\u00E7\u00E3o do zero");
        botaoReiniciar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		reiniciar = true;
        	}
        });
        horizontalBoxMenu.add(botaoReiniciar);
        botaoReiniciar.setHorizontalAlignment(SwingConstants.LEADING);
       
    	botaoReiniciar.setEnabled(false);
        
        JToggleButton resumirPausarToggleButton = new JToggleButton("Pausar");
        resumirPausarToggleButton.setEnabled(false);
        resumirPausarToggleButton.setToolTipText("Pausa ou resume a simulação atual");
        resumirPausarToggleButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		wait = !wait;
        		
        		if(resumirPausarToggleButton.isSelected()) { 
	        		Thread t = new Thread(new Runnable() {
	        	        @Override
	        	        public void run() {
	        	        	resumirPausarToggleButton.setText("Resumir");
	        	        }     
	        	    });
	        	    t.start();
        		} 
        		else {
	        		Thread t = new Thread(new Runnable() {
	        	        @Override
	        	        public void run() {
	        	        	resumirPausarToggleButton.setText("Pausar");
	        	        }     
	        	    });
	        	    t.start();
        		}
        	}
        });
        horizontalBoxMenu.add(resumirPausarToggleButton);
        
        JButton buttonSerialize = new JButton("Salvar");
        buttonSerialize.setEnabled(false);
        buttonSerialize.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		serialize = !serialize;
        	}
        });
        buttonSerialize.setToolTipText("Salva o oceano a partir deste ponto");
        buttonSerialize.setHorizontalAlignment(SwingConstants.LEADING);
        horizontalBoxMenu.add(buttonSerialize);
        
        
        buttonIniciar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setUpIniciarFlag();
        		Thread t = new Thread(new Runnable() {
        	        @Override
        	        public void run() {
        	        	buttonIniciar.setEnabled(false);
        	        	resumirPausarToggleButton.setEnabled(true);
        	        	botaoReiniciar.setEnabled(true);
        	        	buttonSerialize.setEnabled(true);
        	        }     
        	    });
        	    t.start();
        	}
        });
        
        
        JButton buttonRecovery = new JButton("Recuperar");
        buttonRecovery.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		recovery = !recovery;
        	}
        });
        buttonRecovery.setToolTipText("Recupera o \u00FAltimo ponto salvo");
        buttonRecovery.setHorizontalAlignment(SwingConstants.LEADING);
        horizontalBoxMenu.add(buttonRecovery);
        
        Box horizontalBoxMenu_2 = Box.createHorizontalBox();
        controlMenu.add(horizontalBoxMenu_2);
        
        JLabel labelVelocidadeMenu = new JLabel("Velocidade");
        horizontalBoxMenu_2.add(labelVelocidadeMenu);
        
        JSlider sliderVelocidadeMenu = new JSlider();
        sliderVelocidadeMenu.setMinorTickSpacing(25);
        sliderVelocidadeMenu.setValue(0);
        sliderVelocidadeMenu.setMinimum(-100);
        sliderVelocidadeMenu.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	  velocidade = sliderVelocidadeMenu.getValue();
              }
            });
        sliderVelocidadeMenu.setMajorTickSpacing(100);
        sliderVelocidadeMenu.setPaintTicks(true);
        horizontalBoxMenu_2.add(sliderVelocidadeMenu);
        resumirPausarToggleButton.isSelected();
        
        constructSardinhaMenu(menuBar);
        
        constructAtumMenu(menuBar);
        
        constructTubaraoMenu(menuBar);
	}

	private void constructTubaraoMenu(JMenuBar menuBar) {
		// TODO Auto-generated method stub
		JMenu tubaraoMenu = new JMenu("Tubar\u00E3o");
        tubaraoMenu.setHorizontalAlignment(SwingConstants.LEFT);
        menuBar.add(tubaraoMenu);
        
        Box horizontalBoxTubarao = Box.createHorizontalBox();
        tubaraoMenu.add(horizontalBoxTubarao);
        
        JLabel labelPesoInicialTubarao = new JLabel("Peso inicial:");
        labelPesoInicialTubarao.setAlignmentY(0.0f);
        horizontalBoxTubarao.add(labelPesoInicialTubarao);
        
        JSlider sliderPesoInicialTubarao = new JSlider();
        
        sliderPesoInicialTubarao.setPaintTicks(true);
        sliderPesoInicialTubarao.setMinimum(1);
        sliderPesoInicialTubarao.setMinorTickSpacing(2);
        sliderPesoInicialTubarao.setValue((int) tubaraoInfosOriginal[0]);
        sliderPesoInicialTubarao.setMaximum(70);
        sliderPesoInicialTubarao.setPaintTicks(true);
        sliderPesoInicialTubarao.setPaintLabels(true);
        sliderPesoInicialTubarao.setMajorTickSpacing(69);
        horizontalBoxTubarao.add(sliderPesoInicialTubarao);
        
        Component verticalGlueTubarao1 = Box.createVerticalGlue();
        tubaraoMenu.add(verticalGlueTubarao1);
        
        Box horizontalBoxTubarao1 = Box.createHorizontalBox();
        tubaraoMenu.add(horizontalBoxTubarao1);
        
        JLabel labelIdadeReprodutoraTubarao = new JLabel("Idade reprodutora inicial:");
        labelIdadeReprodutoraTubarao.setHorizontalAlignment(SwingConstants.CENTER);
        labelIdadeReprodutoraTubarao.setAlignmentY(0.0f);
        horizontalBoxTubarao1.add(labelIdadeReprodutoraTubarao);
        
        JSlider sliderIdadeReprodutoraTubarao = new JSlider();
       
        sliderIdadeReprodutoraTubarao.setMinorTickSpacing(1);
        sliderIdadeReprodutoraTubarao.setMinimum(1);
        sliderIdadeReprodutoraTubarao.setMaximum(40);
        sliderIdadeReprodutoraTubarao.setValue((int) tubaraoInfosOriginal[1]);
        sliderIdadeReprodutoraTubarao.setToolTipText("");
        sliderIdadeReprodutoraTubarao.setPaintTicks(true);
        sliderIdadeReprodutoraTubarao.setPaintLabels(true);
        sliderIdadeReprodutoraTubarao.setMajorTickSpacing(39);
        
        horizontalBoxTubarao1.add(sliderIdadeReprodutoraTubarao);
        
        Component verticalGlueTubarao2 = Box.createVerticalGlue();
        tubaraoMenu.add(verticalGlueTubarao2);
        
        Box horizontalBoxTubarao2 = Box.createHorizontalBox();
        tubaraoMenu.add(horizontalBoxTubarao2);
        
        JLabel labelIdadeMaximaTubarao = new JLabel("Idade m\u00E1xima:");
        labelIdadeMaximaTubarao.setAlignmentY(0.0f);
        horizontalBoxTubarao2.add(labelIdadeMaximaTubarao);
        
        JSlider sliderIdadeMaximaTubarao = new JSlider();
       
        sliderIdadeMaximaTubarao.setValue((int) tubaraoInfosOriginal[2]);
        sliderIdadeMaximaTubarao.setMinorTickSpacing(2);
        sliderIdadeMaximaTubarao.setMinimum(1);
        sliderIdadeMaximaTubarao.setMaximum(50);
        sliderIdadeMaximaTubarao.setPaintTicks(true);
        sliderIdadeMaximaTubarao.setPaintLabels(true);
        sliderIdadeMaximaTubarao.setMajorTickSpacing(49);
        
        horizontalBoxTubarao2.add(sliderIdadeMaximaTubarao);
        
        Component verticalGlueTubarao3 = Box.createVerticalGlue();
        tubaraoMenu.add(verticalGlueTubarao3);
        
        Box horizontalBoxTubarao3 = Box.createHorizontalBox();
        tubaraoMenu.add(horizontalBoxTubarao3);
        
        JButton buttonAtualizarTubarao = new JButton("Atualizar");
        buttonAtualizarTubarao.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
	    		tubaraoFlag = !tubaraoFlag;
	    		tubaraoInfos[0]=sliderPesoInicialTubarao.getValue();
	    		tubaraoInfos[1]=sliderIdadeReprodutoraTubarao.getValue();
	    		tubaraoInfos[2]=sliderIdadeMaximaTubarao.getValue();		
        	}
        });
        buttonAtualizarTubarao.setAlignmentY(0.0f);
        horizontalBoxTubarao3.add(buttonAtualizarTubarao);
        
        JButton buttonResetarTubarao = new JButton("Resetar");
        buttonResetarTubarao.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		tubaraoFlag = !tubaraoFlag;
        		tubaraoInfos[0]=tubaraoInfosOriginal[0];
        		tubaraoInfos[1]=tubaraoInfosOriginal[1];
        		tubaraoInfos[2]=tubaraoInfosOriginal[2];
        		sliderPesoInicialTubarao.setValue((int) tubaraoInfosOriginal[0]);
        		sliderIdadeReprodutoraTubarao.setValue((int) tubaraoInfosOriginal[1]);
        		sliderIdadeMaximaTubarao.setValue((int) tubaraoInfosOriginal[2]);
        	}
        });
        buttonResetarTubarao.setAlignmentY(0.0f);
        horizontalBoxTubarao3.add(buttonResetarTubarao);
        setVisible(true);
	}
	

	private void constructAtumMenu(JMenuBar menuBar) {
		// TODO Auto-generated method stub
		JMenu atumMenu = new JMenu("Atum");
        atumMenu.setHorizontalAlignment(SwingConstants.LEFT);
        menuBar.add(atumMenu);
        
        Box horizontalBoxAtumMenu = Box.createHorizontalBox();
        atumMenu.add(horizontalBoxAtumMenu);
        
        JLabel labelPesoInicialAtum = new JLabel("Peso inicial:");
        labelPesoInicialAtum.setAlignmentY(0.0f);
        horizontalBoxAtumMenu.add(labelPesoInicialAtum);
        
        JSlider sliderPesoInicialAtum = new JSlider();
        sliderPesoInicialAtum.setPaintTicks(true);
        sliderPesoInicialAtum.setMinimum(1);
        sliderPesoInicialAtum.setMinorTickSpacing(2);
        sliderPesoInicialAtum.setValue((int) atumInfosOriginal[0]);
        sliderPesoInicialAtum.setMaximum(40);
        sliderPesoInicialAtum.setPaintTicks(true);
        sliderPesoInicialAtum.setPaintLabels(true);
        sliderPesoInicialAtum.setMajorTickSpacing(39);
        horizontalBoxAtumMenu.add(sliderPesoInicialAtum);
        
        Component verticalGlueAtum = Box.createVerticalGlue();
        atumMenu.add(verticalGlueAtum);
        
        Box horizontalBoxAtum2 = Box.createHorizontalBox();
        atumMenu.add(horizontalBoxAtum2);
        
        JLabel labelIdadeReprodutoraAtum = new JLabel("Idade reprodutora inicial:");
        labelIdadeReprodutoraAtum.setHorizontalAlignment(SwingConstants.CENTER);
        labelIdadeReprodutoraAtum.setAlignmentY(0.0f);
        horizontalBoxAtum2.add(labelIdadeReprodutoraAtum);
        
        JSlider sliderIdadeReprodutoraAtum = new JSlider();
        sliderIdadeReprodutoraAtum.setMinorTickSpacing(1);
        sliderIdadeReprodutoraAtum.setMinimum(1);
        sliderIdadeReprodutoraAtum.setMaximum(30);
        sliderIdadeReprodutoraAtum.setValue((int) atumInfosOriginal[1]);
        sliderIdadeReprodutoraAtum.setToolTipText("");
        sliderIdadeReprodutoraAtum.setPaintTicks(true);
        sliderIdadeReprodutoraAtum.setPaintLabels(true);
        sliderIdadeReprodutoraAtum.setMajorTickSpacing(29);
        horizontalBoxAtum2.add(sliderIdadeReprodutoraAtum);
        
        Component verticalGlueAtum2 = Box.createVerticalGlue();
        atumMenu.add(verticalGlueAtum2);
        
        Box horizontalBoxAtum3 = Box.createHorizontalBox();
        atumMenu.add(horizontalBoxAtum3);
        
        JLabel labelIdadeMaximaAtum = new JLabel("Idade m\u00E1xima:");
        labelIdadeMaximaAtum.setAlignmentY(0.0f);
        horizontalBoxAtum3.add(labelIdadeMaximaAtum);
        
        JSlider sliderIdadeMaximaAtum = new JSlider();
        sliderIdadeMaximaAtum.setValue((int) atumInfosOriginal[2]);
        sliderIdadeMaximaAtum.setMinorTickSpacing(2);
        sliderIdadeMaximaAtum.setMinimum(1);
        sliderIdadeMaximaAtum.setMaximum(40);
        sliderIdadeMaximaAtum.setPaintTicks(true);
        sliderIdadeMaximaAtum.setPaintLabels(true);
        sliderIdadeMaximaAtum.setMajorTickSpacing(39);
        
        horizontalBoxAtum3.add(sliderIdadeMaximaAtum);
        
        Component verticalGlueAtum3 = Box.createVerticalGlue();
        atumMenu.add(verticalGlueAtum3);
        
        Box horizontalBoxAtum4 = Box.createHorizontalBox();
        atumMenu.add(horizontalBoxAtum4);
        
        JButton buttonAtualizarAtum = new JButton("Atualizar");
        buttonAtualizarAtum.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
	    		atumFlag = !atumFlag;
	    		atumInfos[0]=sliderPesoInicialAtum.getValue();
	    		atumInfos[1]=sliderIdadeReprodutoraAtum.getValue();
	    		atumInfos[2]=sliderIdadeMaximaAtum.getValue();		
        	}
        });
        buttonAtualizarAtum.setAlignmentY(0.0f);
        horizontalBoxAtum4.add(buttonAtualizarAtum);
        
        JButton buttonResetarAtum = new JButton("Resetar");
        buttonResetarAtum.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		atumFlag = !atumFlag;
        		atumInfos[0]=atumInfosOriginal[0];
        		atumInfos[1]=atumInfosOriginal[1];
        		atumInfos[2]=atumInfosOriginal[2];
        		sliderPesoInicialAtum.setValue((int) atumInfosOriginal[0]);
        		sliderIdadeReprodutoraAtum.setValue((int) atumInfosOriginal[1]);
        		sliderIdadeMaximaAtum.setValue((int) atumInfosOriginal[2]);
        	}
        });
        buttonResetarAtum.setAlignmentY(0.0f);
        horizontalBoxAtum4.add(buttonResetarAtum);
	}
	

	private void constructSardinhaMenu(JMenuBar menuBar) {
		// TODO Auto-generated method stub
		JMenu sardinhaMenu = new JMenu("Sardinha");
        sardinhaMenu.setHorizontalAlignment(SwingConstants.LEFT);
        menuBar.add(sardinhaMenu);
        
        Box pesoInicialBoxSardinha = Box.createHorizontalBox();
        sardinhaMenu.add(pesoInicialBoxSardinha);      
        
        JLabel pesoInicialSardinhaLabel = new JLabel("Peso inicial:");
        pesoInicialSardinhaLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        pesoInicialBoxSardinha.add(pesoInicialSardinhaLabel);
        
        JSlider pesoInicialSliderSardinha = new JSlider();
        pesoInicialSliderSardinha.setMinimum(1);
        pesoInicialSliderSardinha.setMinorTickSpacing(1);
        pesoInicialSliderSardinha.setValue((int) sardinhaInfos[0]);
        pesoInicialSliderSardinha.setMaximum(10);
        pesoInicialSliderSardinha.setPaintLabels(true);
        pesoInicialSliderSardinha.setMajorTickSpacing(9);
        pesoInicialSliderSardinha.setPaintTicks(true);
        pesoInicialBoxSardinha.add(pesoInicialSliderSardinha);
        
        Component verticalGlueSardinha = Box.createVerticalGlue();
        sardinhaMenu.add(verticalGlueSardinha);
        
        Box idadeReproducaoBox = Box.createHorizontalBox();
        sardinhaMenu.add(idadeReproducaoBox);
        
        JLabel idadeReproducaoInicialSardinhaLabel = new JLabel("Idade reprodutora inicial:");
        idadeReproducaoInicialSardinhaLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        idadeReproducaoInicialSardinhaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idadeReproducaoBox.add(idadeReproducaoInicialSardinhaLabel);
        
        JSlider idadeReproducaoInicialSliderSardinha = new JSlider();
        idadeReproducaoInicialSliderSardinha.setMinorTickSpacing(1);
        idadeReproducaoInicialSliderSardinha.setMinimum(1);
        idadeReproducaoInicialSliderSardinha.setMaximum(30);
        idadeReproducaoInicialSliderSardinha.setValue((int) sardinhaInfos[1]);
        idadeReproducaoInicialSliderSardinha.setToolTipText("");
        idadeReproducaoInicialSliderSardinha.setPaintTicks(true);
        idadeReproducaoInicialSliderSardinha.setPaintLabels(true);
        idadeReproducaoInicialSliderSardinha.setMajorTickSpacing(29);
        idadeReproducaoBox.add(idadeReproducaoInicialSliderSardinha);
        
        Component verticalGlueSardinha2 = Box.createVerticalGlue();
        sardinhaMenu.add(verticalGlueSardinha2);
        
        Box idadeMaximaBoxSardinha = Box.createHorizontalBox();
        sardinhaMenu.add(idadeMaximaBoxSardinha);
        
        JLabel idadeMaximaLabelSardinha = new JLabel("Idade m\u00E1xima:");
        idadeMaximaLabelSardinha.setAlignmentY(Component.TOP_ALIGNMENT);
        idadeMaximaBoxSardinha.add(idadeMaximaLabelSardinha);
        
        JSlider idadeMaximaSliderSardinha = new JSlider();
        idadeMaximaSliderSardinha.setValue((int) sardinhaInfos[2]);
        idadeMaximaSliderSardinha.setMinorTickSpacing(2);
        idadeMaximaSliderSardinha.setMinimum(1);
        idadeMaximaSliderSardinha.setMaximum(40);
        idadeMaximaSliderSardinha.setPaintTicks(true);
        idadeMaximaSliderSardinha.setPaintLabels(true);
        idadeMaximaSliderSardinha.setMajorTickSpacing(39);
        idadeMaximaBoxSardinha.add(idadeMaximaSliderSardinha);
        
        Component verticalGlueSardinha3 = Box.createVerticalGlue();
        sardinhaMenu.add(verticalGlueSardinha3);
        
        Box botoesBoxSardinha = Box.createHorizontalBox();
        sardinhaMenu.add(botoesBoxSardinha);
        
        JButton botaoAtualizarBoxSardinha = new JButton("Atualizar");
        botaoAtualizarBoxSardinha.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		sardinhaFlag = !sardinhaFlag;
        		sardinhaInfos[0]=pesoInicialSliderSardinha.getValue();
        		sardinhaInfos[1]=idadeReproducaoInicialSliderSardinha.getValue();
        		sardinhaInfos[2]=idadeMaximaSliderSardinha.getValue();		
        	}
        });
        botaoAtualizarBoxSardinha.setAlignmentY(Component.TOP_ALIGNMENT);
        botoesBoxSardinha.add(botaoAtualizarBoxSardinha);
        
        JButton botaoResetarBoxSardinha = new JButton("Resetar");
        botaoResetarBoxSardinha.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		sardinhaFlag = !sardinhaFlag;
        		sardinhaInfos[0]=sardinhaInfosOriginal[0];
        		sardinhaInfos[1]=sardinhaInfosOriginal[1];
        		sardinhaInfos[2]=sardinhaInfosOriginal[2];
        		pesoInicialSliderSardinha.setValue((int) sardinhaInfosOriginal[0]);
        		idadeReproducaoInicialSliderSardinha.setValue((int) sardinhaInfosOriginal[1]);
        		idadeMaximaSliderSardinha.setValue((int) sardinhaInfosOriginal[2]);
        	}
        });
        botaoResetarBoxSardinha.setAlignmentY(Component.TOP_ALIGNMENT);
        botoesBoxSardinha.add(botaoResetarBoxSardinha);
	}

	
	/**
     * Define a color to be used for a given class of animal.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the ocean.
     * @param step Which iteration step it is.
     * @param ocean The ocean whose status is to be displayed.
     */
    public void showStatus(int step, Ocean ocean)
    {
        if(!isVisible())
            setVisible(true);

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        oceanView.preparePaint();
            
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                Fish animal = ocean.getFishAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    oceanView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                	int r = Plancton_Full_COLOR.getRed();
                	int g = Plancton_Full_COLOR.getGreen();
                	int b = Plancton_Full_COLOR.getBlue();
                	int planctonInfluence = (int) ocean.getPlanctonAt(row, col);
                	//rgb(24, 67, 53) -> rgb(24, 51, 123)
                	//85, 170, 74 ->  85, 150, 220
                	//53+53=106+53=160
                	
                    oceanView.drawMark(col, row, new Color(r, Math.max(0, g+planctonInfluence), Math.max(0, (int)(b-planctonInfluence*2*10))));
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(ocean));
        oceanView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Ocean ocean)
    {
        return stats.isViable(ocean);
    }
    
    /**
     * Provide a graphical view of a rectangular ocean. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the ocean.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class OceanView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image oceanImage;

        /**
         * Create a new OceanView component.
         */
        public OceanView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }
        
        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                oceanImage = oceanView.createImage(size.width, size.height);
                g = oceanImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this ocean in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale, yScale);
        }

        /**
         * The ocean view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(oceanImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(oceanImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(oceanImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}

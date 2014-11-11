package dragonhat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Edwin
 */
public class SimulationFrame extends JFrame implements Observer{
    private final JPanel jpSimulation= new JPanel();
    private final JPanel jpError= new JPanel();
    private final JPanel jpButton = new JPanel();
    private final JPanel jpLiftSquare = new JPanel();
    private final JPanel jpDragSquare = new JPanel();
    private final JLabel jlLift = new JLabel("Lift");
    private final JLabel jlDrag = new JLabel("Drag");
    private final JLabel jlResult = new JLabel("Curent simulation:");
    private final JLabel jlMaxResult = new JLabel("Most Optimized simulation: Lift/Drag: ");
    private final JPanel jpResults = new JPanel();
    private final JPanel jpLift = new JPanel();
    private final JPanel jpDrag = new JPanel();
    private final JPanel jpCenter = new JPanel();
    private final JButton jbContinue = new JButton("Continue");
    private final JButton jbStartSimulation = new JButton("Start simulation");
    private final JButton jbStop = new JButton("Stop");
    private final JButton jbErrorLift = new JButton("Error on Lift");
    private final JButton jbErrorDrag = new JButton("Error on Drag");
    private final JButton jbWarningLift = new JButton("Warning on Lift");
    private final JButton jbWarningDrag = new JButton("Warning on Drag");
    
    public SimulationFrame() {
        jpSimulation.setBorder(BorderFactory.createTitledBorder("Simulation"));
        jpSimulation.setLayout(new BoxLayout(jpSimulation, BoxLayout.Y_AXIS));
        jpError.setBorder(BorderFactory.createTitledBorder("Error generation"));
        jpError.setLayout(new BoxLayout(jpError, BoxLayout.Y_AXIS));
       
        JPanel p6 = new JPanel();
        p6.add(jbErrorLift);
        p6.add(jbErrorDrag);
        JPanel p7 = new JPanel();
        p7.add(jbWarningLift);
        p7.add(jbWarningDrag);
        jpError.add(p6);
        jpError.add(p7);
        jbWarningDrag.setEnabled(false);
        jbWarningLift.setEnabled(false);
        jbErrorDrag.setEnabled(false);
        jbErrorLift.setEnabled(false);
        jpCenter.setLayout(new GridLayout(2, 2));
        jpButton.add(jbContinue);
        jpButton.add(jbStop);
        jbStop.setEnabled(false);
        jbContinue.setEnabled(false);
        jpLiftSquare.setBackground(Color.green);
        jpDragSquare.setBackground(Color.green);
        jpLift.add(jlLift);
        jpLift.add(jpLiftSquare);
        jpDrag.add(jlDrag);
        jpDrag.add(jpDragSquare);
        jpResults.setLayout(new GridLayout(2, 1));
       //jpResults.setSize(600, 50);
        jpResults.add(jlMaxResult);
        jpResults.add(jlResult);
        JPanel p5 = new JPanel();
        p5.add(jbStartSimulation);
        jpCenter.setLayout(new GridLayout(2, 2));
        jpCenter.add(jpLift);
        jpCenter.add(p5);
        jpCenter.add(jpDrag);
        jpCenter.add(jpButton);

        jpSimulation.add(jpCenter);
        jpSimulation.add(jpResults);
        jpSimulation.add(jpError);
        this.add(jpSimulation);
        this.setVisible(true);
        this.setSize(600, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    } 
    
    public JButton getJbErrorLift(){
        return this.jbErrorLift;
    }
    public JButton getJbErrorDrag(){
        return this.jbErrorDrag;
    }
    public JButton getJbWarningLift(){
        return this.jbWarningLift;
    }
    public JButton getJbWarningDrag(){
        return this.jbWarningDrag;
    }
    public JButton getJbStartSimulation() {
        return this.jbStartSimulation;
    }
    public JButton getJbContrinue() {
        return this.jbContinue;
    }
    public JButton getJbStop() {
        return this.jbStop;
    }
    public void setFlagLiftColor(Color color) {
        this.jpLiftSquare.setBackground(color);
    }
    public void setFlagDragColor(Color color) {
        this.jpDragSquare.setBackground(color);
    }
    public JLabel getJlResult() {
        return jlResult;
    }
    public JLabel getJlMaxResult() {
        return jlMaxResult;
    }
    public void setActionListener(SimulationFrameController controller){
        this.getJbErrorLift().addActionListener(controller);
        this.getJbErrorDrag().addActionListener(controller);
        this.getJbWarningLift().addActionListener(controller);
        this.getJbWarningDrag().addActionListener(controller);
        this.getJbStartSimulation().addActionListener(controller);
        this.getJbContrinue().addActionListener(controller);
        this.getJbStop().addActionListener(controller);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

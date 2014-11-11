package dragonhat;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Edwin
 */
public class InitialisationFrame extends JFrame implements Observer{
    private final JPanel jpInit = new JPanel();
    private final JPanel jpTextField = new JPanel();
    private final JPanel jpName = new JPanel();
    private final JButton jbStart = new JButton("Start simulation");
    private final JButton jbLoad = new JButton("Load simulation");
    private final JLabel jlName = new JLabel("Simulation name");
    private final JTextField jtName = new JTextField(10);
    private final JTextField jtCx = new JTextField(10);
    private final JTextField jtCz = new JTextField(10);
    private final JTextField jtSx = new JTextField(10);
    private final JTextField jtSz = new JTextField(10);
    private final JLabel jlCx = new JLabel("Cx");
    private final JLabel jlCz = new JLabel("Cz");
    private final JLabel jlSx = new JLabel("Sx");
    private final JLabel jlSz = new JLabel("Sz");
    private final JCheckBox jcAutoRecover = new JCheckBox("Auto-Recover");

    
    public InitialisationFrame() {
        jpInit.setBorder(BorderFactory.createTitledBorder("Simulation Initialisation"));
        jpName.add(jlName);
        jpName.add(jtName);
        jpTextField.add(jlCx);
        jpTextField.add(jtCx);
        jpTextField.add(jlCz);
        jpTextField.add(jtCz);
        jpTextField.add(jlSx);
        jpTextField.add(jtSx);
        jpTextField.add(jlSz);
        jpTextField.add(jtSz);
        JPanel pd = new JPanel();
        pd.add(jcAutoRecover);
        //jpInit.setLayout(new GridLayout(5, 1));
        jpInit.setLayout(new BoxLayout(jpInit, BoxLayout.Y_AXIS));
        jcAutoRecover.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpInit.add(jpName);
        jpInit.add(pd);
        jpInit.add(jpTextField);
        JPanel p = new JPanel();
        p.add(jbStart);
        p.add(jbLoad);
        jpInit.add(p);
        this.add(jpInit);
        this.setVisible(true);
        this.setSize(600, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }  

    public JButton getJbStart(){
        return this.jbStart;
    }
    public JButton getJbLoad(){
        return this.jbLoad;
    }
    public JTextField getJtName() {
        return this.jtName;
    }
    public JTextField getJtCx() {
        return this.jtCx;
    }
    public JTextField getJtCz() {
        return this.jtCz;
    }
    public JTextField getJtSx() {
        return this.jtSx;
    }
    public JTextField getJtSz() {
        return this.jtSz;
    }
    public JCheckBox getJcAutoRecover() {
        return this.jcAutoRecover;
    }

    /**
     *
     * @param controller
     */
    
    public void setActionListener(InitialisationFrameController controller){
        this.getJbStart().addActionListener(controller);
        this.getJbLoad().addActionListener(controller);
    }
    @Override
    public void update(Observable o, Object arg) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

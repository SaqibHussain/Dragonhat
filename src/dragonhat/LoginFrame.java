package dragonhat;


import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



/**
 *
 * @author Edwin
 */
public class LoginFrame extends JFrame implements Observer {
    private final JLabel jlName = new JLabel("User name");
    private final JLabel jlPassword = new JLabel("Password");
    private final JTextField jtName = new JTextField(15);
    private final JPasswordField jtPassword = new JPasswordField(15);
    private final JButton jbValidate = new JButton("Validate");
    private final JButton jbQuit = new JButton("Quit");
    private final JPanel jpButton = new JPanel();
    private final JPanel jpName = new JPanel();
    private final JPanel jpPassword = new JPanel();
    
    public LoginFrame() {
        this.setLayout(new GridLayout(3, 1));
        jpName.add(jlName);
        jpName.add(jtName);
        jpPassword.add(jlPassword);
        jpPassword.add(jtPassword);
        jpButton.add(jbValidate);
        jpButton.add(jbQuit);
        this.setTitle("Login");
        this.add(jpName);
        this.add(jpPassword);
        this.add(jpButton);
        this.setSize(300, 140);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public JButton getJbValidate()
    {
        return this.jbValidate;
    }
    public JButton getJbQuit()
    {
        return this.jbQuit;
    }
    public JTextField getJtName()
    {
        return this.jtName;
    }
    public JTextField getJtPassword()
    {
        return this.jtPassword;
    }
    
    public void setActionListener(LoginFrameController controller)
    {
        this.getJbValidate().addActionListener(controller);
        this.getJbQuit().addActionListener(controller);
    }
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
/*LoginFrame lf = new LoginFrame();
            LoginFrameController lfc = new LoginFrameController(lf);
            lf.setActionListener(lfc);*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Edwin
 */
public class LoginFrameController implements ActionListener {

    LoginFrame view;
    public LoginFrameController(LoginFrame view) {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
          if (e.getSource() == this.view.getJbValidate()) {
             if ( !view.getJtName().getText().equals("") && !view.getJtPassword().getText().equals("")) {
                 DBInterface test = new DBInterface();
                    test.InitUser(this.view.getJtName().getText(), this.view.getJtPassword().getText());
                    InitialisationFrame initialisationFrame = new InitialisationFrame();
                    InitialisationFrameController initialisationFrameController = new InitialisationFrameController(initialisationFrame);
                    initialisationFrame.setActionListener(initialisationFrameController);
                    view.dispose();
                }
            } else if (e.getSource() == this.view.getJbQuit()) {
                System.exit(0);
            } else {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage(), "Autentification error" , JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.getMessage());
        }
    }
}

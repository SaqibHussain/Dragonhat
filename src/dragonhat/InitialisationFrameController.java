/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Edwin
 */
public class InitialisationFrameController implements ActionListener {
    InitialisationFrame view;

    public InitialisationFrameController(InitialisationFrame view) {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
          if (e.getSource() == this.view.getJbStart()) {
              Parameter p = new Parameter();
              p.Cx=Double.parseDouble(view.getJtCx().getText());
              p.Cz=Double.parseDouble(view.getJtCz().getText());
              p.Sx=Double.parseDouble(view.getJtSx().getText());
              p.Sz=Double.parseDouble(view.getJtSz().getText());
              int wfid =  DBInterface.CreateWorkflow(this.view.getJtName().getText(), this.view.getJcAutoRecover().isSelected());
              Workflow wf = new Workflow(wfid, p);
              wf.InitWorkflow(false);
              Shared.getInstance();
              Shared.getInstance().setWf(wf);
              Shared.getInstance().setAutorecover(this.view.getJcAutoRecover().isSelected());
              SimulationFrame simulationFrame = new SimulationFrame();
              SimulationFrameController simulationFrameController = new SimulationFrameController(simulationFrame);
              simulationFrame.setActionListener(simulationFrameController);
              this.view.dispose();
            } else if (e.getSource() == this.view.getJbLoad()) {
                LoadFrame loadFrame= new LoadFrame(this.view);
                LoadFrameController loadFrameController = new LoadFrameController(loadFrame);
                loadFrame.setActionListener(loadFrameController);
                
            } else {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage(), "Autentification error" , JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.getMessage());
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import javax.swing.JTable;

/**
 *
 * @author Edwin
 */
public class LoadFrameController extends Observable implements ActionListener {

    LoadFrame view;
    public LoadFrameController(LoadFrame view) throws Exception {
        this.view = view;
        List<HashMap<String, Object>>res = DBInterface.ReadWorkflows();
        String[][] table = new String [res.size()][4];
        int i = 0;
        for (HashMap<String, Object> r : res) {
            int id = (int)r.get("id_simulation");
            String sid = ""+id;
            String name = (String)r.get("name_simulation");
            Object date = r.get("date_simulation");
            String autoRecover;
            if ((Boolean)r.get("autorecover")==false) {
                autoRecover="NO";
            } else {
                autoRecover="YES";
            }
            table[i][0]=sid;
            table[i][1]=name;
            table[i][2]=date.toString();
            table[i][3]=autoRecover;
            i++;
        }
        String[] header = {"Id","Name","Date","Autorecover"};
        this.view.setJtTable(header, table);
        notifyObservers();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
          if (e.getSource() == this.view.getJbCancel()) {
              this.view.dispose();
            } else if (e.getSource() == this.view.getJbLoad()) {
                if (this.view.getJtTable().getSelectedRow() >= 0) {
                    int rowIndex = this.view.getJtTable().getSelectedRow();
                    Workflow wf = new Workflow(Integer.parseInt((String)this.view.getJtTable().getValueAt(rowIndex, 0)));
                    SimulationFrame simulationFrame = new SimulationFrame();
                    wf.InitWorkflow(true);
                    Shared.getInstance().setWf(wf);
                    SimulationFrameController simulationFrameController = new SimulationFrameController(simulationFrame);
                    simulationFrame.setActionListener(simulationFrameController);
                    if (((String)this.view.getJtTable().getValueAt(rowIndex, 3)).equals("YES")) {
                        Shared.getInstance().setAutorecover(true);
                    } else  {
                        Shared.getInstance().setAutorecover(false);
                    }
                    this.view.dispose();
                    this.view.getCaller().dispose();
                }
                
            } else {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage(), "Autentification error" , JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.getMessage());
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Edwin
 */
public class LoadParamsFrameController extends Observable implements ActionListener {

    LoadParamsFrame view;
    public LoadParamsFrameController(LoadParamsFrame view) throws Exception {
        this.view = view;
        List<HashMap<String, Object>>res = Shared.getInstance().getParams();
        String[][] table = new String [res.size()][6];
        int i = 0;
        for (HashMap<String, Object> r : res) {
            table[i][0]=""+(int)r.get("id");
            table[i][1]=""+(double)r.get("Cx");
            table[i][2]=""+(double)r.get("Cz");
            table[i][3]=""+(double)r.get("Sx");
            table[i][4]=""+(double)r.get("Sz");
            table[i][5]=""+(double)r.get("liftdrag");
            i++;
        }
        String[] header = {"Id Param","Cx","Cz","Sx","Sz","Lift/Drag"};
        this.view.setJtTable(header, table);
        notifyObservers();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
          if (e.getSource() == this.view.getJbCancel()) {
              view.getCaller().getJbContrinue().setEnabled(true);
              view.getCaller().getJbStop().setEnabled(true);
              this.view.dispose();
            } else if (e.getSource() == this.view.getJbLoad()) {
                if (this.view.getJtTable().getSelectedRow() >= 0) {
                    int rowIndex = this.view.getJtTable().getSelectedRow();
                    
                    int id = (Integer.parseInt((String)this.view.getJtTable().getValueAt(rowIndex, 0)));
                    Shared.getInstance().setIdresult(id);
                    Shared.getInstance().setContinuewf(true);
                    Shared.getInstance().getLock().unlock();
                    view.getCaller().getJbContrinue().setEnabled(false);
                    view.getCaller().getJbStop().setEnabled(false);
                    this.view.dispose();
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

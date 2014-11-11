/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edwin
 */
public class LoadParamsFrame extends JFrame implements Observer {
    private JTable jtTable;
    private final SimulationFrame caller;
    private DefaultTableModel dtm;
    private final JButton jbLoad= new JButton("Load");
    private final JButton jbCancel = new JButton("Cancel");
    private final JPanel jpButton = new JPanel();
    private final JPanel jpTable = new JPanel();
    
    public LoadParamsFrame(SimulationFrame caller) {
        this.caller = caller;
        jpButton.add(jbLoad);
        jpButton.add(jbCancel);
        String[] header = {"Id Param","Cx","Cz","Sx","Sz","Lift/Drag"};
        Object[][] data = {{"Id Param","Cx","Cz","Sx","Sz","Lift/Drag"}};
        this.dtm = new DefaultTableModel(data, header);
        jtTable = new JTable(this.dtm);
        jtTable.setSize(200, 200);
        jpTable.add(jtTable.getTableHeader(), BorderLayout.NORTH);
        jpTable.add(jtTable, BorderLayout.CENTER);
        this.add(jpTable, BorderLayout.CENTER);
        this.add(jpButton, BorderLayout.SOUTH);
        this.setSize(520, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }
    public JButton getJbLoad(){
        return this.jbLoad;
    }
    public JButton getJbCancel(){
        return this.jbCancel;
    }
    public JTable getJtTable() {
        return this.jtTable;
    }
    public SimulationFrame getCaller() {
        return this.caller;
    }
    public void setJtTable(String[] header, String[][] data) {
        dtm = new DefaultTableModel(data, header); //(DefaultTableModel)this.jtTable.getModel();
        dtm.fireTableDataChanged();
        this.jtTable.setModel(dtm);
        
    }
    public void setActionListener(LoadParamsFrameController controller){
        this.getJbCancel().addActionListener(controller);
        this.getJbLoad().addActionListener(controller);
    }
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

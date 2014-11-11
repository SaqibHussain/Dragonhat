package dragonhat;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwin
 */
public class LoadFrame extends JFrame implements Observer {
    
    private final JTable jtTable;
    private final JFrame caller;
    private DefaultTableModel dtm;
    private final JButton jbLoad= new JButton("Load");
    private final JButton jbCancel = new JButton("Cancel");
    private final JPanel jpButton = new JPanel();
    private final JPanel jpTable = new JPanel();
    
    
    public LoadFrame(JFrame caller) {
        jpButton.add(jbLoad);
        jpButton.add(jbCancel);
        String[] header = {"Id","Name","Date","Autorecover"};
        Object[][] data = {{"1","TestName","14/02/2014","YES"}};
        this.dtm = new DefaultTableModel(data, header);
        jtTable = new JTable(this.dtm);
        jtTable.setSize(200, 200);
        jpTable.add(jtTable.getTableHeader(), BorderLayout.NORTH);
        jpTable.add(jtTable, BorderLayout.CENTER);
        this.add(jpTable, BorderLayout.CENTER);
        this.add(jpButton, BorderLayout.SOUTH);
        this.setSize(350, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.caller = caller;
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
    public JFrame getCaller() {
        return this.caller;
    }
    public void setJtTable(String[] header, String[][] data) {
        dtm = new DefaultTableModel(data, header); //(DefaultTableModel)this.jtTable.getModel();
        dtm.fireTableDataChanged();
        this.jtTable.setModel(dtm);
        
    }
    public void setActionListener(LoadFrameController controller){
        this.getJbCancel().addActionListener(controller);
        this.getJbLoad().addActionListener(controller);
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
}

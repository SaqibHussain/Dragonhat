/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragonhat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Edwin
 */
public class SimulationFrameController implements Observer, ActionListener {

    private final SimulationFrame view;
    private final Workflow wf;

    public SimulationFrameController(SimulationFrame view) throws Exception {
        this.view = view;
        this.wf = Shared.getInstance().getWf();
        Shared.getInstance().addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.view.getJbErrorLift()) {
                Shared.getInstance().getLock().lock();
                Shared.getInstance().setFlagliftAndNotify(Flag.RED);
            } else if (e.getSource() == this.view.getJbErrorDrag()) {
                Shared.getInstance().getLock().lock();
                Shared.getInstance().setFlagdragAndNotify(Flag.RED);
            } else if (e.getSource() == this.view.getJbWarningLift()) {
                Shared.getInstance().getLock().lock();
                Shared.getInstance().setFlagliftAndNotify(Flag.AMBER);
            } else if (e.getSource() == this.view.getJbWarningDrag()) {
                Shared.getInstance().getLock().lock();
                Shared.getInstance().setFlagdragAndNotify(Flag.AMBER);
            } else if (e.getSource() == this.view.getJbStartSimulation()) {
                this.view.getJbStartSimulation().setEnabled(false);
                this.wf.start();
                this.view.getJbErrorDrag().setEnabled(true);
                this.view.getJbErrorLift().setEnabled(true);
                this.view.getJbWarningDrag().setEnabled(true);
                this.view.getJbWarningLift().setEnabled(true);
            } else if (e.getSource() == this.view.getJbStop()) {
                JOptionPane.showMessageDialog(null, "Simulation abort", "End of simulation", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (e.getSource() == this.view.getJbContrinue()) {
                // mettre dans shared les nouvo param
                LoadParamsFrame lpf = new LoadParamsFrame(this.view);
                LoadParamsFrameController lpfc = new LoadParamsFrameController(lpf);
                lpf.setActionListener(lpfc);
                this.view.getJbErrorDrag().setEnabled(true);
                this.view.getJbErrorLift().setEnabled(true);
                this.view.getJbWarningDrag().setEnabled(true);
                this.view.getJbWarningLift().setEnabled(true);

            } else {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage(), "Autentification error" , JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        double Cx = Shared.getInstance().getParam().Cx;
        double Cz = Shared.getInstance().getParam().Cz;
        double Sx = Shared.getInstance().getParam().Sx;
        double Sz = Shared.getInstance().getParam().Sz;
        double result = Shared.getInstance().getResult();
        double bestResult = Shared.getInstance().getBestResult();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        this.view.getJlResult().setText("Curent simulation: Cx: " + df.format(Cx) + " Cz: " + df.format(Cz) + " Sx: " + df.format(Sx) + " Sz: " + df.format(Sz) + "Lift/Drag: " + df.format(result));
        this.view.getJlMaxResult().setText("Most optimized simulation: Lift/Drag: " + df.format(bestResult));
        if (Shared.getInstance().isIsFinished() == true) {
            JOptionPane.showMessageDialog(null, "Simulation succeed!\n------------------------------\nCx :"+Cx+"\nCz: "+Cz+"\nSx: "+Sx+"\nSz: "+Sz+"\n\nLift/Drag: "+bestResult, "End of simulation", JOptionPane.INFORMATION_MESSAGE);
            try {
                Graph.PlotGraph((int) Shared.getInstance().getWf().getIdWf());
            } catch (Exception ex) {
                Logger.getLogger(SimulationFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        } else {
            this.view.getJbErrorDrag().setEnabled(false);
            this.view.getJbErrorLift().setEnabled(false);
            this.view.getJbWarningDrag().setEnabled(false);
            this.view.getJbWarningLift().setEnabled(false);
            if (Shared.getInstance().getFlagdrag() == Flag.AMBER) {
                this.view.setFlagDragColor(Color.orange);
                if (Shared.getInstance().isAutorecover()) {
                    JOptionPane.showMessageDialog(null, "Warning: An error has occured on the drag calculation. Auto recovery started.", "Drag Warning", JOptionPane.WARNING_MESSAGE);
                    Shared.getInstance().getLock().unlock();
                    this.view.getJbErrorDrag().setEnabled(true);
                    this.view.getJbErrorLift().setEnabled(true);
                    this.view.getJbWarningDrag().setEnabled(true);
                    this.view.getJbWarningLift().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Warning: An error has occured on the drag calculation. Simulation stopped wating for your decision.", "Drag Warning", JOptionPane.WARNING_MESSAGE);
                    this.view.getJbContrinue().setEnabled(true);
                    this.view.getJbStop().setEnabled(true);
                }
            } else if (Shared.getInstance().getFlagdrag() == Flag.RED) {
                this.view.setFlagDragColor(Color.red);
                JOptionPane.showMessageDialog(null, "Error: An error has occured on the drag calculation. Simulation abort.", "Drag Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else if (Shared.getInstance().getFlaglift() == Flag.AMBER) {
                this.view.setFlagLiftColor(Color.orange);
                if (Shared.getInstance().isAutorecover()) {
                    JOptionPane.showMessageDialog(null, "Warning: An error has occured on the lift calculation. Auto recovery started.", "Lift Warning", JOptionPane.WARNING_MESSAGE);
                    Shared.getInstance().getLock().unlock();
                    this.view.getJbErrorDrag().setEnabled(true);
                    this.view.getJbErrorLift().setEnabled(true);
                    this.view.getJbWarningDrag().setEnabled(true);
                    this.view.getJbWarningLift().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Warning: An error has occured on the lift calculation. Simulation stopped wating for your decision.", "Lift Warning", JOptionPane.WARNING_MESSAGE);
                    this.view.getJbContrinue().setEnabled(true);
                    this.view.getJbStop().setEnabled(true);
                }
            } else if (Shared.getInstance().getFlaglift() == Flag.RED) {
                this.view.setFlagLiftColor(Color.red);
                JOptionPane.showMessageDialog(null, "Error: An error has occured on the lift calculation. Simulation abort.", "Lift Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else if (Shared.getInstance().getFlaglift() == Flag.GREEN && Shared.getInstance().getFlagdrag() == Flag.GREEN) {
                this.view.setFlagLiftColor(Color.green);
                this.view.setFlagDragColor(Color.green);
                this.view.getJbErrorDrag().setEnabled(true);
                this.view.getJbErrorLift().setEnabled(true);
                this.view.getJbWarningDrag().setEnabled(true);
                this.view.getJbWarningLift().setEnabled(true);
            }
        }
    }
}

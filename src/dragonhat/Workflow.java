/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragonhat;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephane
 */
public class Workflow extends Thread {

    private final int id;
    private int step_number;
    private Flag flag_lift;
    private Flag flag_drag;
    private final Optimizer opti;

    public Workflow(int workflowID) {
        id = workflowID;
        opti = new Optimizer();
        step_number = 0;
        flag_lift = Flag.GREEN;
        flag_drag = Flag.GREEN;
    }

    public Workflow(int workflowID, Parameter p) {
        id = workflowID;
        opti = new Optimizer(p);
        step_number = 0;
        flag_lift = Flag.GREEN;
        flag_drag = Flag.GREEN;
    }

    public void InitWorkflow(boolean RecoverMode) throws Exception {

        /*if(RecoverMode == false)
         {
         opti.GetParamsOptimizer(step_number, id);
         }
         else*/
        if (RecoverMode == true) {
            opti.GetRecovery(id);
        }
    }

    public int getIdWf() {
        return id;
    }

    //TODO
    @Override
    public void run() {

        boolean error = false;
        synchronized (this) {
            do {
                try {
                    //Get parameters from optimizer
                    Parameter p = opti.param;
                    flag_lift = Shared.getInstance().getFlaglift();
                    flag_drag = Shared.getInstance().getFlagdrag();

                    //Get results from simulation, send them to optimizer
                    opti.lift = Simulation.Lift(p, flag_lift);
                    opti.drag = Simulation.Drag(p, flag_drag);

                    //Store results no matter what the flag is
                    opti.StoreResult(id);

                    //Check for errors
                    Flag res = opti.CheckError();

                    //If no error, continue the workflow
                    if (res == Flag.GREEN) {

                        double result = opti.lift.value / opti.drag.value;
                        Shared.getInstance().setParam(p);
                        Shared.getInstance().setResult(result);
                        if (Shared.getInstance().getBestResult() < result) {
                            Shared.getInstance().setBestResult(result);
                            Shared.getInstance().setBestParam(p);
                        }
                        if (step_number % 50 == 0) {
                            Shared.getInstance().updateObserver();
                        }

                        opti.GetParamsOptimizer(step_number + 1, id);
                    } //If amber flag
                    else if (res == Flag.AMBER) {
                        //Send flag results to shared 
                        Shared.getInstance().setFlaglift(opti.lift.f);
                        Shared.getInstance().setFlagdrag(opti.drag.f);

                        if (!Shared.getInstance().isAutorecover()) {
                            Shared.getInstance().setParams(DBInterface.ReadLastGoodResults(id));
                        }

                        //Wait for user action
                        Shared.getInstance().getLock().lock();

                        //If user wants to continue
                        if (Shared.getInstance().isContinuewf()) {
                            //If workflow is in auto recovery
                            if (Shared.getInstance().isAutorecover()) {
                                //Get las good parameter
                                opti.GetLastParameter(id);

                            } else {
                                opti.GetParameterID(id, Shared.getInstance().getIdresult());
                            }

                            Shared.getInstance().setFlagdrag(Flag.GREEN);
                            Shared.getInstance().setFlaglift(Flag.GREEN);
                            Shared.getInstance().updateObserver();
                        }
                        Shared.getInstance().getLock().unlock();
                    } else {
                        //Send flag results to shared 
                        Shared.getInstance().setFlaglift(opti.lift.f);
                        Shared.getInstance().setFlagdrag(opti.drag.f);
                        error = true;
                        Shared.getInstance().setFlagdrag(Flag.GREEN);
                        Shared.getInstance().setFlaglift(Flag.GREEN);

                        //Notify user, abort workflow
                    }
                    //System.out.println("END STEP Number " + step_number);
                    step_number++;
                } catch (Exception ex) {
                    Logger.getLogger(Workflow.class.getName()).log(Level.SEVERE, null, ex);
                }
               // System.out.println(step_number);
            } while (StoppingCondition() == false && error == false);
            if (!error) {
                Shared.getInstance().setIsFinished(true);
            }
        }
    }

    //TODO
    public boolean StoppingCondition() {

        double criteria = 0.05;
        return (Math.abs(opti.param.max_Cx - opti.param.min_Cx) < criteria
                && Math.abs(opti.param.max_Cz - opti.param.min_Cz) < criteria
                && Math.abs(opti.param.max_Sx - opti.param.min_Sx) < criteria
                && Math.abs(opti.param.max_Sz - opti.param.min_Sz) < criteria);
       //return(step_number > 2000);

    }

}

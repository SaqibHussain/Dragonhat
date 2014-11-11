/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;


/**
 *
 * @author Stephane
 */
public class Dragonhat {

    
    public static void main(String[] args) {
       // DBInterface test = new DBInterface();
        try {
            /*test.InitUser("dragon", "hat");
            System.out.println("User id: " + DBInterface.userID);
            
            int wfid =  DBInterface.CreateWorkflow("another workflow");
            Parameter p = new Parameter();
            p.Cx = 1.0;
            p.Cz = 1.0;
            p.Sx = 1.0;
            p.Sz = 1.0;
            Workflow w = new Workflow(wfid,p);
            
            w.InitWorkflow(false);
            
            w.StartWorkflow();
            //System.out.println("Workflow id: " + wfid);
            
           
            System.out.println("DONE");*/
            
            LoginFrame lf = new LoginFrame();
            LoginFrameController lfc = new LoginFrameController(lf);
            lf.setActionListener(lfc);
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
//Logger.getLogger(Dragonhat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

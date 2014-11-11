/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Stephane
 */
public class Optimizer {
    public Parameter param;
    public Result lift;
    public Result drag;
    
    private final int step_threshold = 100;
    
    private ArrayList<Parameter> blacklist;
    
    public Optimizer()
    {
        param = new Parameter();
        lift = new Result();
        drag = new Result();
    }
    
    public Optimizer(Parameter p)
    {
        param = p;
        lift = new Result();
        drag = new Result();
        blacklist = new ArrayList<Parameter>();
    }
    
    //TODO
    public void GetParamsOptimizer(int stepNo, int workflowId) throws Exception
    {

       if(stepNo > 0 && stepNo % step_threshold == 0)
       {
           //List<HashMap<String, Object>> res = DBInterface.Read(workflowId);
           List<HashMap<String, Object>> res = DBInterface.ReadResults(workflowId);
           
           ArrayList Cx = new ArrayList();
           ArrayList Cz = new ArrayList();
           
           ArrayList Sx = new ArrayList();
           ArrayList Sz = new ArrayList();
           
           for(HashMap<String,Object> s: res)
           {
               Flag f = Flag.valueOf(s.get("status").toString().toUpperCase());
               if(f ==  Flag.GREEN)
               {
                   Cx.add((double)s.get("Cx"));
                   Cz.add((double)s.get("Cz"));
                   
                   Sx.add((double)s.get("Sx"));
                   Sz.add((double)s.get("Sz"));
               }
           }
           
           Statistics StatCx = new Statistics(Cx);
           Statistics StatCz = new Statistics(Cz);
           
           Statistics StatSx = new Statistics(Sx);
           Statistics StatSz = new Statistics(Sz);
           
           param.min_Cx = Math.abs(StatCx.getMean() - StatCx.getStdDev());
           param.max_Cx = Math.abs(StatCx.getMean() + StatCx.getStdDev());
           
           param.min_Cz = Math.abs(StatCz.getMean() - StatCz.getStdDev());
           param.max_Cz = Math.abs(StatCz.getMean() + StatCz.getStdDev());
           
           param.min_Sx = Math.abs(StatSx.getMean() - StatSx.getStdDev());
           param.max_Sx = Math.abs(StatSx.getMean() + StatSx.getStdDev());
           
           param.min_Sz = Math.abs(StatSz.getMean() - StatSz.getStdDev());
           param.max_Sz = Math.abs(StatSz.getMean() + StatSz.getStdDev());
           
           System.out.println("minCx: " + param.min_Cx + " maxCx: " + param.max_Cx + " minCz: " + param.min_Cz + " maxCz: " + param.max_Cz);
       }
       
       param.Cx = Math.abs((Math.random() * (param.max_Cx - param.min_Cx)) + param.min_Cx);
       param.Cz = Math.abs((Math.random() * (param.max_Cz - param.min_Cz)) + param.min_Cz);
       
       param.Sx = Math.abs((Math.random() * (param.max_Sx - param.min_Sx)) + param.min_Sx);
       param.Sz = Math.abs((Math.random() * (param.max_Sz - param.min_Sz)) + param.min_Sz);
       
       if(param.Cx < 0 || param.Cz < 0 || param.Sx < 0 || param.Sz < 0)
       {
           System.out.println("ERROR at step No: " + stepNo + "Cx: " + param.Cx + " Cz: " + param.Cz + " Sx: " + param.Sx + " Sz: " + param.Sz);
       }
       
    }
    
    
    
    public void StoreResult(int workflow_id) throws Exception
    {
      DBInterface.Add(workflow_id, param, lift, drag);
    }
    
    
    public Flag CheckError()
    {
      boolean error = (lift.f == Flag.AMBER || lift.f == Flag.RED || drag.f == Flag.AMBER || drag.f == Flag.RED);
      
      if(error)
      {
          blacklist.add(param);
      }
      
      Flag f = Flag.GREEN;
                 
                 if(lift.f == Flag.AMBER || drag.f == Flag.AMBER)
                 {
                     f = Flag.AMBER;
                 }
                 
                 if(lift.f == Flag.RED || drag.f == Flag.RED)
                 {
                     f = Flag.RED;
                 }
      
      return f;
    }
    
    
    public void GetRecovery(int workflow_id) throws Exception
    {
        this.blacklist = (ArrayList<Parameter>) Recovery.SetBlackList(workflow_id);
        this.param = Recovery.GetLastParameter(workflow_id);
    }
    
    public void GetLastParameter(int workflow_id) throws Exception
    {
        Parameter a = Recovery.GetLastParameter(workflow_id);
        //this.param = Recovery.GetLastParameter(workflow_id);
        this.param.Cx = a.Cx;
        this.param.Cz = a.Cz;
        
        this.param.Sx = a.Sx;
        this.param.Sz = a.Sz;
    }
    
    public void GetParameterID(int workflow_id, int parameter_id) throws Exception
    {
        Parameter a = Recovery.GetParameterID(workflow_id, parameter_id); 
        //this.param = Recovery.GetParameterID(workflow_id, parameter_id);
        this.param.Cx = a.Cx;
        this.param.Cz = a.Cz;
        
        this.param.Sx = a.Sx;
        this.param.Sz = a.Sz;
    }
}

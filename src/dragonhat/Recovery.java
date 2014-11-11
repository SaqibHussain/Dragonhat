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
public class Recovery {
    

    public static Parameter GetLastParameter(int workflowId) throws Exception
    {
        Parameter p = null;
        List<HashMap<String, Object>> res = DBInterface.Read(workflowId);
        
        if(res.size() == 0)
        {
            throw new Exception("GETLASTPARAM_ERROR_USER_NOT_WF_OWNER");
        }
        
        for(HashMap<String,Object> s: res)
        {
            Flag f = Flag.valueOf(s.get("status").toString().toUpperCase());
            
            if(f ==  Flag.GREEN)
            {
                Parameter curr = new Parameter();
                curr.Cx = (double)s.get("Cx");
                curr.Cz = (double)s.get("Cz");
                
                curr.Sx = (double)s.get("Sx");
                curr.Sz = (double)s.get("Sz");
                
                curr.min_Cx = (double)s.get("min_cx");
                curr.max_Cx = (double)s.get("max_cx");
                
                curr.min_Cz = (double)s.get("min_cz");
                curr.max_Cz = (double)s.get("max_cz");
                
                curr.min_Sx = (double)s.get("min_sx");
                curr.max_Sx = (double)s.get("max_sx");
                
                curr.min_Sz = (double)s.get("min_sz");
                curr.max_Sz = (double)s.get("max_sz");
                
                p = curr;
            }

        }
        if(p == null)
        {
            throw new Exception("GETLASTPARAM_ERROR_NO_LAST_GOOD_PARAM");
        }
        else
        {
            return p;
        }
        
        
    }
    
    public static Parameter GetParameterID(int workflowId, int parameterID) throws Exception
    {
        Parameter p = null;
        List<HashMap<String, Object>> res = DBInterface.ReadResultID(workflowId, parameterID);
        
        if(res.size() == 0)
        {
            throw new Exception("GETPARAMID_ERROR_USER_NOT_WF_OWNER");
        }
        
        for(HashMap<String,Object> s: res)
        {
            Flag f = Flag.valueOf(s.get("status").toString().toUpperCase());
            
            if(f ==  Flag.GREEN)
            {
                Parameter curr = new Parameter();
                curr.Cx = (double)s.get("Cx");
                curr.Cz = (double)s.get("Cz");
                
                curr.Sx = (double)s.get("Sx");
                curr.Sz = (double)s.get("Sz");
                
                p = curr;
            }

        }
        if(p == null)
        {
            throw new Exception("GETPARAMID_ERROR_NO_GOOD_PARAM");
        }
        else
        {
            return p;
        }
        
        
    }
    
 
    public static List<Parameter> SetBlackList(int workflowId) throws Exception
    {
        List<Parameter> p = new ArrayList<Parameter>();
        
        List<HashMap<String, Object>> res = DBInterface.Read(workflowId);
        
        for(HashMap<String,Object> s: res)
        {
            Flag f = Flag.valueOf(s.get("status").toString().toUpperCase());
            
            if(f ==  Flag.AMBER || f == Flag.RED)
            {
                Parameter curr = new Parameter();
                curr.Cx = (double)s.get("Cx");
                curr.Cz = (double)s.get("Cz");
                
                curr.Sx = (double)s.get("Sx");
                curr.Sz = (double)s.get("Sz");
                
                p.add(curr);
            }

        }
        
        return p;
    }
}

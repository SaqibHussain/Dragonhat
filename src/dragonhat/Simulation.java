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
public class Simulation {
    
    //TODO
    public static Result Lift(Parameter p, Flag f)
    {
        
        Result r = new Result();
        r.value = p.Cx * p.Sx;
        
        
        if(f == Flag.AMBER || f == Flag.RED)
        {
            r.f = f;
        }
        
        return r;
    }
    
    //TODO
    public static Result Drag(Parameter p, Flag f)
    {
        Result r = new Result();
        r.value = p.Cz * p.Sz;
        
        
        if(f == Flag.AMBER || f == Flag.RED)
        {
            r.f = f;
        }
        
        return r;
    }
}

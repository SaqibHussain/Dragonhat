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
public class Parameter {
    public double Cx;
    public double Cz;
    
    public double Sx;
    public double Sz;
    
    
    //TODO: DEFINE BOUNDARIES FOR PARAMS
    
    public double min_Cx;
    public double min_Cz;
    public double max_Cx;
    public double max_Cz;
    
    public double min_Sx;
    public double min_Sz;
    public double max_Sx;
    public double max_Sz;

    public Parameter() {
        
        Cx = 0.0;
        Cz = 0.0;
        
        Sx = 0.0;
        Sz = 0.0;
        
        this.min_Cx = 0.00001;
        this.max_Cx = 100.0;
        
        this.min_Cz = 0.00001;
        this.max_Cz = 100.0;
        
        this.min_Sx = 0.00001;
        this.max_Sx = 100.0;
        
        this.min_Sz = 0.00001;
        this.max_Sz = 100.0;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import java.util.ArrayList;

public class Statistics 
{
    ArrayList data;
    double size;    

    public Statistics(ArrayList data) 
    {
        this.data = data;
        size = data.size();
    }   

    double getMean()
    {
        double sum = 0.0;
        for(Object a : data)
        {
            sum += (double)a;
        }
        return sum/size;
    }

        double getVariance()
        {
            double mean = getMean();
            double temp = 0;
            
            for(Object a :data)
            {
                temp += (mean-(double)a)*(mean-(double)a);
            }
            
            return temp/size;
        }

        double getStdDev()
        {
            return Math.sqrt(getVariance());
        }
}
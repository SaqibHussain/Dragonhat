/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.Point;
import com.panayotis.gnuplot.dataset.PointDataSet;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Stephane
 */
public class Graph {
    
    public static void PlotGraph(int wfid) throws Exception
    {
        
       /* PointDataSet pds = new PointDataSet();
        pds.add(new Point(2, 1.0, 1.0));
        JavaPlot p = new JavaPlot();
        p.addPlot(pds);
        p.plot();*/
        
        PointDataSet pds = new PointDataSet();
        List<HashMap<String,Object>> pointlist = DBInterface.LoadGraphResults(wfid);
        
        int i = 1;
        for(HashMap<String,Object> s: pointlist)
        {
            //pds.add(new Point((int)s.get("id"), (double)s.get("liftdrag")));
            pds.add(new Point(i, (double)s.get("liftdrag")));
            i++;
        }
        
        JavaPlot p = new JavaPlot();
        p.setTitle("Lift/Drag");
        p.getAxis("x").setLabel("Workflow Step");
        p.getAxis("y").setLabel("Lift/Drag ratio");
        p.addPlot(pds);
        p.plot();
    }
}

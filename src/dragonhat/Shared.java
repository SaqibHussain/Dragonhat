package dragonhat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class Shared extends Observable {

    private static volatile Shared instance = null;

    // D'autres attributs, classiques et non "static".
    private Flag flaglift;
    private Flag flagdrag;
    private boolean autorecover;
    private boolean continuewf;
    private int idresult;
    public String test = "";
    private Workflow wf;
    private final Lock lock;
    private boolean isFinished;
    private Parameter param;
    private double result;
    private Parameter bestParam;
    private double bestResult;

    public synchronized Parameter getBestParam() {
        return bestParam;
    }

    public synchronized void setBestParam(Parameter bestParam) {
        this.bestParam = bestParam;
    }

    public synchronized double getBestResult() {
        return bestResult;
    }

    public synchronized void setBestResult(double bestResult) {
        this.bestResult = bestResult;
    }
    

    public synchronized Parameter getParam() {
        return param;
    }

    public synchronized void setParam(Parameter param) {
        this.param = param;
    }

    public synchronized double getResult() {
        return result;
    }

    public synchronized void setResult(double result) {
        this.result = result;
    }
    
    
    

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
        this.updateObserver();
    }

    public synchronized boolean isIsFinished() {
        return isFinished;
    }

    public ArrayList<Observer> listObserver = new ArrayList();

    public synchronized Lock getLock()
    {
        return this.lock;
    }
    
    @Override
    public synchronized void addObserver(Observer obs) {
        listObserver.add(obs);
    }

    
    public synchronized void updateObserver()
    {
        for(Observer obs : listObserver) {
            obs.update(this, obs);
        }
    }
    public synchronized void setWf(Workflow wf) {
        this.wf = wf;
    }

    public synchronized Workflow getWf() {
        return wf;
    }

    public synchronized int getIdresult() {
        return idresult;
    }

    public synchronized void setIdresult(int idresult) {
        this.idresult = idresult;
    }
    private List<HashMap<String, Object>> params;

    public synchronized boolean isContinuewf() {
        return continuewf;
    }

    public synchronized void setContinuewf(boolean continuewf) {
        this.continuewf = continuewf;
    }

    public synchronized Flag getFlaglift() {
        return flaglift;
    }

    public synchronized void setFlagliftAndNotify(Flag flaglift) {
   
        this.flaglift = flaglift;
        updateObserver();
    }
    public synchronized void setFlaglift(Flag flaglift) {
   
        this.flaglift = flaglift;
    }
    public synchronized Flag getFlagdrag() {
        return flagdrag;
    }

    public synchronized void setFlagdragAndNotify(Flag flagdrag) {
        this.flagdrag = flagdrag;
        updateObserver();
    }
    public synchronized void setFlagdrag(Flag flagdrag) {
        this.flagdrag = flagdrag;
    }
    public synchronized boolean isAutorecover() {
        return autorecover;
    }

    public synchronized void setAutorecover(boolean autorecover) {
        this.autorecover = autorecover;
    }

    public synchronized List<HashMap<String, Object>> getParams() {
        return params;
    }

    public synchronized void setParams(List<HashMap<String, Object>> params) {
        this.params = params;
    }
    

    private Shared() {
        super();
        flaglift = Flag.GREEN;
        flagdrag = Flag.GREEN;
        autorecover = true;
        continuewf = true;
        params = null;
        idresult = 0;
        bestResult = -1;
        param = new Parameter();
        bestParam = new Parameter();
        bestParam.Cx = 0;
        bestParam.Cz = 0;
        bestParam.Sx = 0;
        bestParam.Sz = 0;
        lock = new Lock();
        isFinished = false;
    }

    public final static Shared getInstance() {
        if (Shared.instance == null) {
            synchronized (Shared.class) {
                if (Shared.instance == null) {
                    Shared.instance = new Shared();
                }
            }
        }
        return Shared.instance;
    }
}

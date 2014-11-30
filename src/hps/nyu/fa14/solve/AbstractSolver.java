package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.ISolutionMonitor;
import hps.nyu.fa14.ISolver;
import hps.nyu.fa14.Matrix;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolver implements ISolver {

    @Override
    public abstract ColumnAssignment solve(Matrix m);

    protected List<ISolutionMonitor> monitors = new ArrayList<ISolutionMonitor>();

    @Override
    public void addSolutionMonitor(ISolutionMonitor monitor) {
        monitors.add(monitor);
    }

    protected void notifyNewSolution(ColumnAssignment c) {
        for(ISolutionMonitor m : monitors) {
            m.updateSolution(c);
        }
    }
  
}

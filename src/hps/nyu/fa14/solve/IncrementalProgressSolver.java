package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.ISolutionMonitor;
import hps.nyu.fa14.ISolver;
import hps.nyu.fa14.Matrix;

// Makes sure to write a new solution to disk each time a new (better one) is available
public class IncrementalProgressSolver extends AbstractSolver implements
        ISolutionMonitor {

    private final String outputPath;
    private final ISolver solver;

    public IncrementalProgressSolver(ISolver solver, String output) {
        this.solver = solver;
        this.outputPath = output;
        solver.addSolutionMonitor(this);
    }
    
    @Override
    public void updateSolution(ColumnAssignment c) {
        if(c == null){
            return;
        }
        synchronized (solver) {
            // TODO: Make sure that it is valid before writing to disk
            try {
                c.writeFile(outputPath);
            } catch (Exception IOException) {
                System.err.println("Trouble writing incremental file");
            }
            notifyNewSolution(c);
        }
    }

    @Override
    public ColumnAssignment solve(Matrix m) {
        return solver.solve(m);
    }
}

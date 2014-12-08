package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.ISolutionMonitor;
import hps.nyu.fa14.ISolver;
import hps.nyu.fa14.Matrix;

public class TimedSolver extends AbstractSolver implements
        ISolutionMonitor, Runnable {

    private final int maxSeconds;
    private final ISolver solver;
    private Matrix currentMatrix;
    // Make sure to give yourself this much overhead for setting up the thread
    private final int SETUP_MILLIS = 150;

    public TimedSolver(ISolver solver, int seconds) {
        this.solver = solver;
        maxSeconds = seconds;
    }

    @SuppressWarnings("deprecation")
    // Yes, I know stop is deprecated, but that is silly
    @Override
    public ColumnAssignment solve(Matrix m) {

        // Set up the filler to report the best solution reported so far
        currentMatrix = m;

        // run a thread.
        Thread solveThread = new Thread(this);
        solveThread.start();
        try {
            // Wait until the thread finishes or we time out
            solveThread.join((maxSeconds * 1000) - SETUP_MILLIS);
        } catch (Exception ex) {/* suppress */
            System.out.println();
        }

        if (solveThread.isAlive()) {
            // Interrupt does not do what we need (or expect) it to do, so fail
            // solveThread.interrupt();
            solveThread.stop();
        }
        // Wait a certain amount of time, then kill and output
        return bestSolution;
    }

    @Override
    public void run() {
        solver.addSolutionMonitor(this);
        ColumnAssignment c = solver.solve(currentMatrix);
        // If we get this far, assume the solver returned the best assignment
        updateSolution(c);
    }

    private ColumnAssignment bestSolution;

    @Override
    public void updateSolution(ColumnAssignment c) {
        if(c == null){
            return;
        }
        bestSolution = c;
        notifyNewSolution(c);
    }
}

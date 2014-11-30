package hps.nyu.fa14;

public interface ISolver {
    ColumnAssignment solve(Matrix m);
    void addSolutionMonitor(ISolutionMonitor monitor);
}

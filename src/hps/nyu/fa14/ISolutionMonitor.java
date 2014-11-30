package hps.nyu.fa14;

public interface ISolutionMonitor {

    /**
     * Notify a monitor that an new (better) solution has been produced
     * 
     * @param c
     */
    void updateSolution(ColumnAssignment c);
}

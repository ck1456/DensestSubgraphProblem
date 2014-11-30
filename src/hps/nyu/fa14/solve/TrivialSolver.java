package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.Matrix;

/**
 * A very naive implementation that assumes that we did not get any lemons
 */
public class TrivialSolver extends AbstractSolver {

    @Override
    public ColumnAssignment solve(Matrix m) {
        ColumnAssignment c = new ColumnAssignment(m.cols);
        return c;
    }

}

package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.DensityUtil;
import hps.nyu.fa14.ISolver;
import hps.nyu.fa14.Matrix;

/**
 * A implementation that tries to hill climb to a locally optimal solution
 */
public class LocalSearchSolver extends AbstractSolver {

    @Override
    public ColumnAssignment solve(Matrix m) {
        ISolver randSolver = new RandomSolver();
        ColumnAssignment c = randSolver.solve(m);

        c = localSearch(m, c);
        return c;
    }

    public static ColumnAssignment localSearch(Matrix m, ColumnAssignment c) {
        // Incrementally optimize this ColumnAssignment
        c = c.clone(); // don't modify the original
        double bestDensity = 0;
        ColumnAssignment bestAssignment = c.clone();
        DensityUtil du = new DensityUtil(m);

        int rows = (int)Math.sqrt(m.rows);
        boolean improved = true;
        while (improved) {
            improved = false;
            c = bestAssignment;
            for(int i = 0; i < c.cols.length; i++) {
                c.cols[i] = !c.cols[i]; // flip a bit
                if(du.density(c, rows) > bestDensity) {
                    bestDensity = du.density(c, rows);
                    bestAssignment = c.clone();
                    improved = true;
                    break;
                }
                c.cols[i] = !c.cols[i]; // flip back
            }
        }
        return bestAssignment;
    }

}

package hps.nyu.fa14.solve;

import static org.junit.Assert.assertEquals;
import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.DensityUtil;
import hps.nyu.fa14.Matrix;

import org.junit.Test;

public class LocalSearchSolverTest {

    @Test
    public void testProblem1() throws Exception {
        Matrix m = Matrix.parseFile("data/problem0_input_0.txt");
        
        LocalSearchSolver s = new LocalSearchSolver();
        ColumnAssignment c = s.solve(m);
        DensityUtil du = new DensityUtil(m);
        
        // assert that at least one column was picked
        System.out.println("Density " + du.bestDensity(c));
        
    }

}

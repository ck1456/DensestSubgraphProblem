package hps.nyu.fa14;

import static org.junit.Assert.*;
import hps.nyu.fa14.solve.RandomSolver;

import org.junit.Test;

public class DensityUtilTest {

    @Test
    public void testCalculateDensity() throws Exception {
        Matrix m = Matrix.parseFile("data/problem0_input_0.txt");
        
        RandomSolver s = new RandomSolver();
        ColumnAssignment c = s.solve(m);
        
        DensityUtil d = new DensityUtil(m);
        double density = d.density(c, 22);
        
        // Is this a reasonable test?
        assertTrue(density > .5);
    }

}

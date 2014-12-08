package hps.nyu.fa14.solve;

import static org.junit.Assert.assertTrue;
import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.ISolver;
import hps.nyu.fa14.Matrix;

import org.junit.Test;

public class EvolutionarySolverTest {

    @Test
    public void test_input_0() throws Exception {
        Matrix m = Matrix.parseFile("data/input_0.txt");
        ISolver solver = new EvolutionarySolver();
        ISolver tSolver = new TimedSolver(solver, 11);
        
        ColumnAssignment c = tSolver.solve(m);
        assertTrue(c.lemonCount() > 10);
    }

}

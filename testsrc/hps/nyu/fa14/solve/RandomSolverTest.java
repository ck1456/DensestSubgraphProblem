package hps.nyu.fa14.solve;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.Matrix;

import org.junit.Test;

public class RandomSolverTest {

    @Test
    public void test() {
        Matrix m = new Matrix(2, 2);
        m.values[0][0] = true;
        m.values[1][1] = true;
        
        RandomSolver s = new RandomSolver();
        ColumnAssignment c = s.solve(m);
        // assert that at least one column was picked
        assertTrue(c.cols[0] || c.cols[1]);
    }
    
    @Test
    public void testProblem1() throws Exception {
        Matrix m = Matrix.parseFile("data/problem0_input_0.txt");
        
        RandomSolver s = new RandomSolver();
        ColumnAssignment c = s.solve(m);
        // assert that at least one column was picked
        assertEquals(22, c.lemonCount());
    }

}

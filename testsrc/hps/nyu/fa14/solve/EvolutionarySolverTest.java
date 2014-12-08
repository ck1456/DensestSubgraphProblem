package hps.nyu.fa14.solve;

import static org.junit.Assert.assertEquals;
import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.Matrix;

import org.junit.Test;

public class EvolutionarySolverTest {

    @Test
    public void testProblem1() throws Exception {
        Matrix m = Matrix.parseFile("data/input_0.txt");
        
        EvolutionarySolver s = new EvolutionarySolver();
        ColumnAssignment c = s.solve(m);
        // assert that at least one column was picked
        assertEquals(22, c.lemonCount());
    }

}

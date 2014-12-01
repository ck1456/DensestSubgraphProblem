package hps.nyu.fa14;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatrixTest {

    @Test
    public void test() {
        Matrix m = new Matrix(2, 2);
        m.values[0][0] = true;
        m.values[1][1] = true;
        
        assertEquals(1, m.getAllColumnsSum());
    }
    
    @Test
    public void testParse() throws Exception {
        Matrix m = Matrix.parseFile("data/problem0_input_0.txt");
        
        assertEquals(500, m.getAllColumnsSum());
        assertEquals(500, m.cols);
        assertEquals(5000, m.rows);
    }

}

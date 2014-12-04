package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.Matrix;

import java.util.Random;

/**
 * A very naive implementation that picks about \sqrt(D) lemons at random
 */
public class DensitySolver extends AbstractSolver {

  private static final Random RAND = new Random();
  private int numBadColumns = 0;
  @Override
  public ColumnAssignment solve(Matrix m) {
    ColumnAssignment c = new ColumnAssignment(m.cols);

    numBadColumns = (int) Math.floor(Math.sqrt(m.cols));
    
    return c;
  }

}

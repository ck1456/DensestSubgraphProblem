package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.Matrix;

import java.util.Random;

/**
 * A very naive implementation that picks about \sqrt(D) lemons at random
 */
public class RandomSolver extends AbstractSolver {

  private static final Random RAND = new Random();
  @Override
  public ColumnAssignment solve(Matrix m) {
    ColumnAssignment c = new ColumnAssignment(m.cols);

    int lemons = 0;
    while(lemons < Math.floor(Math.sqrt(m.cols))){
      int col = RAND.nextInt(m.cols);
      if(!c.cols[col]){
        c.cols[col] = true;
        lemons++;
      }
    }

    return c;
  }

}

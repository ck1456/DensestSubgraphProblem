package hps.nyu.fa14;

import hps.nyu.fa14.solve.EvolutionarySolver;
import hps.nyu.fa14.solve.RandomSolver;
import hps.nyu.fa14.solve.TrivialSolver;

import java.io.File;

public class LemonFinder {

  private static ColumnAssignment solve(Matrix m, String outFile){

    // TODO: Implement this better
    ISolver solver = new EvolutionarySolver();

    ColumnAssignment c = solver.solve(m);
    return c;
  }

  public static void main(String[] args) throws Exception {
    if(args.length != 2) {
      usage();
    }
    // first parameter is input
    String inputFile = args[0];
    String outputFile = args[1];

    // Make directory for the output file if it does not exist
    File outFile = new File(outputFile);
    outFile.getAbsoluteFile().getParentFile().mkdirs();

    Matrix m = Matrix.parseFile(inputFile);
    ColumnAssignment c = solve(m, outputFile);

    c.writeFile(outputFile);
  }

  private static void usage() {
    // How to use it
    System.out.println("java -jar LemonFinder.jar <input> <output>");
    System.exit(1);
  }

}

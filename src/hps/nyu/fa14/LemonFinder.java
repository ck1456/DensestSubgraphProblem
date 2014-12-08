package hps.nyu.fa14;

import hps.nyu.fa14.solve.EvolutionarySolver;
import hps.nyu.fa14.solve.IncrementalProgressSolver;
import hps.nyu.fa14.solve.TimedSolver;

import java.io.File;

public class LemonFinder {

  private static ColumnAssignment solve(Matrix m, String outFile){

    ISolver solver = new EvolutionarySolver();
    ISolver iSolver = new IncrementalProgressSolver(solver, outFile);
    ISolver tSolver = new TimedSolver(iSolver, 117);
    
    ColumnAssignment c = tSolver.solve(m);
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

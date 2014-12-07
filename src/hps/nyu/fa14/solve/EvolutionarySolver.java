package hps.nyu.fa14.solve;

import hps.nyu.fa14.ColumnAssignment;
import hps.nyu.fa14.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class EvolutionarySolver extends AbstractSolver {
  private static final Random RAND = new Random();
  private int numBadColumns = 0;
  private int populationSize = 500;
  private int generations = 2000;
  private int numBest = 10;
  private Matrix actualMatrix;
  
  @Override
  public ColumnAssignment solve(Matrix m) {
    actualMatrix = m.clone();
    ColumnAssignment c = new ColumnAssignment(m.cols);
    numBadColumns = (int) Math.floor(Math.sqrt(m.cols));
    
    List<Assignment> oldPopulation = new ArrayList<Assignment>();
    List<Assignment> currentPopulation = new ArrayList<Assignment>();
    
    //Initial assignment
    for(int i=0;i<populationSize;i++) {
      //Matrix subMatrix = new Matrix(rCount, cCount, m.D, m.lowM, m.highM, m.lowN, m.highN, m.lowd, m.highd);
      oldPopulation.add(pickRandom(m));
    }
    
    Comparator<Assignment> cmp = new Comparator<Assignment>(){
      @Override
      public int compare(Assignment m1, Assignment m2) {
        return (int) (getDensity(getSubMatrix(m1)) - getDensity(getSubMatrix(m2)));
      }
    };
    
    Collections.sort(oldPopulation,cmp);
    
    double bestDensity = 0.0;
    Assignment bestAssignment = oldPopulation.get(0);
    
    int t = 0;
    while(t < generations) {
      currentPopulation = shuffleAndCombine(oldPopulation);
      Collections.sort(currentPopulation,cmp);
      double bestDensityOfPopulation = getDensity(getSubMatrix(currentPopulation.get(0)));
      if(bestDensity < bestDensityOfPopulation) {
        bestDensity = bestDensityOfPopulation;
        bestAssignment = currentPopulation.get(0);
      }
      //currentPopulation = mutate(currentPopulation);
      oldPopulation = new ArrayList<Assignment>(currentPopulation);
      t++;
    }
    
    c = new ColumnAssignment(bestAssignment.cols);
    
    return c;
  }
  
  private Matrix getSubMatrix(Assignment a) {
    Matrix subMatrix = new Matrix(a.rowNum,a.colNum);
    int x = 0;
    for(int i=0;i<a.rows.length;i++) {
      if(a.rows[i]) {
        int y = 0;
        for(int j=0;j<a.cols.length;j++) {
          if(a.cols[j]) {
            subMatrix.values[x][y] = actualMatrix.values[i][j];
            y++;
          }
        }
        x++;
      }
    }
    return subMatrix;
  }
  
  private Assignment pickRandom(Matrix m) {
    int diff1 = m.highM - m.lowM + 1;
    int diff2 = m.highN - m.lowN + 1;
    int rCount = RAND.nextInt(diff1);
    int cCount = RAND.nextInt(diff2);
    rCount += m.lowM;
    cCount += m.lowN;
    Assignment assignment = new Assignment(m.rows, m.cols);
    assignment.rowNum = rCount;
    assignment.colNum = cCount;
    for(int i=0;i<rCount;i++) {
      int rowNum = RAND.nextInt(m.rows);
      while(assignment.rows[rowNum]) {
        rowNum = RAND.nextInt(m.rows);
      }
      assignment.rows[rowNum] = true;
    }
    for(int i=0;i<rCount;i++) {
      int colNum = RAND.nextInt(m.cols);
      while(assignment.rows[colNum]) {
        colNum = RAND.nextInt(m.rows);
      }
      assignment.rows[colNum] = true;
    }
    return assignment;
  }
  
  private double getDensity(Matrix m) {
    double density = 0.0;
    for(int i=0;i<m.rows;i++) {
      for(int j=0;j<m.cols;j++) {
        if(m.values[i][j]) {
          density += 1;
        }
      }
    }
    density = density / (m.rows + m.cols);
    return density;
  }
  
  private boolean isColumnSumSatisfied(Matrix m) {
    int[] sums = new int[m.cols];
    for(int i=0;i<m.cols;i++) {
      for(int j=0;j<m.rows;j++) {
        if(m.values[j][i]) {
          sums[i] = sums[i] + 1;
        }
      }
    }
    boolean satisfied = true;
    for(int i=0;i<m.cols - 1;i++) {
      if(sums[i] != sums[i+1]) {
        satisfied = false;
        break;
      }
    }
    return satisfied;
  }
  
  private List<Assignment> shuffleAndCombine(List<Assignment> oldPopulation) {
    List<Assignment> currentPopulation = new ArrayList<Assignment>();
    for(int i=0;i<populationSize;i++) {
      //pick 2 random matrices to combine
      int index1 = RAND.nextInt(numBest);
      int index2 = RAND.nextInt(numBest);
      while(index1 == index2) {
        index2 = RAND.nextInt(numBest);
      }
      //combine the matrices at these 2 indices in the old population
      currentPopulation.add(combine(oldPopulation.get(index1),oldPopulation.get(index2)));
    }
    return currentPopulation;
  }
  
  private Assignment combine(Assignment m1, Assignment m2) {
    int diff1 = Math.abs(m1.rowNum - m1.rowNum + 1);
    int diff2 = Math.abs(m1.colNum - m2.colNum + 1);
    int rCount = 0;
    if(diff1 != 0) {
      rCount = RAND.nextInt(diff1);
    }
    int cCount = 0;
    if(diff2 != 0) {
      cCount = RAND.nextInt(diff2);
    }
    rCount += Math.min(m1.rowNum,m1.rowNum);
    cCount += Math.min(m1.colNum,m2.colNum);
    for(int i=0;i<rCount;i++) {
      for(int j=0;j<cCount;j++) {
      }
    }
    return m1;
  }
}

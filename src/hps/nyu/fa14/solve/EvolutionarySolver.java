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
    
    List<Matrix> oldPopulation = new ArrayList<Matrix>();
    List<Matrix> currentPopulation = new ArrayList<Matrix>();
    
    //Initial assignment
    for(int i=0;i<populationSize;i++) {
      oldPopulation.add(pickRandom(m));
    }
    
    Comparator<Matrix> cmp = new Comparator<Matrix>(){
      @Override
      public int compare(Matrix m1, Matrix m2) {
        return (int) (getDensity(m1) - getDensity(m2));
      }
    };
    
    Collections.sort(oldPopulation,cmp);
    
    double bestDensity = 0.0;
    Matrix bestMatrix = m.clone();
    
    int t = 0;
    while(t < generations) {
      currentPopulation = shuffleAndCombine(oldPopulation);
      Collections.sort(currentPopulation,cmp);
      double bestDensityOfPopulation = getDensity(currentPopulation.get(0));
      if(bestDensity < bestDensityOfPopulation) {
        bestDensity = bestDensityOfPopulation;
        bestMatrix = currentPopulation.get(0).clone();
      }
      //currentPopulation = mutate(currentPopulation);
      oldPopulation = new ArrayList<Matrix>(currentPopulation);
      t++;
    }
    
    
    
    return c;
  }
  
  private Matrix pickRandom(Matrix m) {
    int diff1 = m.highM - m.lowM + 1;
    int diff2 = m.highN - m.lowN + 1;
    int rCount = RAND.nextInt(diff1);
    int cCount = RAND.nextInt(diff2);
    rCount += m.lowM;
    cCount += m.lowN;
    Matrix subMatrix = new Matrix(rCount, cCount, m.D, m.lowM, m.highM, m.lowN, m.highN, m.lowd, m.highd);
    List<Integer> rowList = new ArrayList<Integer>();
    for(int i=0;i<rCount;i++) {
      int rowNum = RAND.nextInt(m.rows);
      if(!rowList.contains(rowNum)) {
        rowList.add(rowNum);
      }
    }
    List<Integer> colList = new ArrayList<Integer>();
    for(int i=0;i<rCount;i++) {
      int colNum = RAND.nextInt(m.cols);
      if(!colList.contains(colNum)) {
        colList.add(colNum);
      }
    }
    for(int x=0;x<rCount;x++) {
      for(int y=0;y<cCount;y++) {
        subMatrix.values[rowList.get(x)][colList.get(y)] = m.values[x][y];
      }
    }
    return subMatrix;
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
  
  private List<Matrix> shuffleAndCombine(List<Matrix> oldPopulation) {
    List<Matrix> currentPopulation = new ArrayList<Matrix>();
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
  
  private Matrix combine(Matrix m1, Matrix m2) {
    /*int diff1 = m1.highM - m1.lowM + 1;
    int diff2 = m1.highN - m1.lowN + 1;
    int rCount = RAND.nextInt(diff1);
    int cCount = RAND.nextInt(diff2);
    rCount += m1.lowM;
    cCount += m1.lowN;
    List<Integer> rowList1 = new ArrayList<Integer>();
    List<Integer> rowList2 = new ArrayList<Integer>();
    Matrix m = new Matrix(rCount, cCount, m1.D, m1.lowM, m1.highM, m1.lowN, m1.highN, m1.lowd, m1.highd);
    for(int i=0;i<rCount;i++) {
      for(int j=0;j<cCount;j++) {
        if(RAND.nextBoolean()) {
          int rowNum = RAND.nextInt(m1.rows);
          if(!rowList1.contains(rowNum)) {
            rowList1.add(rowNum);
          }
        }
        else {
          int rowNum = RAND.nextInt(m2.rows);
          if(!rowList2.contains(rowNum)) {
            rowList2.add(rowNum);
          }
        }
      }
    }*/
    return m1.clone();
  }
}

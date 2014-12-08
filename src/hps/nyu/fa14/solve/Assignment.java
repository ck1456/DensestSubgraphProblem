package hps.nyu.fa14.solve;

import hps.nyu.fa14.Matrix;

public class Assignment {
  public boolean[] cols;
  public boolean[] rows;
  public int rowNum = 0;
  public int colNum = 0;
  public double density = 0.0;
  
  public Assignment(int numRows, int numCols){
    rows = new boolean[numRows];
    cols = new boolean[numCols];
  }
  
  public Matrix getSubMatrix(Matrix m) {
    Matrix subMatrix = new Matrix(rowNum,colNum);
    int x = 0;
    for(int i=0;i<rows.length;i++) {
      if(rows[i]) {
        int y = 0;
        for(int j=0;j<cols.length;j++) {
          if(cols[j]) {
            subMatrix.values[x][y] = m.values[i][j];
            y++;
          }
        }
        x++;
      }
    }
    return subMatrix;
  }
}

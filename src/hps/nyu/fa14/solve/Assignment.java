package hps.nyu.fa14.solve;

public class Assignment {
  public boolean[] cols;
  public boolean[] rows;
  public int rowNum;
  public int colNum;
  
  public Assignment(int numRows, int numCols){
    rows = new boolean[numRows];
    cols = new boolean[numCols];
  }
}

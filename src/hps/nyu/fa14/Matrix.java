package hps.nyu.fa14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matrix {

    public final int rows;
    public final int cols;

    public final boolean[][] values;

    public Matrix(int rCount, int cCount) {
        rows = rCount;
        cols = cCount;
        values = new boolean[rows][cols];
    }

    public Matrix transpose() {
      Matrix m = new Matrix(cols,rows);
      for(int i=0;i<cols;i++) {
        for(int j=0;j<rows;j++) {
          m.values[i][j] = this.values[j][i];
        }
      }
      return m;
    }
    
    public int[][] product(Matrix t) {
      int [][] prod = new int[rows][rows];
      for(int i=0;i<this.rows;i++) {
        for(int j=0;j<t.cols;j++) {
          for(int k=0;k<this.cols;k++) {
            prod[i][j] += ((this.values[i][k] & t.values[k][j]) ? 1 : 0);
          }
        }
      }
      return prod;
    }
    
    /**
     * Returns the value that each column sums to.
     * If the columns do not all sum to the same value, throws an exception
     * @return
     */
    public int getAllColumnsSum(){
        int colSum = columnSum(0);
        for(int c = 0; c < cols; c++){
            if(colSum != columnSum(c)){
                throw new RuntimeException("Column sums not equal");
            }
        }
        return colSum;
    }
    
    public int columnSum(int col) {
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            sum += (values[i][col] ? 1 : 0);
        }
        return sum;
    }

    public int rowSum(int row) {
        int sum = 0;
        for (int i = 0; i < cols; i++) {
            sum += (values[row][i] ? 1 : 0);
        }
        return sum;
    }

    // Probably not needed
    /*
    public int correlation() {
      int corr = 0;
      Matrix t = this.transpose();
      int [][] S = this.product(t);
      for(int i=0;i<this.rows;i++) {
        for(int j=0;j<this.rows;j++) {
          if(i != j) {
            corr += (S[i][j] * S[i][j]);
          }
        }
      }
      return corr;
    }
    public int correlation2() {
        int[][] S = getS();
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (i != j) {
                    sum += S[i][j] * S[i][j];
                }
            }
        }
        return sum;
    }

    private int[][] getS() {
        int m = rows;
        int n = cols;
        int[][] S = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (values[i][k] && values[j][k]) {
                        S[i][j]++;
                    }
                }
            }
        }
        return S;
    }
     */
    
    /**
     * Creates a deep copy of the matrix
     */
    public Matrix clone(){
        Matrix m = new Matrix(rows, cols);
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                m.values[r][c] = this.values[r][c];
            }
        }
        return m;
    }
    
    @Override
    public boolean equals(Object other){
        if(this == other){
            return true; // same object
        }
        if(!(other instanceof Matrix)){
            return false; // incompatible types
        }
        
        Matrix that = (Matrix)other;
        if(this.rows != that.rows
                || this.cols != that.cols){
            return false; // different sizes
        }
        
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(this.values[r][c] != that.values[r][c]){
                    return false; // Don't match in all points
                }
            }
        }
        return true; // otherwise all the same
    }

    @Override
    public int hashCode(){
        final int multiplier = 37; // prime
        int hash = (rows * multiplier) + cols;

        for(int r = 0; r < rows; r++){
            hash = (hash * multiplier) + rowSum(r);
        }
        return hash;
    }
    
    // Need to supply the graphs here
    public static Matrix parse(InputStream input) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        // first line is M N
        String[] dims = br.readLine().trim().split("\\s");
        int M = Integer.parseInt(dims[0]);
        int N = Integer.parseInt(dims[1]);
        Matrix m = new Matrix(M, N);
        
        String line = null;
        for(int i = 0; i < M; i++){
            line = br.readLine();
            String[] vals = line.split("\\s");
            for(int j = 0; j < N; j++){
                m.values[i][j] = (Integer.parseInt(vals[j]) > 0);
            }
        }
        return m;
    }
    
    public static Matrix parseFile(String filePath) throws IOException {
        return parse(new FileInputStream(new File(filePath)));
    }
    
    /*
    private void write(BufferedWriter writer) throws IOException {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                writer.write(String.format("%d ", values[r][c] ? 1 : 0));
            }
        }
        writer.newLine();
    }
    
    public void write(OutputStream output) throws IOException {
        Matrix.write(output, Arrays.asList(this));
    }

    public static void write(OutputStream output, List<Matrix> matrices)
            throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));
        for (Matrix m : matrices) {
            m.write(bw);
        }
        bw.close();
    }
    */
    
    private static final Random RAND = new Random();
    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows, cols);
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                m.values[r][c] = RAND.nextBoolean();
            }
        }
        return m;
    }
}

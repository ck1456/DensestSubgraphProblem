package hps.nyu.fa14;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Encapsulates which columns we think are lemons
 */
public class ColumnAssignment {

    public boolean[] cols;
    
    public ColumnAssignment(int numCols){
        cols = new boolean[numCols];
    }
    
    public ColumnAssignment(boolean [] columns) {
      cols = new boolean[columns.length];
      for(int i=0;i<columns.length;i++) {
        cols[i] = columns[i];
      }
    }
    
    /**
     * Returns the number of flagged columns
     */
    public int lemonCount(){
        int count = 0;
        for(int i = 0; i < cols.length; i++){
            if(cols[i]){
                count++;
            }
        }
        return count;
    }
    
    public ColumnAssignment clone(){
        // Copy over the specified columns;
        ColumnAssignment c = new ColumnAssignment(cols.length);
        for(int i = 0; i < cols.length; i++){
            c.cols[i] = cols[i];
        }
        return c;
    }
    
    
    // Write the partition map
    public void write(OutputStream output) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));
        // Write out all of the cluster centers
        for(int i = 0; i < cols.length; i++) {
            bw.write(String.format("%d ", (cols[i] ? 1 : 0)));
        }
        bw.close();
    }

    public void writeFile(String filePath) throws IOException {
        write(new FileOutputStream(new File(filePath)));
    }
}

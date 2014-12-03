package hps.nyu.fa14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DensityUtil {

    private final Matrix m;

    public DensityUtil(Matrix m) {
        this.m = m;
    }

    // A supernaive implementation that assumes little-n ~ \sqrt(big-N)
    public double bestDensity(ColumnAssignment a){
        return density(a, (int)Math.sqrt(m.rows));
    }
    
    
    public double density(ColumnAssignment a, int rows) {
        // rows is our best guess at little-n
        
        // columnCount is our specified little-m
        int columnCount = a.lemonCount();
        
        List<WeightedRow> assetWeights = new ArrayList<WeightedRow>();
        for(int r = 0; r < m.rows; r++) {
            WeightedRow sr = new WeightedRow();
            sr.row = r;
            for(int c = 0; c < a.cols.length; c++) {
                if(a.cols[c] && m.values[r][c]){
                    sr.weight++;
                }
            }
            assetWeights.add(sr);
        }

        // for the assigned columns, sort by assigned count (descending)
        Collections.sort(assetWeights, WeightedRow.SORT_BY_WEIGHT);
        
        // sum the first r rows
        int sum = 0;
        for(int i = 0; i < rows; i++){
            sum += assetWeights.get(i).weight;
        }

        double density = sum / (1.0 * rows * columnCount);

        return density;
    }

    private static class WeightedRow {
        int row;
        int weight;
        
        public static Comparator<WeightedRow> SORT_BY_WEIGHT = new Comparator<WeightedRow>() {
            @Override
            public int compare(WeightedRow o1, WeightedRow o2) {
                return (int)Math.signum(o2.weight - o1.weight);
            }
        };
    }
}

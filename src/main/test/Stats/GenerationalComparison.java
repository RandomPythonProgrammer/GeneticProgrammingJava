package Stats;

import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;
import java.util.Arrays;

public class GenerationalComparison {
    public static void main(String[] args) {
        File heapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File rheapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\rheap_tree");

        Csv[] heaps = Arrays.stream(heapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] rheaps = Arrays.stream(rheapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        double hs = 0;

        double ls = 0;

        for (int i = 0; i <= 1000; i++) {
            for (Csv heap: heaps) {
                if (i+1 < heap.getRows()) {
                    hs++;
                }
            }


            for (Csv rheap: rheaps) {
                if (i+1 < rheap.getRows()) {
                    ls++;
                }
            }
        }

        System.out.println(ls/hs);

    }
}

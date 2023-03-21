import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;
import java.util.Arrays;

public class GenerationalComparison {
    public static void main(String[] args) {
        File heapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File rheapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\rheap_tree");

        Graph graph = new Graph()
                .setTitle("Comparison Of Heap And RHeap Trees")
                .setXAxis("Generation")
                .setYAxis("Unfinished Trials")
                .addLegend("Heap Tree")
                .addLegend("RHeap Tree")
                .setFontSize(25)
                .setSize(1250, 1000);

        Csv[] heaps = Arrays.stream(heapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] rheaps = Arrays.stream(rheapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        for (int i = 0; i <= 1000; i++) {
            int samples = 0;
            for (Csv heap: heaps) {
                if (i+1 < heap.getRows()) {
                    samples++;
                }
            }

            graph.addPoint(0, i, samples);

            samples = 0;

            for (Csv rheap: rheaps) {
                if (i+1 < rheap.getRows()) {
                    samples++;
                }
            }

            graph.addPoint(1, i, samples);
        }

        graph.save("C:\\Users\\Joshua\\Documents\\research_data\\graphs\\RComparisonOrganisms.png");
    }
}

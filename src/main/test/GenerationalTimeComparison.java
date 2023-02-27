import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;
import java.util.Arrays;

public class GenerationalTimeComparison {
    public static void main(String[] args) {
        File heapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File linkedData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\linked_tree");

        Graph graph = new Graph()
                .setTitle("Comparison Of Heap And Linked Trees")
                .setXAxis("Generation")
                .setYAxis("Generational Time Difference (ms)")
                .addLegend("Heap Tree")
                .addLegend("Linked Tree")
                .setFontSize(25)
                .setPrecision(2)
                .setSize(1250, 1000);

        Csv[] heaps = Arrays.stream(heapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] links = Arrays.stream(linkedData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        for (int i = 2; i <= 1000; i++) {
            double sum = 0;
            int samples = 0;
            for (Csv heap: heaps) {
                if (i < heap.getRows()) {
                    sum += Double.parseDouble(heap.get(i).get(4)) - Double.parseDouble(heap.get(i - 1).get(4));
                    samples++;
                }
            }

            graph.addPoint(0, i, sum/samples);

            sum = 0;
            samples = 0;

            for (Csv link: links) {
                if (i < link.getRows()) {
                    sum += Double.parseDouble(link.get(i).get(4)) - Double.parseDouble(link.get(i - 1).get(4));
                    samples++;
                }
            }

            graph.addPoint(1, i, sum/samples);
        }

        graph.save("C:\\Users\\Joshua\\Documents\\research_data\\graphs\\GenerationalTimeComparison.png");
    }
}

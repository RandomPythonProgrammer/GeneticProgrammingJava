import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;

public class ComparisonData {
    public static void main(String[] args) {
        File heapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File linkedData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\linked_tree");

        Graph timeGraph = new Graph()
                .setYAxis("Time (ms)");
//                .set

        File[] heaps = heapData.listFiles();
        for (File heap: heaps) {
            Csv csv = new Csv();
            csv.parse(heap);
            int generations = Integer.parseInt(csv.get(csv.getRows()-1).get(0));
            int time = Integer.parseInt(csv.get(csv.getRows()-1).get(4));

        }
    }
}

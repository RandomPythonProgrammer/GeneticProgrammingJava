import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;
import java.util.List;

public class CsvGraph {
    public static void main(String[] args) {
        File dataFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File[] files = dataFolder.listFiles();
        for (int i = 0; i < 1; i++) {
            Csv csv = new Csv();
            csv.parse(files[i]);
            Graph graph = csv
                    .toGraph(0, List.of(2, 3))
                    .setRenderLines(true).setFontSize(20)
                    .setPrecision(2)
                    .setSize(1000, 750)
                    .setTitle("Heap Tree Training Data")
                    .setYAxis("Accuracy");
            graph.save(String.format("C:\\Users\\Joshua\\Documents\\research_data\\graphs\\Heap_Tree_Train_%d.png", i+1));
        }
    }
}

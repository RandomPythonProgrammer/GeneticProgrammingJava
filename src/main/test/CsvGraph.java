import com.jchen.csv.Csv;
import com.jchen.graph.Graph;
import com.jchen.stats.Stats;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvGraph {
    public static void main(String[] args) {
        File heapFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File rheapFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\rheap_tree");

        Csv[] heapFiles = Arrays.stream(heapFolder.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] rheapFiles = Arrays.stream(rheapFolder.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        Graph graph = new Graph()
                .setRenderPoints(true)
                .setFontSize(20)
                .setPrecision(2)
                .setSize(1000, 750)
                .setTitle("Heap Tree Standard Deviation")
                .setYAxis("Standard Deviation")
                .addLegend("Heap Tree")
                .addLegend("RHeap Tree");
        for (int i = 0; i < 1000; i++) {
            ArrayList<Double> accuracies = new ArrayList<>();
            for (Csv csv: heapFiles)
                if (csv.getRows() > i+1)
                    accuracies.add(Double.parseDouble(csv.get(i+1).get(2)));
            graph.addPoint(0, i, Stats.std(accuracies));

            accuracies.clear();

            for (Csv csv: rheapFiles)
                if (csv.getRows() > i+1)
                    accuracies.add(Double.parseDouble(csv.get(i+1).get(2)));
            graph.addPoint(1, i, Stats.std(accuracies));
        }

        graph.save("C:\\Users\\Joshua\\Documents\\research_data\\graphs\\heapstd.png");
    }
}

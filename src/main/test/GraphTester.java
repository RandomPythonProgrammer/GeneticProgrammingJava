import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.util.List;

public class GraphTester {
    public static void main(String[] args) {
        Graph graph = new Graph()
                .setRenderLines(true)
                .setTitle("Performance Over Time")
                .setXAxis("Generation")
                .setYAxis("Accuracy")
                .setFontSize(15)
                .setVLines(10)
                .addLegend("Best")
                .addLegend("Average");
        Csv csv = new Csv();
        csv.parse("C:\\Users\\Joshua\\Desktop\\Linked_Tree_Train_1.csv");
        for (int i = 1; i < csv.getRows(); i++) {
            List<String> row = csv.get(i);
            for (int j = 0; j < row.size(); j++) {
                graph.addPoint(0, Double.parseDouble(row.get(0)), Double.parseDouble(row.get(2)));
                graph.addPoint(1, Double.parseDouble(row.get(0)), Double.parseDouble(row.get(3)));
            }
        }
        graph.save("C:\\Users\\Joshua\\Desktop\\TestGraph.png");
    }
}

import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.util.List;

public class GraphTester {
    public static void main(String[] args) {
        Graph graph = new Graph(750, 500);
        graph.setRenderLines(true).setTitle("Performance Over Time").setxAxis("Generation").setyAxis("Accuracy").setFontSize(15);
        Csv csv = new Csv();
        csv.parse("C:\\Users\\JC200\\Desktop\\Heap_Tree_Train_67.csv");
        for (int i = 1; i < csv.getRows(); i++) {
            List<String> row = csv.get(i);
            for (int j = 0; j < row.size(); j++) {
                graph.addPoint(0, Double.parseDouble(row.get(0)), Double.parseDouble(row.get(2)));
                graph.addPoint(1, Double.parseDouble(row.get(0)), Double.parseDouble(row.get(3)));
            }
        }
        graph.save("C:\\Users\\JC200\\Desktop\\TestGraph.png");
    }
}

import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.util.List;

public class CsvGraphTest {
    public static void main(String[] args) {
        Csv csv = new Csv();
        csv.parse("C:\\Users\\Joshua\\Desktop\\Linked_Tree_Train_1.csv");
        Graph graph = csv.toGraph(0, List.of(2, 3));
        graph.setRenderLines(true).setFontSize(20).setPrecision(2).setSize(1000, 750);
        graph.save("C:\\Users\\Joshua\\Desktop\\Linked_Tree_Train_1.png");
    }
}

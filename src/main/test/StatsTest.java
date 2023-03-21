import com.jchen.csv.Csv;
import com.jchen.stats.Stats;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class StatsTest {
    public static void main(String[] args) {
        File heap_treeFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File linked_treeFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\linked_tree");
        Csv[] heap_trees = Arrays.stream(heap_treeFolder.listFiles()).map((file -> new Csv().parse(file))).toArray(Csv[]::new);
        Csv[] linked_trees = Arrays.stream(linked_treeFolder.listFiles()).map((file -> new Csv().parse(file))).toArray(Csv[]::new);
        double v1 = 0;
        double v2 = 0;
        for (int i = 0; i < 1000; i++) {
            ArrayList<Double> heaps = new ArrayList<>();
            ArrayList<Double> links = new ArrayList<>();

            for (Csv heapTree: heap_trees) {
                if (heapTree.getRows() > i+1) {
                    heaps.add(Double.parseDouble(heapTree.get(i+1).get(2)));
                }
            }

            for (Csv linkTree: linked_trees) {
                if (linkTree.getRows() > i+1) {
                    links.add(Double.parseDouble(linkTree.get(i+1).get(2)));
                }
            }
            System.out.println(Stats.wtTest(links, heaps));
        }
    }
}

import com.jchen.csv.Csv;
import com.jchen.geneticprogramming.tree.HeapTree;
import com.jchen.geneticprogramming.tree.LinkedTree;

import java.util.List;


public class EvaluationTest {
    public static void main(String[] args) {
        Csv csv = new Csv();
        csv.addLine(List.of(
                "Linked Tree Node Count",
                "Heap Tree Node Count",
                "Linked Tree Scaled Time (ns)",
                "Heap Tree Scaled Time (ns)",
                "Linked Tree / Heap Tree Ratio:"
        ));

        for (int i = 0; i < 100; i++) {
            //Testing the LinkedTree
            LinkedTree linkedTree = new LinkedTree(true);
            long linkedTreeStart = System.nanoTime();
            int linkedTreeResult = linkedTree.evaluate(0);
            long linkedTreeTime = System.nanoTime() - linkedTreeStart;
            double linkedTreeScaledTime = ((double) linkedTreeTime) / linkedTree.getNodeCount();

            //Testing the HeapTree
            HeapTree heapTree = new HeapTree(true);
            long heapTreeStart = System.nanoTime();
            int heapTreeResult = heapTree.evaluate(0);
            long heapTreeTime = System.nanoTime() - heapTreeStart;
            double heapTreeScaledTime = ((double) heapTreeTime) / heapTree.getNodeCount();

            csv.addLine(List.of(
                    String.valueOf(linkedTree.getNodeCount()),
                    String.valueOf(heapTree.getNodeCount()),
                    String.valueOf(linkedTreeScaledTime),
                    String.valueOf(heapTreeScaledTime),
                    String.valueOf(linkedTreeScaledTime / heapTreeScaledTime)
            ));
        }

        csv.write("C:\\Users\\JC200\\Desktop\\Evaluation_Data.csv");
        System.out.println("Finished Data Collection");
    }
}

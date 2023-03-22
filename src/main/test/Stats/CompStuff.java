package Stats;

import com.jchen.csv.Csv;

import java.io.File;
import java.util.Arrays;

public class CompStuff {
    public static void main(String[] args) {
        File heapTreeFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File linkedTreeFolder = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\linked_tree");
        Csv[] heapTrees = Arrays.stream(heapTreeFolder.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] linkedTrees = Arrays.stream(linkedTreeFolder.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        double heapTreeTotal = 0;
        int heapTreeSamples = 0;
        double linkedTreeTotal = 0;
        int linkedTreeSamples = 0;

        for (int i = 1; i <= 1000; i++) {
            for (Csv csv: heapTrees) {
                if (csv.getRows() > i) {
                    heapTreeSamples++;
                    heapTreeTotal += Double.parseDouble(csv.get(i, 2));
                }
            }

            for (Csv csv: linkedTrees) {
                if (csv.getRows() > i) {
                    linkedTreeSamples++;
                    linkedTreeTotal += Double.parseDouble(csv.get(i, 2));
                }
            }
        }

        double heapTreeAverage = heapTreeTotal/heapTreeSamples;
        double linkedTreeAverage = linkedTreeTotal/linkedTreeSamples;
        System.out.println(((heapTreeAverage - linkedTreeAverage)/linkedTreeAverage) * 100);

    }
}

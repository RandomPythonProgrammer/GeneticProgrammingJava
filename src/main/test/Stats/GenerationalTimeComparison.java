package Stats;

import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;
import java.util.Arrays;

public class GenerationalTimeComparison {
    public static void main(String[] args) {
        File heapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File linkedData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\linked_tree");


        Csv[] heaps = Arrays.stream(heapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] links = Arrays.stream(linkedData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        double heapSum = 0;
        double heapSamples = 0;

        double linkedSum = 0;
        double linkedSamples = 0;

        for (int i = 2; i <= 1000; i++) {
            for (Csv heap: heaps) {
                if (i < heap.getRows()) {
                    heapSum += Double.parseDouble(heap.get(i).get(4)) - Double.parseDouble(heap.get(i - 1).get(4));
                    heapSamples++;
                }
            }

            for (Csv link: links) {
                if (i < link.getRows()) {
                    linkedSum += Double.parseDouble(link.get(i).get(4)) - Double.parseDouble(link.get(i - 1).get(4));
                    linkedSamples++;
                }
            }
        }

        double heapAverage = heapSum/heapSamples;
        double linkedAverage = linkedSum/linkedSamples;

        System.out.println(((heapAverage - linkedAverage)/linkedAverage) * 100);
    }
}

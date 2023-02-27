import com.jchen.csv.Csv;
import com.jchen.graph.Graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

public class TimeComparison {
    public static void main(String[] args) {
        File heapData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\heap_tree");
        File linkedData = new File("C:\\Users\\Joshua\\Documents\\research_data\\training_data\\linked_tree");

        Graph graph = new Graph()
                .setTitle("Comparison Of Heap And Linked Trees")
                .setXAxis("Time (ms)")
                .setYAxis("Accuracy")
                .addLegend("Heap Tree")
                .addLegend("Linked Tree")
                .setFontSize(25)
                .setVLines(25)
                .setSize(4000, 1000);

        Csv[] heaps = Arrays.stream(heapData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);
        Csv[] links = Arrays.stream(linkedData.listFiles()).map((file) -> new Csv().parse(file)).toArray(Csv[]::new);

        ExecutorService executorService = Executors.newFixedThreadPool(16);
        BiConsumer<Long, Long> task = (start, end) -> {
            int lastHeap = 0;
            int lastLink = 0;
            for (long i = start; i < end; i++) {
                double sum = 0;
                int samples = 0;
                for (Csv heap : heaps) {
                    for (int r = lastHeap + 1; r < heap.getRows(); r++) {
                        List<String> row = heap.get(r);
                        int time = Integer.parseInt(row.get(4));
                        if (time == i) {
                            sum += Double.parseDouble(heap.get(r).get(2));
                            lastHeap = r;
                            samples++;
                            break;
                        } else if (time > i) {
                            break;
                        }
                    }
                }

                if (samples > 0) {
                    graph.addPoint(0, i, sum / samples);
                }

                sum = 0;
                samples = 0;

                for (Csv link : links) {
                    for (int r = lastLink + 1; r < link.getRows(); r++) {
                        List<String> row = link.get(r);
                        int time = Integer.parseInt(row.get(4));
                        if (time == i) {
                            sum += Double.parseDouble(link.get(r).get(2));
                            lastLink = r;
                            samples++;
                            break;
                        } else if (time > i) {
                            break;
                        }
                    }
                }

                if (samples > 0) {
                    graph.addPoint(1, i, sum / samples);
                }
            }
        };
        final long block = (long) 2e4;
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            final int index = i;
            futures.add(executorService.submit(() -> task.accept(index * block, (index+1)*block)));
        }
        for (Future<?> future: futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        graph.save("C:\\Users\\Joshua\\Documents\\research_data\\graphs\\TimeComparison.png");
    }
}

import com.jchen.geneticprogramming.algorithm.GeneticOperations;
import com.jchen.geneticprogramming.tree.RHeapTree;
import com.jchen.geneticprogramming.tree.Tree;

import java.util.Map;

public class RHeapTreeTrainTest {
    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            Map<Tree, Double> organisms = GeneticOperations.train(
                    1000,
                    RHeapTree.class,
                    (x) -> 10 & x | 4,
                    () -> (int) (Math.random() * 10),
                    100,
                    2,
                    null
            );
        }
    }
}

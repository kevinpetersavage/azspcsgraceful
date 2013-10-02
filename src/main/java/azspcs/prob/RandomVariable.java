package azspcs.prob;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class RandomVariable {
    private final Random random;
    private final List<Integer> distribution;

    public RandomVariable(int to, Random random) {
        this.random = random;
        this.distribution = Lists.newArrayList();
        for (int i = 0; i <= to; i++){
            for (int j = 0; j < 10; j++){
                distribution.add(i);
            }
        }
        Collections.sort(distribution);
    }

    public int get(){
        return distribution.get(random.nextInt(distribution.size()));
    }

    public void boost(int i, int score){
        for (int j = 0; j < score; j++){
            distribution.add(i);
        }
        Collections.sort(distribution);
    }
}

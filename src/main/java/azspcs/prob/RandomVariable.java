package azspcs.prob;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class RandomVariable {
    private final Random random;
    private final List<Integer> distribution;

    private Integer lastReturnValue;

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
        lastReturnValue = distribution.get(random.nextInt(distribution.size()));
        return lastReturnValue;
    }

    public void boost(){
        if (lastReturnValue != null){
            boost(lastReturnValue, 1);
        }
    }

    public void boost(int i, int score){
        for (int j = 0; j < score; j++){
            distribution.add(i);
        }
        Collections.sort(distribution);
    }

    @Override
    public String toString() {
        double[] doubleArray = toDoubleArray(distribution);
        DescriptiveStatistics stats = new DescriptiveStatistics(doubleArray);
        return String.format("[avg:%.3f,std:%.3f,size:%d]", stats.getMean(), stats.getStandardDeviation(), distribution.size());
    }

    private double[] toDoubleArray(List<Integer> distribution) {
        double[] doubleArray = new double[distribution.size()];
        for (int i = 0; i < doubleArray.length; i++){
            doubleArray[i] = distribution.get(i);
        }
        return  doubleArray;
    }
}

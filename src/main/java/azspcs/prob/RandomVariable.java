package azspcs.prob;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class RandomVariable implements Variable {
    private final Random random;
    private final List<Integer> distribution;

    private Integer lastReturnValue;

    public RandomVariable(int from, int to, Random random) {
        this.random = random;
        this.distribution = Lists.newArrayList();
        for (int i = from; i <= to; i++){
            for (int j = 0; j < 5; j++){
                distribution.add(i);
            }
        }
        Collections.sort(distribution);
    }

    @Override
    public int get(){
        lastReturnValue = distribution.get(random.nextInt(distribution.size()));
        return lastReturnValue;
    }

    @Override
    public void boost(int score){
        if (lastReturnValue != null){
            boost(lastReturnValue, score);
        }
    }

    @Override
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

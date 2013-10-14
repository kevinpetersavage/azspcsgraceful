package azspcs.prob;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RandomVariableTest {
    @Test
    public void randomness(){
        Variable variable = new RandomVariable(0, 2, new Random());
        Set<Integer> results = Sets.newHashSet();
        for(int i = 0; i<100; i++){
            results.add(variable.get());
        }
        assertThat(results).isEqualTo(Sets.newHashSet(0,1,2));
    }

    @Test
    public void boost(){
        Variable variable = new RandomVariable(0, 2, new Random());
        variable.boost(2, 1000);

        int twos = 0;
        int others = 0;
        for(int i = 0; i<100; i++){
            if (variable.get()>1){
                twos++;
            } else {
                others++;
            }
        }

        assertThat(twos).isGreaterThan(others);
    }

    @Test
    public void toStringReturnsDescriptives(){
        String string = new RandomVariable(0, 10, new Random()).toString();
        assertThat(string).isEqualTo("[avg:5.000,std:3.191,size:55]");

    }
}

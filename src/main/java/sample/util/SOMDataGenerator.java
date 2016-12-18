package sample.util;

import java.io.File;

/**
 * Created by Szymon on 24.11.2016.
 */
public class SOMDataGenerator extends DataGenerator {
    public SOMDataGenerator(File savefile){
        this.saveFile=savefile;
        for (int i = 0; i < 16; i++) {
            final int dir=i;
            expressions.add(subProblem->{
                DataSet set=new DataSet(16);
                double distance=0.3*random.nextDouble()+0.15;
                for (int j = 0; j < set.inputs.length; j++) {
                    double signal=Math.exp(-Math.abs((16+j-dir)%16))*(1.-(distance+random.nextDouble()*.05));
                    set.inputs[j]=signal;
                }
                return set;
            });
        }
    }
}

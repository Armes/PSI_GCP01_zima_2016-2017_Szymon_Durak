package sample.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Szymon on 10.11.2016.
 */
public class TunnelDataGenerator extends DataGenerator {
    public TunnelDataGenerator(File saveFile) {
        this.saveFile=saveFile;
        expressions.add(subProblem->{
            DataSet set=new DataSet(17);
            double angleStep=Math.PI/8.;
            double tunnelSpan=0.4+random.nextDouble()*.3;
            double tunnelTurn=-1. + 2.*random.nextDouble();
            set.inputs[16]=tunnelTurn;
            tunnelTurn*=Math.PI*90./180.;
            for (int i = 0; i < set.inputs.length - 1; i++) {
                double signal=tunnelSpan/(Math.abs(Math.sin(angleStep*(double)i - tunnelTurn))+1e-6);
                set.inputs[i]=signal>1.?0.:(1.-signal);
            }
            return set;
        });
    }
}

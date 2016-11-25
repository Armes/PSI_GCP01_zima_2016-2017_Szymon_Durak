package sample.util;

import java.io.File;

/**
 * Created by Szymon on 24.11.2016.
 */
public class GroupingDataGenerator extends DataGenerator {
    public GroupingDataGenerator(File savefile){
        this.saveFile=savefile;
        expressions.add(subProblem->{
            DataSet set=new DataSet(19);
            double angleStep=Math.PI/8.;
            double tunnelSpan=0.4+random.nextDouble()*.3;
            double tunnelTurn=-1. + 2.*random.nextDouble();
            set.inputs[16]=1.;
            set.inputs[17]=-1.;
            set.inputs[18]=-1.;
            tunnelTurn*=Math.PI*10./180.;
            for (int i = 0; i < set.inputs.length - 1; i++) {
                double signal=tunnelSpan/(Math.abs(Math.sin(angleStep*(double)i - tunnelTurn))+1e-6);
                set.inputs[i]=signal>1.?0.:(1.-signal);
            }
            return set;
        });
        expressions.add(subProblem->{
            DataSet set=new DataSet(19);
            double angleStep=Math.PI/8.;
            double wallDistance=0.4+random.nextDouble()*.3;
            double wallOrientation=1.-2.*random.nextDouble();
            set.inputs[16]=-1.;
            set.inputs[17]=1.;
            set.inputs[18]=-1.;
            wallOrientation*=angleStep;
            for (int i = 0; i < set.inputs.length - 1; i++) {
                double sin=Math.sin(angleStep*(double)i - wallOrientation);
                double signal=wallDistance/(Math.abs(Math.sin(angleStep*(double)i - wallOrientation))+1e-6);
                set.inputs[i]=signal>1.||sin<0?0.:(1.-signal);
            }
            return set;
        });
        expressions.add(subProblem->{
            DataSet set=new DataSet(19);
            double angleStep=Math.PI/8.;
            double wallDistance=0.4+random.nextDouble()*.3;
            double wallOrientation=-2.*random.nextDouble();
            set.inputs[16]=-1.;
            set.inputs[17]=1.;
            set.inputs[18]=-1.;
            wallOrientation*=1.*angleStep;
            wallOrientation+=Math.PI;
            for (int i = 0; i < set.inputs.length - 1; i++) {
                double sin=Math.sin(angleStep*(double)i - wallOrientation);
                double signal=wallDistance/(Math.abs(Math.sin(angleStep*(double)i - wallOrientation))+1e-6);
                set.inputs[i]=signal>1.||sin<0?0.:(1.-signal);
            }
            return set;
        });
        //expressions.add(subProblem->{
        //    DataSet set=new DataSet(19);
        //    double angleStep=Math.PI/8.;
        //    double enemyDistance=0.4+random.nextDouble()*.3;
        //    double enemyOrientation=-2.*random.nextDouble();
        //    set.inputs[16]=-1.;
        //    set.inputs[17]=-1.;
        //    set.inputs[18]=1.;
        //    enemyOrientation*=1.5*angleStep;
        //    for (int i = 0; i < set.inputs.length - 1; i++) {
        //        double signal=enemyDistance/(Math.abs(Math.sin(angleStep*(double)i - enemyOrientation))+1e-6);
        //        set.inputs[i]=signal>1.||Math.abs(enemyOrientation-(double)i*angleStep)>=angleStep?0.:(1.-signal);
        //    }
        //    return set;
        //});
    }
}

package sample.util;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Created by Szymon on 06.10.2016.
 */
public class DataGenerator {
    File saveFile;
    private List<Function<Boolean,DataSet>> expressions; //Function that accepts single Double parameter and returns 3 Double coordinates.
    private Random random;
    Double AND(Double a,Double b)
    {
        return a*b;
    }
    Double OR(Double a,Double b)
    {
        Double result=a+b;
        return result>1.?1.:result<0.?0.:result;
    }
    Double XOR(Double a,Double b)
    {
        return Math.abs(a-b);
    }
    Double NOT(Double a)
    {
        return 1.-a;
    }
    Double EQ(Double a,Double b)
    {
        return 1.-XOR(a,b);
    }
    public DataGenerator(File saveFile) {
        random=new Random();
        this.saveFile=saveFile;
        expressions = new ArrayList<>();
        expressions.add(subProblem->{
            DataSet set=new DataSet(8,2);
            Double[] inputs = set.inputs;
            for (int i = 0, inputsLength = inputs.length; i < inputsLength; i++) {
                Boolean zero=i>inputsLength && subProblem;
                inputs[i] = zero?0.:random.nextDouble();
            }
            set.outputs[0]=AND(
                    OR(
                            OR(
                                    AND(
                                            set.inputs[0],
                                            0.7
                                    ),
                                    set.inputs[1]
                            ),
                            OR(
                                    set.inputs[2],
                                    NOT(set.inputs[3]
                                    )
                            )
                    ),
                    EQ(
                            NOT(
                                    OR(
                                            set.inputs[5],
                                            AND(
                                                    set.inputs[4],
                                                    0.5
                                            )
                                    )
                            ),
                            OR(
                                    set.inputs[7],
                                    AND(
                                            set.inputs[6],
                                            0.5
                                    )
                            )

                    )
            );
            set.outputs[1]=AND(
                    OR(
                            OR(
                                    AND(
                                            set.inputs[1],
                                            0.7
                                    ),
                                    set.inputs[0]
                            ),
                            OR(
                                    set.inputs[3],
                                    NOT(set.inputs[2]
                                    )
                            )
                    ),
                    EQ(
                            NOT(
                                    OR(
                                            set.inputs[4],
                                            AND(
                                                    set.inputs[5],
                                                    0.5
                                            )
                                    )
                            ),
                            OR(
                                    set.inputs[6],
                                    AND(
                                            set.inputs[7],
                                            0.5
                                    )
                            )

                    )
            );
            return set;
        });
    }
    public void generateAndSave()
    {
        try {
            saveFile.createNewFile();
            OutputStream file=new FileOutputStream(saveFile);
            OutputStream stream=new BufferedOutputStream(file);
            ObjectOutput output=new ObjectOutputStream(stream);
            List<DataSet> results=new LinkedList<>();
            for(Function<Boolean,DataSet> expression:expressions)
            {
                int size=10000;
                for(int i=0;i<size;i++)
                {
                    results.add(expression.apply(i<size/10));
                }
            }
            output.writeObject(results);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

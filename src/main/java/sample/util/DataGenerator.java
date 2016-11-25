package sample.util;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.Charset;
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
    protected List<Function<Boolean,DataSet>> expressions; //Function that accepts single Double parameter and returns 3 Double coordinates.
    protected Random random;

    public DataGenerator() {
        random=new Random();
        expressions=new ArrayList<>();
    }

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
            DataSet set=new DataSet(16);
            Double[] inputs = set.inputs;
            for (int i = 0, inputsLength = inputs.length; i < inputsLength; i++) {
                inputs[i] = random.nextDouble();
            }
            return set;
        });
    }
    public void generateAndSave()
    {
        try {
            saveFile.createNewFile();
            Gson gson= new Gson();
            FileWriter writer=new FileWriter(saveFile);
            List<DataSet> results=new LinkedList<>();
            for(Function<Boolean,DataSet> expression:expressions)
            {
                int size=10000/expressions.size();
                for(int i=0;i<size;i++)
                {
                    results.add(expression.apply(false));
                }
            }
            String output=gson.toJson(results);
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

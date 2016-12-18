package sample.networks;

import sample.neurons.ClassifierNeuron;

/**
 * Created by Szymon on 03.12.2016.
 */
public class WTANetwork extends NeuralNetwork<ClassifierNeuron> {
    public WTANetwork(int in, int out) throws Exception {
        super(new Integer[]{in,out},ClassifierNeuron.class);
    }

    @Override
    public NetworkError learn(Double[] inputData, Double[] expectedOutput) {
        Double[] result=processData(inputData);
        NetworkError error=new NetworkError();
        error.calculate(result,expectedOutput);
        return error;
    }
    @Override
    public Double[] processData(Double[] inputData)
    {
        Double[] result=super.processData(inputData);
        int maxValueIndex=0;
        for(int i=0;i<result.length;i++)
        {
            if(result[i]>result[maxValueIndex])
                maxValueIndex=i;
        }
        for(int i=0;i<result.length;i++)
        {
            if(i==maxValueIndex)
                result[i]=1.;
            else
                result[i]=-1.;
        }
        return result;
    }
}

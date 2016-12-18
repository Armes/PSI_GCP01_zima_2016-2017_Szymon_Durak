package sample.networks;

import sample.neurons.SOMNeuron;

/**
 * Created by Szymon on 18.12.2016.
 */
public class SOMNetwork extends NeuralNetwork<SOMNeuron> {
    private final int outX;
    private final int outY;
    private  double rate;
    private double radius;
    private int recentWinner;
    private int tick;

    public SOMNetwork(int in, int outX, int outY) throws Exception {
        super(new Integer[]{in,outX*outY},SOMNeuron.class);
        this.outX=outX;
        this.outY=outY;
        this.recentWinner=-1;
        this.rate=.1;
        this.radius=Math.sqrt((double)(outX*outX+outY*outY))/4.;
        this.tick=1000;
    }
    @Override
    public NetworkError learn(Double[] inputData, Double[] expectedOutput) {
        Double[] result=processData(inputData);
        adjustWeights(inputData);
        NetworkError error=new NetworkError();
        error.calculate(result,expectedOutput);
        return error;
    }

    private void adjustWeights(Double[] inputData) {
        tick--;
        if(tick==0)
        {
            tick=1000;
            radius*=0.999;
            rate*=0.999;
        }
        int winX=recentWinner%outX,winY=recentWinner/outX;
        for (int i = 0; i < innerLayers[0].neurons.length; i++) {
            int myX=i%outX,myY=i/outX;
            double distance=Math.sqrt(Math.pow(myX-winX,2.)+Math.pow(myY-winY,2.));
            ((SOMNeuron)innerLayers[0].neurons[i]).applyLearningRule(inputData,distance,rate,radius);
        }
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
        recentWinner=maxValueIndex;
        return result;
    }

    public int getRecentWinner() {
        return recentWinner;
    }
}

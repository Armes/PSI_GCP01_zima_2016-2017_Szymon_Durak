package sample.networks;

import sample.neurons.McCullochPittsNeuron;
import sample.neurons.Neuron;

import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class Perceptron extends NeuralNetwork<McCullochPittsNeuron> {
    @Override
    public void learn(List<Double[]> inputData, Double[] expectedOutput) {
        Double[] results=processData(inputData);
        Neuron[] neurons = innerLayers[0].neurons;
        for (int i = 0, neuronsLength = neurons.length; i < neuronsLength; i++) {
            Neuron neuron = neurons[i];
            neuron.applyLearningRule(results[i],expectedOutput[i]);
        }
    }
    @Override
    public NetworkError verify(List<Double[]> inputData, Double[] expectedOutput){
        Double[] previous=inputData.get(inputData.size()-1);
        Double[] tmpExpectedOutput=adjustOutput(expectedOutput,previous);
        return super.verify(inputData,tmpExpectedOutput);
    }
    public Double[] adjustOutput(Double[] expectedOutput,Double[] previous)
    {
        Double[] tmpExpectedOutput=new Double[expectedOutput.length];
        for (int i = 0; i < expectedOutput.length; i++) {
            tmpExpectedOutput[i]=expectedOutput[i]>=previous[i]?1.:-1.;
        }
        return tmpExpectedOutput;
    }
    public Perceptron(int inputs,int outputs) throws Exception {
        super(new Integer[]{inputs,outputs},McCullochPittsNeuron.class);
    }
}

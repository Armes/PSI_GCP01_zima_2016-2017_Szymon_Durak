package sample.networks;

import sample.neurons.McCullochPittsNeuron;
import sample.neurons.McCullochPittsNeuronWithGeneralizedLearningRule;
import sample.neurons.Neuron;

/**
 * Created by Szymon on 08.10.2016.
 */
public class Perceptron extends NeuralNetwork<McCullochPittsNeuron> {
    @Override
    public NetworkError learn(Double[] inputData, Double[] expectedOutput) {
        return standardLearnMode(inputData,expectedOutput);
    }
    @Override
    public NetworkError verify(Double[] inputData, Double[] expectedOutput){
        //Double[] tmpExpectedOutput=adjustOutput(expectedOutput);
        //return super.verify(inputData,tmpExpectedOutput);
        return super.verify(inputData,expectedOutput);
    }

    private Double[] adjustOutput(Double[] expectedOutput) {
        for (int i = 0, expectedOutputLength = expectedOutput.length; i < expectedOutputLength; i++) {
            Double x = expectedOutput[i];
            expectedOutput[i] = x >= 0.5 ? 1. : 0.;
        }
        return expectedOutput;
    }

    public Perceptron(int inputs,int outputs) throws Exception {
        super(new Integer[]{inputs,outputs},McCullochPittsNeuron.class);
    }
}

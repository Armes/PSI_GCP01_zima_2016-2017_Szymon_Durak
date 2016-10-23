package sample.networks;

import sample.neurons.McCullochPittsNeuron;
import sample.neurons.McCullochPittsNeuronWithGeneralizedLearningRule;
import sample.neurons.Neuron;

/**
 * Created by Szymon on 23.10.2016.
 */
public class UnilayerNetwork extends NeuralNetwork<McCullochPittsNeuronWithGeneralizedLearningRule> {
    public UnilayerNetwork(int inputs,int outputs) throws Exception {
        super(new Integer[]{inputs,outputs},McCullochPittsNeuronWithGeneralizedLearningRule.class);
    }

    @Override
    public void learn(Double[] inputData, Double[] expectedOutput) {
        Double[] results=processData(inputData);
        Neuron[] neurons = innerLayers[0].neurons;
        for (int i = 0, neuronsLength = neurons.length; i < neuronsLength; i++) {
            Neuron neuron = neurons[i];
            neuron.applyLearningRule(results[i],expectedOutput[i]);
        }
    }
}

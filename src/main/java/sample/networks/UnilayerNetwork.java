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
    public NetworkError learn(Double[] inputData, Double[] expectedOutput) {
        return standardLearnMode(inputData,expectedOutput);
    }
}

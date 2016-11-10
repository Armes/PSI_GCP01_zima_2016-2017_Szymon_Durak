package sample.networks;

import sample.neurons.BackPropagatingNeuron;

/**
 * Created by Szymon on 10.11.2016.
 */
public class MultilayerNetwork extends NeuralNetwork<BackPropagatingNeuron> {
    public MultilayerNetwork(Integer[] layerSizes) throws Exception {
        super(layerSizes, BackPropagatingNeuron.class);
    }

    @Override
    public NetworkError learn(Double[] inputData, Double[] expectedOutput) {
        Double[] results=processData(inputData);
        int lastLayer=innerLayers.length-1;
        for(int i=0;i<innerLayers[lastLayer].neurons.length;i++){
            innerLayers[lastLayer].neurons[i].applyLearningRule(results[i],expectedOutput[i]);
        }
        for(int i=lastLayer;i>=0;i--)
        {
            for(int j=0;j<innerLayers[i].neurons.length;j++)
            {
                BackPropagatingNeuron neuron=(BackPropagatingNeuron)(innerLayers[i].neurons[j]);
                neuron.propagateError();
                neuron.updateWeights();
            }
        }
        NetworkError error=new NetworkError();
        error.calculate(results,expectedOutput);
        return error;
    }
}

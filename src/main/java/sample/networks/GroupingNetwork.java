package sample.networks;

import sample.neurons.HebbNeuron;
import sample.neurons.Neuron;

/**
 * Created by Szymon on 23.11.2016.
 */
public class GroupingNetwork extends NeuralNetwork<HebbNeuron> {

    private final boolean supervised;
    private final boolean forgetting;
    public GroupingNetwork(Integer[] layerSizes,boolean supervised,boolean forgetting) throws Exception {
        if(layerSizes==null||layerSizes.length<2)
            throw new Exception("Invalid layers");
        inputLayer=new InputLayer(layerSizes[0]);
        for (int i = 1; i < layerSizes.length; i++) {
            innerLayers=new SpecialLayer[layerSizes.length-1];
            for (int j = 0; j < innerLayers.length; j++) {
                innerLayers[j] = new SpecialLayer(layerSizes[j + 1], supervised,forgetting);
                if(j==0)
                    innerLayers[j].bindToAnotherLayer(inputLayer);
                else
                    innerLayers[j].bindToAnotherLayer(innerLayers[j-1]);
            }
        }
        this.forgetting = forgetting;
        this.supervised = supervised;
    }

    @Override
    public NetworkError learn(Double[] inputData, Double[] expectedOutput) {
        Double[] results=normalise(processData(inputData));
        NetworkError error=new NetworkError();
        error.calculate(results,expectedOutput);
        SpecialLayer[] innerLayers1 = (SpecialLayer[]) innerLayers;
        for (int i = (innerLayers1).length-1; i >=0; i--) {
            SpecialLayer layer = innerLayers1[i];
            HebbNeuron[] neurons = (HebbNeuron[]) layer.neurons;
            for (int j = 0, neuronsLength = neurons.length; j < neuronsLength; j++) {
                HebbNeuron neuron = neurons[j];
                double neuronOutput=neuron.getSignal();
                double expectedSignal=0;
                if(i==innerLayers1.length-1) {
                    expectedSignal = expectedOutput[j];
                }
                else
                {
                    for (Neuron neuron1 : innerLayers1[i + 1].neurons) {
                        expectedSignal+=((HebbNeuron)neuron1).getExpectedSignal();
                    }
                    expectedSignal/=(double)innerLayers1[i+1].neurons.length;
                }
                neuron.setExpectedSignal(expectedSignal);
                neuron.applyLearningRule(neuronOutput,expectedSignal);
            }
        }
        return error;
    }
    @Override
    public NetworkError verify(Double[] inputData, Double[] expectedOutput)
    {
        Double[] results = normalise(processData(inputData));
        NetworkError error=new NetworkError();
        error.calculate(results,expectedOutput);
        return error;
    }
    private Double[] normalise(Double[] doubles) {
        for (int i = 0; i < doubles.length; i++) {
            doubles[i]=Math.signum(doubles[i]);
        }
        return doubles;
    }

    public boolean isSupervised() {
        return supervised;
    }

    public boolean isForgetting() {
        return forgetting;
    }

    public class SpecialLayer extends Layer<HebbNeuron>
    {
        public SpecialLayer(int size,boolean supervised,boolean forgetting)
        {
            super();

            neurons = new HebbNeuron[size];
            for (int i = 0; i < neurons.length; i++) {
                neurons[i]=new HebbNeuron(supervised,forgetting);
            }
        }
    }
}

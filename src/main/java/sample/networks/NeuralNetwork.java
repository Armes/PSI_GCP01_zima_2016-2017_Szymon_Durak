package sample.networks;

import sample.neurons.InputNeuron;
import sample.neurons.Neuron;

import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public abstract class NeuralNetwork<NetworkType extends Neuron> {
    InputLayer inputLayer;
    Layer[] innerLayers;
    
    public NeuralNetwork(Integer[] layerSizes,Class<NetworkType> networkType) throws Exception {
        if(layerSizes==null||layerSizes.length<2)
            throw new Exception("Invalid layers");
        inputLayer=new InputLayer(layerSizes[0]);
        for (int i = 1; i < layerSizes.length; i++) {
            innerLayers=new Layer[layerSizes.length-1];
            for (int j = 0; j < innerLayers.length; j++) {
                innerLayers[j] = new Layer<>(layerSizes[j + 1], networkType);
                if(j==0)
                    innerLayers[j].bindToAnotherLayer(inputLayer);
                else
                    innerLayers[j].bindToAnotherLayer(innerLayers[j-1]);
            }
        }
    }
    public abstract void learn(Double[] inputData, Double[] expectedOutput);
    public NetworkError verify(Double[] inputData, Double[] expectedOutput)
    {
        Double[] results = processData(inputData);
        NetworkError error=new NetworkError();
        error.calculate(results,expectedOutput);
        return error;
    }
    public Double[] processData(Double[] inputData)
    {
        inputLayer.readData(inputData);
        inputLayer.compute();
        for (Layer layer :
                innerLayers) {
            layer.compute();
        }
        return innerLayers[innerLayers.length-1].getLayerOutput();

    }

    public class Layer<Type extends Neuron>{
        protected Neuron[] neurons;

        public Layer() {

        }

        public <SomeType extends Neuron> void bindToAnotherLayer(Layer<SomeType> another){
            for (Neuron neuron :
                    neurons) {
                neuron.setConnections(another.neurons);
            }
        }
        public Layer(int size,Class<Type> neuronType){
            neurons = new Neuron[size];
            for (int i = 0; i < neurons.length; i++) {
                try {
                    neurons[i]=neuronType.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        public void compute()
        {
            for (Neuron neuron :
                    neurons) {
                neuron.update();
            }
        }
        public Double[] getLayerOutput(){
            Double[] result=new Double[neurons.length];
            for (int i = 0; i < neurons.length; i++) {
                result[i]=neurons[i].getSignal();
            }
            return result;
        }
    }
    public class InputLayer extends Layer<InputNeuron>{
        Double[] inputs;
        public InputLayer(int size){
            super();
            neurons = new Neuron[size];
            inputs=new Double[size];
            for (int i = 0; i < neurons.length; i++) {
                    neurons[i]=new InputNeuron(inputs,i);
            }
            for (int i = 0; i < size; i++) {
                inputs[i]=0.;
            }
        }
        public void readData(Double[] data,int startIndex){
            for (int i = 0; i < inputs.length && i + startIndex < data.length; i++) {
                inputs[i]=data[i+startIndex];
            }

        }
        @Override
        public void compute()
        {
            for (Neuron neuron :
                    neurons) {
                InputNeuron asInputNeuron = (InputNeuron) neuron;
                asInputNeuron.update();
            }
        }
        public void readData(Double[] data)
        {
            readData(data,0);
        }
    }


}

package sample.neurons;

import java.io.InputStream;

/**
 * Created by Szymon on 08.10.2016.
 */
public class InputNeuron extends Neuron {
    private Double[] inputValues;
    private int neuronIndex;
    public InputNeuron(Double[] inputValues,int index){
        this.neuronIndex=index;
        this.inputValues=inputValues;
        }
    public void setInputValues(Double[] newInputValues)
    {
        this.inputValues=newInputValues;
    }
    public void setNeuronIndex(int neuronIndex)
    {
        this.neuronIndex=neuronIndex;
    }
    @Override
    public void update(){
        lastSignal=inputValues[neuronIndex];
    }
    @Override
    protected Double activationFunction(Double signal) {
        return signal;
    }

    @Override
    public void applyLearningRule(Double result, Double expectedResult) {
        return;
    }

    @Override
    public Double getSignal(){
        return activationFunction(inputValues[neuronIndex]);
    }
}

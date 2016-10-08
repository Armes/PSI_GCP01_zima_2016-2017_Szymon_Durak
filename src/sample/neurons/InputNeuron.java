package sample.neurons;

import java.io.InputStream;

/**
 * Created by Szymon on 08.10.2016.
 */
public class InputNeuron extends Neuron {
    Double[] inputValues;
    int neuronIndex;
    public InputNeuron(Double[] inputValues,int index){
        this.neuronIndex=index;
        this.inputValues=inputValues;
        }

    @Override
    protected Double activationFunction(Double signal) {
        return signal;
    }

    @Override
    protected void applyLearningRule(Double result,Double expectedResult) {
        return;
    }

    @Override
    public Double getSignal(){
        return activationFunction(inputValues[neuronIndex]);
    }
}

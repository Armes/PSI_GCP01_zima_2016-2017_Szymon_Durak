package sample.neurons;

import sample.functions.ActivationFunction;
import sample.functions.HyperbolicTangensFunction;
import sample.networks.NeuralNetwork;

/**
 * Created by Szymon on 09.11.2016.
 */
public class BackPropagatingNeuron extends Neuron {
    private final ActivationFunction function;
    private double calculatedError;

    public BackPropagatingNeuron(ActivationFunction function) {
        super();
        this.function = function;
    }
    public BackPropagatingNeuron() {
        super();
        this.function = new HyperbolicTangensFunction();
    }

    @Override
    protected double activationFunction(Double signal) {
        calculatedError=0.;
        return function.calc(signal);
    }

    @Override
    public void applyLearningRule(Double output, Double expectedOutput) {
        calculatedError+= output - expectedOutput;
    }
    public void propagateError(){
        calculatedError*=function.derivative(lastSignal);
        for (Connection connection :
                connections) {
            double x=connection.inputNeuron.getSignal();
            double correction=connection.inputWeight*calculatedError;
            connection.inputNeuron.applyLearningRule(x,x+correction);
        }
    }
    public void updateWeights(){
        for (Connection connection :
                connections) {
            double x=connection.inputNeuron.getSignal();
            double correction=-1.*x*calculatedError*LEARNING_RATE;
            connection.inputWeight+=correction;
        }
        constant+=-1.*calculatedError*LEARNING_RATE;
    }
}

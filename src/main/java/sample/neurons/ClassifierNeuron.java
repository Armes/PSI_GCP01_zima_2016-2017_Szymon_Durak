package sample.neurons;

import sample.functions.HyperbolicTangensFunction;

/**
 * Created by Szymon on 03.12.2016.
 */
public class ClassifierNeuron extends Neuron {
    private Double decrementing_learning_rate=0.1;
    private Double decrement_coefficient=0.0001;
    private final int decrement_frequency=30;
    private int decrement_count=0;
    public ClassifierNeuron(){
        super();
        constant=0.;
    }
    private void adjustLearningRate(){
        decrement_count=(decrement_count+1)%decrement_frequency;
        if(decrement_count==0&&decrementing_learning_rate>1e-4)
            decrementing_learning_rate*=(1.-decrement_coefficient);
    }
    @Override
    protected double activationFunction(Double signal) {
        return new HyperbolicTangensFunction().calc(signal);
    }

    @Override
    public void applyLearningRule(Double output, Double expectedOutput) {
        if(expectedOutput==1.) {
            for (Connection connection :
                    connections) {
                Double w = connection.inputWeight, s = connection.inputNeuron.getSignal();
                connection.inputWeight = decrementing_learning_rate*s+(1.-decrementing_learning_rate)*w;
            }
        }
        adjustLearningRate();
    }
}

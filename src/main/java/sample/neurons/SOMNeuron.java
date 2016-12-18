package sample.neurons;

/**
 * Created by Szymon on 18.12.2016.
 */
public class SOMNeuron extends Neuron
{
    public SOMNeuron(){
        super();
        constant=0.;
    }

    @Override
    protected double activationFunction(Double signal) {
        return signal;
    }

    @Override
    public void applyLearningRule(Double output, Double expectedOutput) {
    }

    public void applyLearningRule(Double[] inputData, double distance, double rate, double radius) {
        double h=rate*Math.exp(-Math.pow(distance/radius,2.));
        for (int i = 0; i < connections.length; i++) {
            connections[i].inputWeight+=(inputData[i]-connections[i].inputWeight)*h;
        }
    }
}

package sample.neurons;

/**
 * Created by Szymon on 08.10.2016.
 */
public class McCullochPittsNeuron extends Neuron {
    @Override
    protected double activationFunction(Double signal) {
        return signal>0.?1.:0.;
    }
    public McCullochPittsNeuron(){
        super();
    }
    @Override
    public void applyLearningRule(Double result, Double expectedResult) {
        for(Connection connection:connections)
        {
            if(result==expectedResult)
                continue;
            else if(result>expectedResult) {
                connection.inputWeight -= connection.inputNeuron.getSignal() * LEARNING_RATE;
            }
            else {
                connection.inputWeight += connection.inputNeuron.getSignal() * LEARNING_RATE;
            }
        }
        if(result>expectedResult)
            constant-=LEARNING_RATE;
        else if(result<expectedResult)
            constant+=LEARNING_RATE;

    }
}

package sample.neurons;

/**
 * Created by Szymon on 08.10.2016.
 */
public class McCullochPittsNeuronWithGeneralizedLearningRule extends McCullochPittsNeuron {
    public McCullochPittsNeuronWithGeneralizedLearningRule()
    {
        super();
    }
    @Override
    protected Double activationFunction(Double signal) {
        return signal;
        //return signal<0.?0.:signal>1.?1.:signal;
    }
    @Override
    public void applyLearningRule(Double result, Double expectedResult){
        constant+=LEARNING_RATE*(expectedResult-result);
        for(Connection connection:connections)
        {
            connection.inputWeight+=LEARNING_RATE*connection.inputNeuron.getSignal()*(expectedResult-result);
        }
    }
}

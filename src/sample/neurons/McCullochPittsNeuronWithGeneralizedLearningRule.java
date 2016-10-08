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
    public void applyLearningRule(Double result, Double expectedResult){
        for(Connection connection:connections)
        {
            connection.inputWeight+=LEARNING_RATE*connection.inputNeuron.getSignal()*(expectedResult-result);
        }
    }
}

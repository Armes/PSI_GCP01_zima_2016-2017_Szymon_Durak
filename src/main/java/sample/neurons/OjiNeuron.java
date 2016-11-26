package sample.neurons;

/**
 * Created by Szymon on 26.11.2016.
 */
public class OjiNeuron extends UnsupervisedNeuron {
    @Override
    public void applyLearningRule(Double output, Double expectedOutput)
    {
        for (Connection c:connections
                ) {
            double y;
            if(supervised)
                y=expectedOutput;
            else
                y=output;
            c.inputWeight+=LEARNING_SPEED*y*(c.inputNeuron.getSignal()-y*c.inputWeight);
        }
    }
}

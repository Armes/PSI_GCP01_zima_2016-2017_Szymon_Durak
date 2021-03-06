package sample.neurons;

/**
 * Created by Szymon on 26.11.2016.
 */
public class HebbNeuron extends UnsupervisedNeuron {
    @Override
    public void applyLearningRule(Double output, Double expectedOutput)
    {
        for (Connection c:connections
             ) {
            if(forgetting)
                c.inputWeight*=(1.-FORGETTING_SPEED);
            double y;
            if(supervised)
                y=expectedOutput;
            else
                y=output;
            c.inputWeight+=LEARNING_SPEED*y*c.inputNeuron.getSignal();
        }
    }
}

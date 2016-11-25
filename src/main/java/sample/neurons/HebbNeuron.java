package sample.neurons;

/**
 * Created by Szymon on 23.11.2016.
 */
public class HebbNeuron extends Neuron {
    private final double LEARNING_SPEED=5e-6;
    private final double FORGETTING_SPEED=1e-5;
    private boolean supervised;
    private boolean forgetting;
    private double expectedSignal;

    public HebbNeuron(){
        this(true);
    }

    public HebbNeuron(boolean supervised) {
        this(supervised,false);
    }

    public HebbNeuron(boolean supervised, boolean forgetting) {
        super();
        this.supervised=supervised;
        this.forgetting=forgetting;
        this.constant=0.;
    }

    @Override
    protected double activationFunction(Double signal) {
        return signal;
    }

    @Override
    public void applyLearningRule(Double output, Double expectedOutput) {
        for (Connection c:connections
             ) {
            if(forgetting)
                c.inputWeight*=(1.-FORGETTING_SPEED);
            if(supervised)
                c.inputWeight+=LEARNING_SPEED*expectedOutput*c.inputNeuron.getSignal();
            else
                c.inputWeight+=LEARNING_SPEED*output*c.inputNeuron.getSignal();
        }
    }

    public double getExpectedSignal() {
        return expectedSignal;
    }
    public void setExpectedSignal(double expectedSignal) {
        this.expectedSignal = expectedSignal;
    }
}

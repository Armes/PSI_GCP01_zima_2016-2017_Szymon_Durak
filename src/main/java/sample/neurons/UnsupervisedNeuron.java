package sample.neurons;

/**
 * Created by Szymon on 23.11.2016.
 */
public abstract class UnsupervisedNeuron extends Neuron {
    protected final double LEARNING_SPEED=5e-6;
    protected final double FORGETTING_SPEED=1e-5;
    protected boolean supervised;
    protected boolean forgetting;
    private double expectedSignal;

    public void setSupervised(boolean supervised) {
        this.supervised = supervised;
    }

    public void setForgetting(boolean forgetting) {
        this.forgetting = forgetting;
    }

    public UnsupervisedNeuron(){
        this(true);
    }

    public UnsupervisedNeuron(boolean supervised) {
        this(supervised,false);
    }

    public UnsupervisedNeuron(boolean supervised, boolean forgetting) {
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
    public abstract void applyLearningRule(Double output, Double expectedOutput) ;

    public double getExpectedSignal() {
        return expectedSignal;
    }
    public void setExpectedSignal(double expectedSignal) {
        this.expectedSignal = expectedSignal;
    }
}

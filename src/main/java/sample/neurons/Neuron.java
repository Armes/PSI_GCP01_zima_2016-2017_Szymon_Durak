package sample.neurons;

import com.sun.istack.internal.NotNull;

import java.util.Random;

/**
 * Created by Szymon on 06.10.2016.
 */
public abstract class Neuron {
    public static final Double LEARNING_RATE=1e-4;
    public Connection[] connections;
    public double constant =new Random().nextGaussian();
    protected Double lastSignal;

    public Double getSignal(){
        return lastSignal;
    }
    public void update()
    {
        lastSignal= activationFunction(sumSignals());
    }
    public Double updateAndGetSignal(){
        update();
        return lastSignal;
    }
    public Neuron(){
    }
    protected abstract double activationFunction(Double signal);
    public abstract void applyLearningRule(Double output, Double expectedOutput);
    private Double sumSignals() {
        Double sum= constant;
        for (Connection connection:connections
             ) {
            sum+=connection.getWeightedSignal();
        }
        return sum;
    }
    public void setConnections(Neuron[] neurons)
    {
        connections=new Connection[neurons.length];
        for (int i = 0; i < connections.length; i++) {
            connections[i]=new Connection(neurons[i]);
        }
    }
    public class Connection{
        public Neuron inputNeuron;
        public Double inputWeight;
        public Double getWeightedSignal()
        {
            return inputWeight*inputNeuron.getSignal();
        }
        public Connection(@NotNull Neuron inputNeuron)
        {
            this.inputNeuron=inputNeuron;
            this.inputWeight=new Random().nextGaussian();
        }
    }
}
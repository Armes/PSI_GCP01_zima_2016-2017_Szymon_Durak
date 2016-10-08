package sample.neurons;

import com.sun.istack.internal.NotNull;
import jdk.internal.util.xml.impl.Pair;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by Szymon on 06.10.2016.
 */
public abstract class Neuron {
    public static final Double LEARNING_RATE=0.05;
    protected Connection[] connections;
    public Double getSignal(){
        return activationFunction(sumSignals());
    }

    protected abstract Double activationFunction(Double signal);
    protected abstract void applyLearningRule(Double output, Double expectedOutput);
    private Double sumSignals() {
        Double sum=new Double(.0);
        for (Connection connection:connections
             ) {
            sum+=connection.getWeightedSignal();
        }
        return sum;
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
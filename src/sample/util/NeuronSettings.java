package sample.util;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NeuronSettings {
    public int numberOfEpochs;
    public int numberOfNeurons;
    public int maxNeuronsOnCharts;
    public int cyclesPerEpoch;
    public NeuronSettings()
    {
        numberOfEpochs=100;
        numberOfNeurons=100;
        maxNeuronsOnCharts=10;
        cyclesPerEpoch=10000;
    }
}

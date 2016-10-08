package sample.util;

import sample.networks.NeuralNetwork;
import sample.networks.Perceptron;
import sample.neurons.McCullochPittsNeuron;
import sample.neurons.Neuron;

import java.io.File;
import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NeuralNetworkLogic {
    private DataReader dataReader;
    private List<List<Double[]>> data;
    private NeuralNetwork network;

    public NeuralNetworkLogic() {
    }

    public void loadData(File targetFile) {
        this.dataReader=new DataReader(targetFile);
        this.dataReader.readFile();
        this.data=this.dataReader.getData();
    }

    public void runAsPerceptron(NeuronSettings settings){
        try {
            this.network= new Perceptron(15,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

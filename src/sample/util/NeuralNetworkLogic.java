package sample.util;

import javafx.scene.control.ProgressBar;
import sample.networks.NetworkError;
import sample.networks.NeuralNetwork;
import sample.networks.Perceptron;
import sun.nio.ch.Net;

import java.io.File;
import java.util.*;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NeuralNetworkLogic {
    private DataReader dataReader;
    private List<List<Double[]>> data;
    private NeuralNetwork[] networks;
    private NetworkError[][] errors;
    private ProgressBar neuronProgress;
    private ProgressBar epochProgress;

    public NeuralNetworkLogic(ProgressBar neuronProgress, ProgressBar epochProgress) {
        this.neuronProgress = neuronProgress;
        this.epochProgress = epochProgress;
    }

    public void loadData(File targetFile) {
        this.dataReader=new DataReader(targetFile);
        this.dataReader.readFile();
        this.data=this.dataReader.getData();
    }

    public void runAsPerceptron(NeuronSettings settings){
        data=data.subList(8000,9999);
        try {
            this.networks = new Perceptron[settings.numberOfNeurons];
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                this.networks[i]=new Perceptron(15,3);
            }
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                neuronProgress.setProgress((float)i/(float)settings.numberOfNeurons);
                for(int j=0;j<settings.numberOfEpochs;j++){
                    epochProgress.setProgress((float)j/(float)settings.numberOfEpochs);
                    Collections.shuffle(data);
                    performCycles(settings,i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NetworkError performCycles(NeuronSettings settings,int neuronIndex) {
        int cuttingPoint=7*data.size()/10;
        List<List<Double[]>>
                learningSet=data.subList(0,cuttingPoint),
                verifyingSet=data.subList(cuttingPoint,data.size());
        for (int i = 0; i < settings.cyclesPerEpoch; i++) {
            Collections.shuffle(learningSet);
            for (List<Double[]> set:
            learningSet){
                List<Double[]> input=set.subList(0,set.size()-1);
                Double[] output=set.get(set.size()-1);
                networks[neuronIndex].learn(input,output);
            }
        }
        List<NetworkError> networkErrorList=new LinkedList<>();
        for (List<Double[]> set :
                verifyingSet) {
            List<Double[]> input=set.subList(0,set.size()-1);
            Double[] output=set.get(set.size()-1);
            networkErrorList.add(networks[neuronIndex].verify(input,output));
        }
        return NetworkError.combine(networkErrorList);
    }

}

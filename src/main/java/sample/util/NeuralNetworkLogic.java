package sample.util;

import javafx.stage.DirectoryChooser;
import javafx.util.Pair;
import sample.networks.NetworkError;
import sample.networks.NeuralNetwork;
import sample.networks.Perceptron;
import sample.networks.UnilayerNetwork;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NeuralNetworkLogic {
    private DataReader dataReader;
    private List<DataSet> data;
    private NeuralNetwork[] networks;
    Function<Double[], Double[]> outputExpr;

    public NeuralNetworkLogic() {
    }

    public void loadData(File targetFile) {
        this.dataReader=new DataReader(targetFile);
        this.dataReader.readFile();
        this.data=this.dataReader.getData();
    }
    public void runLearning(NeuronSettings settings,Function<Double[], Double[]> outputExpr){
        this.outputExpr=outputExpr;
        File directory = new DirectoryChooser().showDialog(null);
        File mseFile=new File(directory,"MSE.txt");
        File mapeFile=new File(directory,"MAPE.txt");
        File bestNeuronFile=new File(directory,"BestNeuron.txt");
        List<Pair<NeuralNetwork,List<NetworkError>>> results=new LinkedList<>();
        try {
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                List<NetworkError> errors=new LinkedList<>();
                for(int j=0;j<settings.numberOfEpochs;j++){
                    Collections.shuffle(data);
                    NetworkError error=performCycles(settings,i);
                    errors.add(error);
                }
                results.add(new Pair<>(networks[i],errors));
            }
            results.sort((o1, o2) -> {
                List<NetworkError> l1=o1.getValue(),l2=o2.getValue();
                NetworkError e1=l1.get(l1.size()-1), e2=l2.get(l2.size()-1);
                double result=e1.getMSE()-e2.getMSE();
                if(result>0.)
                    return 1;
                else if(result==0.)
                    return 0;
                else
                    return -1;
            });
            results=results.subList(0,settings.maxNeuronsOnCharts);
            writeMSEChartsFor(results,mseFile);
            writeMAPEChartsFor(results,mapeFile);
            writeBestNeuron(results.get(0).getKey(),bestNeuronFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        results=null;
        networks=null;
        bestNeuronFile=null;
        data=null;

    }
    public void runAsPerceptron(NeuronSettings settings)throws Exception{
        this.networks = new Perceptron[settings.numberOfNeurons];
        for (int i = 0; i < settings.numberOfNeurons; i++) {
            this.networks[i]=new Perceptron(16,2);
        }
        runLearning(settings,(array)->{
            Double[] results=new Double[2];
            results[0]=0.;
            results[1]=0.;
            for (int i = 0; i < array.length; i++) {
                results[0]=results[0]<array[i]?array[i]:results[0];
                results[1]=results[1]>array[i]?array[i]:results[1];
            }
            results[0]=results[0]<=0.65?0.:1.;
            results[1]=results[1]>=0.35?0.:1.;
            return results;
        });
    }
    protected void writeBestNeuron(NeuralNetwork neuralNetwork, File bestNeouronFile) throws IOException {

        bestNeouronFile.createNewFile();
        //Gson gson= new Gson();
        //String output=gson.toJson(neuralNetwork);
        String output=neuralNetwork.getParameters();
        FileWriter writer=new FileWriter(bestNeouronFile);
        writer.write(output);
        writer.close();
    }

    protected void writeMAPEChartsFor(List<Pair<NeuralNetwork, List<NetworkError>>> results, File mapeFile) throws IOException {
        mapeFile.createNewFile();
        FileWriter writer = new FileWriter(mapeFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        results.forEach(neuralNetworkListPair -> {
            neuralNetworkListPair.getValue().forEach(networkError -> {
                try {
                    bufferedWriter.write(String.format("%1.6f;", networkError.getMAPE()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bufferedWriter.close();
    }

    protected void writeMSEChartsFor(List<Pair<NeuralNetwork, List<NetworkError>>> results, File mseFile) throws IOException {
        mseFile.createNewFile();
        FileWriter writer = new FileWriter(mseFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        results.forEach(neuralNetworkListPair -> {
            final String[] newLine = {""};
            neuralNetworkListPair.getValue().forEach(networkError -> {
                try {
                    bufferedWriter.write(String.format("%1.6f;", networkError.getMSE()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bufferedWriter.close();
    }

    private NetworkError performCycles(NeuronSettings settings, int neuronIndex) {
        int cuttingPoint=7*data.size()/10;
        List<DataSet>
                learningSet=data.subList(0,cuttingPoint),
                verifyingSet=data.subList(cuttingPoint,data.size());
        for (int i = 0; i < settings.cyclesPerEpoch; i++) {
            Collections.shuffle(learningSet);
            for (DataSet set:
            learningSet){
                Double[] input=set.inputs;
                Double[] output=outputExpr.apply(input);
                networks[neuronIndex].learn(input,output);
            }
        }
        List<NetworkError> networkErrorList=new LinkedList<>();
        for (DataSet set :
                verifyingSet) {
            Double[] input=set.inputs;
            Double[] output=outputExpr.apply(input);
            networkErrorList.add(networks[neuronIndex].verify(input,output));
        }
        NetworkError avgError=NetworkError.combine(networkErrorList);
        return avgError;
    }

    public void runAsMcCulloch(NeuronSettings settings) throws Exception {
        this.networks = new UnilayerNetwork[settings.numberOfNeurons];
        for (int i = 0; i < settings.numberOfNeurons; i++) {
            this.networks[i]=new UnilayerNetwork(16,1);
        }
        runLearning(settings,(array)->{
            Double[] results=new Double[1];
            results[0]=1.;
            for (int i = 0; i < array.length; i++) {
                results[0]=results[0]>array[i]?array[i]:results[0];
            }
            return results;
        });

    }
}

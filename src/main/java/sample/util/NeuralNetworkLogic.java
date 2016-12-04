package sample.util;

import javafx.stage.DirectoryChooser;
import javafx.util.Pair;
import sample.networks.*;
import sample.neurons.HebbNeuron;
import sample.neurons.OjiNeuron;

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
    public NeuralNetwork runLearning(NeuronSettings settings, Function<Double[], Double[]> outputExpr){
        this.outputExpr=outputExpr;
        File directory = new DirectoryChooser().showDialog(null);
        File mseFile=new File(directory,"MSE.txt");
        File mapeFile=new File(directory,"MAPE.txt");
        File timeFile=new File(directory,"TIME.txt");
        File bestNeuronFile=new File(directory,"BestNeuron.txt");
        List<Pair<NeuralNetwork,List<NetworkError[]>>> results=new LinkedList<>();
        List<Pair<NeuralNetwork,List<Double>>> time=new LinkedList<>();
        try {
            System.out.println(String.format("%d Networks begin learning on size %d dataset",this.networks.length,this.data.size()));
            LinkedList<Thread> threads=new LinkedList<>();
            List<Pair<NeuralNetwork, List<NetworkError[]>>> finalResults = results;
            List<Pair<NeuralNetwork, List<Double>>> finalTime = time;
                for (int i = 0, top = Runtime.getRuntime().availableProcessors(); i < top; i++) {
                    int finalI = i;
                    Thread t=new Thread(()-> {
                    for (int j = finalI; j < networks.length; j += top) {
                        List<NetworkError[]> errors = new LinkedList<>();
                        List<Double> zeit=new LinkedList<>();
                        for (int k = 0; k < settings.numberOfEpochs; k++) {
                            double tElapsed=System.nanoTime();
                            Collections.shuffle(data);
                            NetworkError[] error = performCycles(settings, j);
                            tElapsed=(System.nanoTime()-tElapsed)/1000000000.;
                            errors.add(error);
                            zeit.add(tElapsed);
                        }
                        synchronized (finalResults) {
                            finalResults.add(new Pair<>(networks[j], errors));
                        }
                        synchronized (finalTime){
                            finalTime.add(new Pair<>(networks[j],zeit));
                        }
                    }
                    });
                    t.start();
                    threads.add(t);
                }
            for (Thread t :
                    threads) {
                t.join();
            }
            System.out.println("Networks finished learning");
            results.sort((o1, o2) -> {
                List<NetworkError[]> l1=o1.getValue(),l2=o2.getValue();
                NetworkError[] e1=l1.get(l1.size()-1), e2=l2.get(l2.size()-1);
                double mse1=e1[1].getMSE();
                double mse2=e2[1].getMSE();
                if(mse1>mse2)
                    return 1;
                else if(mse1<mse2)
                    return -1;
                else
                    return 0;
            });
            results=results.subList(0,settings.maxNeuronsOnCharts);
            writeMSEChartsFor(results,mseFile);
            writeMAPEChartsFor(results,mapeFile);
            writeTimeFor(time,timeFile);
            writeBestNeuron(results.get(0).getKey(),bestNeuronFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        NeuralNetwork bestNetwork=results.get(0).getKey();
        results=null;
        networks=null;
        bestNeuronFile=null;
        return bestNetwork;
    }

    private void writeTimeFor(List<Pair<NeuralNetwork, List<Double>>> time, File timeFile) throws IOException {
        timeFile.createNewFile();
        FileWriter writer = new FileWriter(timeFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        time.forEach(neuralNetworkListPair -> {
            try{
                bufferedWriter.write("Neuron "+time.indexOf(neuralNetworkListPair)+" Epochs time;");
            } catch (IOException e) {
                e.printStackTrace();
            }
            neuralNetworkListPair.getValue().forEach(record -> {
                try {
                    bufferedWriter.write(String.format("%3.6f;", record));
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

    protected void writeMAPEChartsFor(List<Pair<NeuralNetwork, List<NetworkError[]>>> results, File mapeFile) throws IOException {
        mapeFile.createNewFile();
        FileWriter writer = new FileWriter(mapeFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        results.forEach(neuralNetworkListPair -> {
            try{
                bufferedWriter.write("Training Set Error;");
            } catch (IOException e) {
                e.printStackTrace();
            }
            neuralNetworkListPair.getValue().forEach(networkError -> {
                try {
                    bufferedWriter.write(String.format("%1.6f;", networkError[0].getMAPE()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                bufferedWriter.newLine();
                bufferedWriter.write("Verifying Set Error;");
            } catch (IOException e) {
                e.printStackTrace();
            }
            neuralNetworkListPair.getValue().forEach(networkError -> {
                try {
                    bufferedWriter.write(String.format("%1.6f;", networkError[1].getMAPE()));
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

    protected void writeMSEChartsFor(List<Pair<NeuralNetwork, List<NetworkError[]>>> results, File mseFile) throws IOException {
        mseFile.createNewFile();
        FileWriter writer = new FileWriter(mseFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        results.forEach(neuralNetworkListPair -> {
            try{
                bufferedWriter.write("Training Set Error;");
            } catch (IOException e) {
                e.printStackTrace();
            }
            neuralNetworkListPair.getValue().forEach(networkError -> {
                try {
                    bufferedWriter.write(String.format("%1.6f;", networkError[0].getMSE()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                bufferedWriter.newLine();
                bufferedWriter.write("Verifying Set Error;");
            } catch (IOException e) {
                e.printStackTrace();
            }
            neuralNetworkListPair.getValue().forEach(networkError -> {
                try {
                    bufferedWriter.write(String.format("%1.6f;", networkError[1].getMSE()));
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

    private NetworkError[] performCycles(NeuronSettings settings, int neuronIndex) {
        int cuttingPoint=7*data.size()/10;
        List<DataSet>
                learningSet=data.subList(0,cuttingPoint),
                verifyingSet=data.subList(cuttingPoint,data.size());
        List<NetworkError> trainingErrorList=new LinkedList<>();
        for (int i = 0; i < settings.cyclesPerEpoch; i++) {
            Collections.shuffle(learningSet);
            for (DataSet set:
            learningSet){
                Double[] input=set.inputs;
                Double[] output=outputExpr.apply(input);
                NetworkError err=networks[neuronIndex].learn(input,output);
                trainingErrorList.add(err);
            }
        }
        List<NetworkError> networkErrorList=new LinkedList<>();
        for (DataSet set :
                verifyingSet) {
            Double[] input=set.inputs;
            Double[] output=outputExpr.apply(input);
            networkErrorList.add(networks[neuronIndex].verify(input,output));
        }
        NetworkError avgVerifyError=NetworkError.combine(networkErrorList);
        NetworkError avgTrainingError=NetworkError.combine(trainingErrorList);
        return new NetworkError[]{avgTrainingError,avgVerifyError};
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

    public void runAsBackpropagating(NeuronSettings settings) throws Exception {
        this.networks = new MultilayerNetwork[settings.numberOfNeurons];
        for (int i = 0; i < settings.numberOfNeurons; i++) {
            Integer[] init = {16,64,16,1};
            this.networks[i]=new MultilayerNetwork(init);
        }
        runLearning(settings,(array)->{
            Double[] results=new Double[1];
            results[0]=array[array.length-1];
            return results;
        });
    }

    public void runAsHebbLearning(NeuronSettings settings, boolean supervised, boolean forgetting, boolean multilayer) throws Exception {
        this.networks = new GroupingNetwork[settings.numberOfNeurons];
        if(multilayer)
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                Integer[] init = {16,8,3};
                this.networks[i]=new GroupingNetwork<>(init, HebbNeuron.class,supervised,forgetting);
            }
        else
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                Integer[] init = {16,3};
                this.networks[i]=new GroupingNetwork<>(init, HebbNeuron.class,supervised,forgetting);
            }

        runLearning(settings,(array)->{
            Double[] results=new Double[3];
            System.arraycopy(array, 16, results, 0, results.length);
            return results;
        });
    }
    public void runAsOjiLearning(NeuronSettings settings, boolean supervised, boolean multilayer) throws Exception {
        this.networks = new GroupingNetwork[settings.numberOfNeurons];
        if(multilayer)
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                Integer[] init = {16,8,3};
                this.networks[i]=new GroupingNetwork<>(init, OjiNeuron.class,supervised,false);
            }
        else
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                Integer[] init = {16,3};
                this.networks[i]=new GroupingNetwork<>(init, OjiNeuron.class,supervised,false);
            }

        runLearning(settings,(array)->{
            Double[] results=new Double[3];
            System.arraycopy(array, 16, results, 0, results.length);
            return results;
        });
    }

    public void runAsKohonenClassifier(NeuronSettings settings) throws Exception {
        this.networks = new KohonenNetwork[settings.numberOfNeurons];
            for (int i = 0; i < settings.numberOfNeurons; i++) {
                this.networks[i]=new KohonenNetwork(16,3);
            }

        runLearning(settings,(array)->{
            Double[] results=new Double[3];
            System.arraycopy(array, 16, results, 0, results.length);
            return results;
        });
    }

    public void runClassifiedPerceptronLearning(NeuronSettings settings) throws Exception {
        this.networks = new KohonenNetwork[500];
        for (int i = 0; i < this.networks.length; i++) {
            this.networks[i]=new KohonenNetwork(16,3);
        }

        NeuralNetwork classifier=runLearning(settings,(array)->{
            Double[] results=new Double[3];
            System.arraycopy(array, 16, results, 0, results.length);
            return results;
        });
        List<NeuralNetwork> winnerNetworks=new LinkedList<>();
        this.networks=new UnilayerNetwork[settings.numberOfNeurons];
        for (int i = 0; i < this.networks.length; i++) {
            this.networks[i]=new UnilayerNetwork(16,1);
        }
        extendWinnerList(settings,winnerNetworks);
        List<DataSet>[] classifiedData=classify(classifier);
        for (List<DataSet> setList :
                classifiedData) {
            this.networks=new UnilayerNetwork[settings.numberOfNeurons];
            for (int i = 0; i < this.networks.length; i++) {
                this.networks[i]=new UnilayerNetwork(16,1);
            }
            this.data=setList;
            extendWinnerList(settings,winnerNetworks);
        }

    }

    private void extendWinnerList(NeuronSettings settings, List<NeuralNetwork> winnerNetworks) {
        winnerNetworks.add(runLearning(settings,(array)->{
            Double[] results=new Double[1];
            results[0]=1.;
            for (int i = 0; i < 16; i++) {
                results[0]=results[0]>array[i]?array[i]:results[0];
            }
            return results;
        }));
    }

    private List[] classify(NeuralNetwork classifier) {
        List[] lists = new LinkedList[classifier.getOutputCount()];
        for(int j=0;j<lists.length;j++){
            lists[j]=new LinkedList<DataSet>();
        }
        for(int i=0;i<data.size();i++)
        {
            DataSet specData=data.get(i);
            Double[] results=classifier.processData(specData.inputs);
            for(int j=0;j<lists.length;j++){
                if(results[j]>0)
                    lists[j].add(specData);
            }
        }
        return lists;
    }
}

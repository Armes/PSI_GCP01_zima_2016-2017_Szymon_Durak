package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.networks.NeuralNetwork;
import sample.util.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    public Slider numberOfEpochsSlider;
    @FXML
    public Slider numberOfNetworksSlider;
    @FXML
    public Slider numberOfCyclesSlider;
    @FXML
    public Slider numberOfNetworksPerChartSlider;
    @FXML
    public RadioButton perceptronRadio;
    public ToggleGroup tasks;
    public RadioButton mccullochRatio;
    public RadioButton backpropagationRatio;
    public Button generateTunnelDataButton1;
    public Button generateHebbDataButton;
    public RadioButton hebbRadio;
    public CheckBox supervisedCheckbox;
    public CheckBox forgettingCheckbox;
    public RadioButton ojiRadio;
    public CheckBox multilayerCheckbox;
    public RadioButton kohonenRadio;
    public RadioButton kohonenPerceptronRadio;
    @FXML
    private Button runButton;
    @FXML
    private Button loadDataButton;
    @FXML
    private Button generateDataButton;

    File targetFile;

    NeuralNetworkLogic neuralNetworkLogic;

    public Controller()

    {
    }


    public void generateData(ActionEvent actionEvent) {
        FileChooser chooser=new FileChooser();
        File saveFile = chooser.showSaveDialog(null);
        DataGenerator dataGenerator=new DataGenerator(saveFile);
        dataGenerator.generateAndSave();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        neuralNetworkLogic=new NeuralNetworkLogic();
    }

    public void run(ActionEvent actionEvent) {
        if(targetFile==null)
            return;
        try {
            if (tasks.getSelectedToggle().equals(perceptronRadio))
                neuralNetworkLogic.runAsPerceptron(getSettings());
            if (tasks.getSelectedToggle().equals(mccullochRatio))
                neuralNetworkLogic.runAsMcCulloch(getSettings());
            if (tasks.getSelectedToggle().equals(backpropagationRatio))
                neuralNetworkLogic.runAsBackpropagating(getSettings());
            if (tasks.getSelectedToggle().equals(hebbRadio))
                neuralNetworkLogic.runAsHebbLearning(getSettings(),supervisedCheckbox.isSelected(),forgettingCheckbox.isSelected(),multilayerCheckbox.isSelected());
            if (tasks.getSelectedToggle().equals(ojiRadio))
                neuralNetworkLogic.runAsOjiLearning(getSettings(),supervisedCheckbox.isSelected(),multilayerCheckbox.isSelected());
            if(tasks.getSelectedToggle().equals(kohonenRadio))
                neuralNetworkLogic.runAsKohonenClassifier(getSettings());
            if(tasks.getSelectedToggle().equals(kohonenPerceptronRadio))
                neuralNetworkLogic.runClassifiedPerceptronLearning(getSettings());
        }
        catch (Exception ignored)
        {
            ignored.printStackTrace();
        }
    }

    private NeuronSettings getSettings() {
        NeuronSettings settings=new NeuronSettings();
        settings.cyclesPerEpoch=(int)numberOfCyclesSlider.getValue();
        settings.numberOfNeurons=(int)numberOfNetworksSlider.getValue();
        settings.numberOfEpochs=(int)numberOfEpochsSlider.getValue();
        settings.maxNeuronsOnCharts=(int)numberOfNetworksPerChartSlider.getValue();
        return settings;
    }

    public void loadData(ActionEvent actionEvent) {
        FileChooser chooser=new FileChooser();
        targetFile = chooser.showOpenDialog(null);
        neuralNetworkLogic.loadData(targetFile);
    }

    public void generateTunnelData(ActionEvent actionEvent) {
        FileChooser chooser=new FileChooser();
        File saveFile = chooser.showSaveDialog(null);
        DataGenerator dataGenerator=new TunnelDataGenerator(saveFile);
        dataGenerator.generateAndSave();
    }

    public void generateUnsupervisedLearningData(ActionEvent actionEvent) {
        FileChooser chooser=new FileChooser();
        File saveFile = chooser.showSaveDialog(null);
        DataGenerator dataGenerator=new GroupingDataGenerator(saveFile);
        dataGenerator.generateAndSave();
    }
}

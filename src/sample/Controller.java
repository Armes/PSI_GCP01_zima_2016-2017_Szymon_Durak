package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.util.DataGenerator;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Button runButton;
    @FXML
    private Button loadDataButton;
    @FXML
    private Button generateDataButton;

    File targetFile;

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

    }

    public void run(ActionEvent actionEvent) {
        if(targetFile==null)
            return;
    }

    public void loadData(ActionEvent actionEvent) {
        FileChooser chooser=new FileChooser();
        targetFile = chooser.showOpenDialog(null);

    }
}

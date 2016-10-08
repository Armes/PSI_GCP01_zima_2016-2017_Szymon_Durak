package sample.util;

import java.io.File;
import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NeuralNetworkLogic {
    private DataReader dataReader;
    private List<List<Double[]>> data;
    public NeuralNetworkLogic() {
    }

    public void loadData(File targetFile) {
        this.dataReader=new DataReader(targetFile);
        this.dataReader.readFile();
        this.data=this.dataReader.getData();
    }
}

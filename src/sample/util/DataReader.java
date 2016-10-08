package sample.util;

import java.io.*;
import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class DataReader {
    private File readFile;
    private List<List<Double[]>> data;
    public DataReader(File readFile){
        this.readFile=readFile;
    }
    public List<List<Double[]>> getData(){
        return data;
    }
    public void readFile(){
        data =null;
        try {
            InputStream file=new FileInputStream(readFile);
            InputStream input=new BufferedInputStream(file);
            ObjectInput stream=new ObjectInputStream(input);
            data =(List<List<Double[]>>)stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

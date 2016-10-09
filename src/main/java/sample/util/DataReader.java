package sample.util;

import java.io.*;
import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class DataReader {
    private File readFile;
    private List<DataSet> data;
    public DataReader(File readFile){
        this.readFile=readFile;
    }
    public List<DataSet> getData(){
        return data;
    }
    public void readFile(){
        data =null;
        try {
            InputStream file=new FileInputStream(readFile);
            InputStream input=new BufferedInputStream(file);
            ObjectInput stream=new ObjectInputStream(input);
            data =(List<DataSet>)stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }
}

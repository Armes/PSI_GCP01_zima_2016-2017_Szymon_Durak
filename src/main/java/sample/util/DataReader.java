package sample.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            FileReader reader=new FileReader(readFile);
            Gson gson=new Gson();
            Type token=new TypeToken<List<DataSet>>(){}.getType();
            String json=new String(Files.readAllBytes(Paths.get(readFile.getPath())), Charset.defaultCharset());
            data=(List<DataSet>)gson.fromJson(json,token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}

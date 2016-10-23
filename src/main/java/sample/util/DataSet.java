package sample.util;

import java.io.Serializable;

/**
 * Created by Szymon on 09.10.2016.
 */
public class DataSet implements Serializable {
    public Double[] inputs;
    public DataSet(){
        this(1);
    }

    public DataSet(int in) {
        inputs=new Double[in];
    }

}

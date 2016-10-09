package sample.util;

import java.io.Serializable;

/**
 * Created by Szymon on 09.10.2016.
 */
public class DataSet implements Serializable {
    public Double[] inputs;
    public Double[] outputs;
    public DataSet(){
        this(1);
    }

    public DataSet(int in, int out) {
        inputs=new Double[in];
        outputs=new Double[out];
    }

    public DataSet(int in) {
        this(in,1);
    }
}

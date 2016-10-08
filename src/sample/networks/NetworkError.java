package sample.networks;

import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NetworkError {
    private Double MSE;
    private Double MAPE;

    public Double getMSE() {
        return MSE;
    }

    public Double getMAPE() {
        return MAPE;
    }

    public void calculate(Double[] results,Double[] expectedResults)
    {
        double tmpMSE=0.;
        double tmpMAPE=0.;
        int length=results.length<expectedResults.length?results.length:expectedResults.length;
        for (int i = 0; i < length; i++) {
            tmpMAPE+= Math.abs((expectedResults[i]-results[i])/expectedResults[i]!=0.?expectedResults[i]:1e-100);
            tmpMSE+= Math.pow(expectedResults[i]-results[i],2.);
        }
    }
    static public NetworkError combine(List<NetworkError> errors){
        NetworkError result=new NetworkError();
        for (NetworkError error: errors
             ) {
            result.MSE+=error.MSE;
            result.MAPE+=error.MAPE;
        }
        result.MSE/=(double)errors.size();
        result.MAPE/=(double)errors.size();
        return result;
    }
}
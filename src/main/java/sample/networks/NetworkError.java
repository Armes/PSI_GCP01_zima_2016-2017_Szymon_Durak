package sample.networks;

import java.util.List;

/**
 * Created by Szymon on 08.10.2016.
 */
public class NetworkError {
    private Double MSE=0.;
    private Double MAPE=0.;

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
        int n=length;
        for (int i = 0; i < length; i++) {
            if(expectedResults[i]!=0.)
            tmpMAPE+= Math.abs((expectedResults[i]-results[i])/(expectedResults[i]));
            else
                n--;
            tmpMSE+= Math.pow(expectedResults[i]-results[i],2.);
        }
        MAPE=tmpMAPE*100./(double)(n!=0?n:1);
        MSE=tmpMSE/(double)length;
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
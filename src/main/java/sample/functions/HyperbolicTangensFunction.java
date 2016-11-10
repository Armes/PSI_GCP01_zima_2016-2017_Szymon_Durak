package sample.functions;

/**
 * Created by Szymon on 09.11.2016.
 */
public class HyperbolicTangensFunction implements ActivationFunction {
    @Override
    public double calc(double x) {
        return (2./(1.+Math.exp(-x)))-1.;
    }

    @Override
    public double derivative(double x) {
        return 1. - Math.pow(calc(x),2.);
    }
}

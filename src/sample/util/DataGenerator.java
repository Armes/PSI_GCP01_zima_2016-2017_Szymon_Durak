package sample.util;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Created by Szymon on 06.10.2016.
 */
public class DataGenerator {
    File saveFile;
    private List<Function<Double,Double[]>> expressions; //Function that accepts single Double parameter and returns 3 Double coordinates.
    private Random random;
    public static final Double delta=0.000001;
    public static final Double standardRange=1.;
    public static final Double extendedRangeMultiplier =10.;
    private ArgumentSet arguments;
    private Double noise()
    {
        return delta*random.nextGaussian();
    }
    private Double standardValue(){
        return standardRange*2.*(random.nextDouble()-0.5);
    }

    public DataGenerator(File saveFile) {
        arguments=new ArgumentSet();
        random=new Random();
        this.saveFile=saveFile;
        expressions = new ArrayList<>();
        expressions.add( //thrown object case
                t -> {
                    Double[] result= new Double[3];
                    result[0]=noise()+(arguments.force*t*Math.cos(arguments.angle)+arguments.x);//X
                    result[1]=noise()+(arguments.force*t*Math.sin(arguments.angle)+arguments.y);//Y
                    result[2]=noise()+(arguments.z-Math.pow(t,2.)*.5);//Z
                    return result;
                }
        );
        expressions.add( //thrown object case - larger area
                t -> {
                    Double[] result= new Double[3];
                    result[0]=noise()+ extendedRangeMultiplier *(arguments.force*t*Math.cos(arguments.angle)+arguments.x);//X
                    result[1]=noise()+ extendedRangeMultiplier *(arguments.force*t*Math.sin(arguments.angle)+arguments.y);//Y
                    result[2]=noise()+ extendedRangeMultiplier *(arguments.z-Math.pow(t,2.)*.5);//Z
                    return result;
                }
        );
        expressions.add( //orbiting object case
                t -> {
                    Double[] result= new Double[3];
                    result[0]=noise()+arguments.radius*Math.cos(arguments.angle+arguments.phase)*Math.cos(arguments.angleVert)+arguments.x;//X
                    result[1]=noise()+arguments.radius*Math.sin(arguments.angle+arguments.phase)*Math.cos(arguments.angleVert)+arguments.y;//Y
                    result[2]=noise()+arguments.radius*Math.sin(arguments.angleVert)+arguments.z;//Z
                    return result;
                }
        );
        expressions.add( //orbiting object case - larger area
                t -> {
                    Double[] result= new Double[3];
                    result[0]=noise()+ extendedRangeMultiplier *(arguments.radius*Math.cos(arguments.angle+arguments.phase)*Math.cos(arguments.angleVert)+arguments.x);//X
                    result[1]=noise()+ extendedRangeMultiplier *(arguments.radius*Math.sin(arguments.angle+arguments.phase)*Math.cos(arguments.angleVert)+arguments.y);//Y
                    result[2]=noise()+ extendedRangeMultiplier *(arguments.radius*Math.sin(arguments.angleVert)+arguments.z);//Z
                    return result;
                }
        );
        expressions.add( //straightforward movement case
                t -> {
                    Double[] result= new Double[3];
                    Double shift=t*standardRange*.5;
                    result[0]=noise()+Math.cos(arguments.angle)*(arguments.x+shift)*Math.cos(arguments.angleVert);//X
                    result[1]=noise()+Math.sin(arguments.angle)*(arguments.x+shift)*Math.cos(arguments.angleVert);//Y
                    result[2]=noise()+(arguments.x+shift)*Math.sin(arguments.angleVert);//Z
                    return result;
                }
        );
    }
    public void generateAndSave()
    {
        try {
            saveFile.createNewFile();
            OutputStream file=new FileOutputStream(saveFile);
            OutputStream stream=new BufferedOutputStream(file);
            ObjectOutput output=new ObjectOutputStream(stream);
            List<List<Double[]>> results=new LinkedList<>();
            for(Function<Double,Double[]> expression:expressions)
            {
                for(int i=0;i<2000;i++)
                {
                    results.add(getCoordinates(getStartingPoint(),getDt(),expression));
                }
            }
            output.writeObject(results);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Double getDt() {
        return random.nextDouble()*.1;
    }

    private Double getStartingPoint() {
        return random.nextDouble()*1.5-1.;
    }

    private List<Double[]> getCoordinates(Double start,Double dt, Function<Double,Double[]> expr){
        List<Double[]> results = new LinkedList<>();
        Double t=start;
        arguments.angle=Math.PI*random.nextDouble();
        arguments.angleVert=Math.PI*random.nextDouble();
        arguments.force=standardValue()*.1;
        arguments.phase=Math.PI*(t*0.1+random.nextDouble());
        arguments.radius=standardValue()*.5;
        arguments.x=standardValue()*.5;
        arguments.y=standardValue()*.5;
        arguments.z=standardValue()*.5;
        for(int i=0;i<6;i++)
        {
            results.add(expr.apply(t));
            t+=dt;
        }
        return results;
    }

    private class ArgumentSet {
        public Double angle;
        public Double phase;
        public Double angleVert;
        public Double radius;
        public Double force;
        public Double x;
        public Double y;
        public Double z;
    }
}

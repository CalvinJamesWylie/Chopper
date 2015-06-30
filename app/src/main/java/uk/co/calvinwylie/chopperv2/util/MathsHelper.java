package uk.co.calvinwylie.chopperv2.util;

/**
 * Created by Calvin on 24/06/2015.
 */
public class MathsHelper {

    private MathsHelper(){}

    public static float RoundClamp(float value, float min, float max){
        if(value < min) {
            value += (max - min);
        }
        if(value > max) {
            value -= (max - min);
        }
        return value;
    }
}

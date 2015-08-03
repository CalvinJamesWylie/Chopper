package uk.co.calvinwylie.chopperv2.util;

/**
 * Created by Calvin on 24/06/2015.
 */
public class MathsHelper {

    private MathsHelper(){}



    public static double RoundClamp(double value, double min, double max) {
        if(value < min) {
            value += (max - min);
        }
        if(value > max) {
            value -= (max - min);
        }
        return value;
    }

    public static float RoundClamp(float value, float min, float max){
        return (float) RoundClamp((double) value, (double) min, (double) max);
    }
    public static int RoundClamp(int value, int min, int max){
        return (int) RoundClamp((double) value, (double) min, (double) max);
    }
    public static long RoundClamp(long value, long min, long max){
        return (long) RoundClamp((double) value, (double) min, (double) max);
    }
}

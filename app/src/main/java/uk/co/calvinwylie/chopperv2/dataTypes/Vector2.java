package uk.co.calvinwylie.chopperv2.dataTypes;

import android.util.FloatMath;

/**
 * Created by Calvin on 16/04/2015.
 */
public class Vector2 {
    public float X,Y;

    public Vector2(){
        X = Y = 0.0f;
    }

    public Vector2(float x, float y){
        X = x;
        Y = y;
    }

    public Vector2(Vector2 vector){
        X = vector.X;
        Y = vector.Y;
    }

    public void set(float x, float y){
        X = x;
        Y = y;
    }

    public void set(Vector2 vector){
        X = vector.X;
        Y = vector.Y;
    }

    public static Vector2 vectorBetween(Vector2 endVector, Vector2 startVector) {
        return new Vector2(
                endVector.X - startVector.X,
                endVector.Y - startVector.Y
        );
    }

    public float length() {
        return FloatMath.sqrt(
                X * X
              + Y * Y
        );
    }

    public Vector2 scaled(float multiplier){
        return new Vector2(
                X * multiplier,
                Y * multiplier
        );
    }

    public void normalise(){

        float length = length();

        if(length != 0) {
            X /= length;
            Y /= length;
        }else{
            X = 0;
            Y = 0;
        }
    }

    public void scaleBy(float multiplier) {
        X *= multiplier;
        Y *= multiplier;
    }

    public String toString(){
        return "(" + X + ", " + Y + ")";
    }
}

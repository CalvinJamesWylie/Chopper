package uk.co.calvinwylie.chopperv2.dataTypes;

import android.util.FloatMath;

/**
 * Created by Calvin on 16/04/2015.
 */
public class Vector3 {
    public float X, Y, Z;

    public Vector3(){
        X = Y = Z = 0.0f;
    }

    public Vector3(float x, float y, float z){
        X = x;
        Y = y;
        Z = z;
    }

    public Vector3(Vector2 vector) {
        X = vector.X;
        Y = 0.0f;
        Z = vector.Y;
    }

    public void set(float x, float y, float z){
        X = x;
        Y = y;
        Z = z;
    }

    public void set(Vector2 vector) {
        X = vector.X;
        Y = 0.0f;
        Z = vector.Y;
    }

    public void set(Vector3 vector) {
        X = vector.X;
        Y = vector.Y;
        Z = vector.Z;
    }

    public float length(){
        return FloatMath.sqrt(
                  X * X
                + Y * Y
                + Z * Z
        );
    }

    public Vector3 scaled(float multiplier){

        return new Vector3(
                X * multiplier,
                Y * multiplier,
                Z * multiplier
        );
    }

    public void scaleBy(float multiplier){
        X *= multiplier;
        Y *= multiplier;
        Z *= multiplier;
    }

    public Vector3 translate(Vector3 translation){
        return new Vector3(
                X + translation.X,
                Y + translation.Y,
                Z + translation.Z
        );
    }

    public float dotProduct(Vector3 other){
        return X * other.X
             + Y * other.Y
             + Z * other.Z;
    }

    public Vector3 crossProduct(Vector3 other){
        return new Vector3(
                (Y * other.Z) - (Z * other.Y),
                (Z * other.X) - (X * other.Z),
                (X * other.Y) - (Y * other.X)
        );
    }

    public void add(Vector2 other){
        X += other.X;
        Z += other.Y;
    }
    public void add(Vector3 other){
        X += other.X;
        Y += other.Y;
        Z += other.Z;
    }

    public static Vector3 addition(Vector3 vector, Vector3 otherVector){
        return new Vector3(
            vector.X + otherVector.X,
            vector.Y + otherVector.Y,
            vector.Z + otherVector.Z
        );
    }


    public static Vector3 vectorBetween(Vector3 from, Vector3 to){
        return new Vector3(
                to.X - from.X,
                to.Y - from.Y,
                to.Z - from.Z
        );
    }

    public void normalise(){

        float length = length();

        X /= length;
        Y /= length;
        Z /= length;

    }

    public String toString(){
        return  "(" + X + ", " + Y +  ", " + Z + ")";
    }

    public boolean isZero(){
        if(X != 0.0f){
            return false;
        }
        if(Y != 0.0f){
            return false;
        }
        if(Z != 0.0f){
            return false;
        }
        return true;
    }

}

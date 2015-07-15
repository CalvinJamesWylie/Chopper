package uk.co.calvinwylie.chopperv2.dataTypes;

import android.opengl.Matrix;

/**
 * Created by Calvin on 16/04/2015.
 * Rotation is a data type for holding quaternion information.
 */
public class Rotation {
    private float m_Angle;
    private Vector3 m_Axis;


    public Rotation(){
        m_Angle = 0.0f;
        m_Axis = new Vector3(0.0f, 1.0f, 0.0f);
    }

    public Rotation(float angle, Vector3 axis) {
        m_Angle = angle;
        m_Axis = axis;
    }
    public Rotation(float angle, float x, float y, float z){
        m_Angle = angle;
        m_Axis.X = x;
        m_Axis.Y = y;
        m_Axis.Z = z;
    }

    public boolean isValid() {
        if (m_Angle != 0.0f && !m_Axis.isZero()){
            return true;
        }else{
            return false;
        }
    }

    public float getAngle() {
        return m_Angle;
    }

    public float getAxisX() {
        return m_Axis.X;
    }

    public float getAxisY() {
        return m_Axis.Y;
    }

    public float getAxisZ() {
        return m_Axis.Z;
    }

    public void set(float angle, Vector3 axis){
        m_Angle = angle;
        m_Axis = axis;
    }

    public void set(float angle, float x, float y, float z){
        m_Angle = angle;
        m_Axis.X = x;
        m_Axis.Y = y;
        m_Axis.Z = z;
    }

    public void setAngle(float angle) {
        m_Angle = angle;
    }

    public void setAxis(Vector3 axis){
        m_Axis = axis;
    }

    public void setXAxis(float x) {
        m_Axis.X = x;
    }

    public void setYAxis(float y) {
        m_Axis.Y = y;
    }

    public void setZAxis(float z) {
        m_Axis.Z = z;
    }
}

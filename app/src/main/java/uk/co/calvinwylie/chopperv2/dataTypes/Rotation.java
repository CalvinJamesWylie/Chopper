package uk.co.calvinwylie.chopperv2.dataTypes;

/**
 * Created by Calvin on 16/04/2015.
 */
public class Rotation {
    private float m_Angle, m_XAxis, m_YAxis, m_ZAxis;

    public Rotation(){
        m_Angle = m_XAxis = m_ZAxis = 0.0f;
        m_YAxis = 1.0f;
    }

    public Rotation(float angle, float xAxis, float yAxis, float zAxis){
        m_Angle = angle;
        m_XAxis = xAxis;
        m_YAxis = yAxis;
        m_ZAxis = zAxis;
    }

    public boolean isValid() {
        if (m_Angle != 0.0f && (m_XAxis != 0.0f || m_YAxis != 0.0f || m_ZAxis != 0.0f))
            return true;
        else
            return false;
    }

    public float getAngle() {
        return m_Angle;
    }

    public float getXAxis() {
        return m_XAxis;
    }

    public float getYAxis() {
        return m_YAxis;
    }

    public float getZAxis() {
        return m_ZAxis;
    }

    public void increment(){
        m_Angle++;
    }

    public void setAngle(float angle) {
        m_Angle = angle;
    }

    public void setXAxis(float xAxis) {
        m_XAxis = xAxis;
    }

    public void setYAxis(float yAxis) {
        m_YAxis = yAxis;
    }

    public void setZAxis(float zAxis) {
        m_ZAxis = zAxis;
    }
}

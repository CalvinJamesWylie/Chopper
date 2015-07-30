package uk.co.calvinwylie.chopperv2.physics;

import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;


public class Engine {
    private final static String tag = "Engine";

    private GameObject m_GameObject;
    private Vector3 m_Acceleration = new Vector3();
    private Vector3 m_Force = new Vector3();
    private Vector3 m_TempVect = new Vector3(); // TODO remove this temp
    private float m_InverseMass;
    private float m_MaxSpeed;

    public Engine(GameObject go){
        m_GameObject = go;
        m_InverseMass = 1.0f/go.getMass();
        m_MaxSpeed = go.getMaxSpeed();

    }

    public void exertForce(Vector2 vector, float multiplier) {
        m_Force.set(vector);
        m_Force.scaleBy(multiplier);
        m_Acceleration = m_Force.scaled(m_InverseMass);
    }


    public void exertForce(Vector3 vector, double deltaTime){

        m_Force.set(vector);
        m_Force.scaleBy((float)deltaTime);

        m_Acceleration = m_Force.scaled(m_InverseMass);
    }

    public Vector3 getAcceleration() {
        return m_Acceleration;
    }

}

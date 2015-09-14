package uk.co.calvinwylie.chopperv2.physics;

import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;


public class Engine {
    private final static String tag = "Engine";

    private Vector3 m_Acceleration = new Vector3();
    private Vector3 m_Force = new Vector3();
    private float m_InverseMass;

    public Engine(GameObject.PhysicalAttribs pA){
        m_InverseMass = 1.0f/pA.mass;

    }

    public void exertForce(Vector2 vector, float multiplier) {
        m_Force.set(vector);
        m_Force.scaleBy(multiplier);
        m_Acceleration = m_Force.scaled(m_InverseMass);
    }


    public void exertForce(Vector3 vector, float multiplier){

        m_Force.set(vector);
        m_Force.scaleBy(multiplier*m_InverseMass);
        m_Acceleration = m_Force;
    }

    public Vector3 getAcceleration() {
        return m_Acceleration;
    }

}

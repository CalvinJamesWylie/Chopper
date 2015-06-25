package uk.co.calvinwylie.chopperv2.physics;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;

/**
 * Created by Calvin on 24/05/2015.
 */
public class Engine {
    GameObject m_GameObject;
    Vector3 m_Acceleration = new Vector3();
    Vector3 m_Force = new Vector3();
    float m_InverseMass;
    float m_MaxSpeed;

    public Engine(GameObject go){
        m_GameObject = go;
        m_InverseMass = 1.0f/go.getMass();
        m_MaxSpeed = go.getMaxSpeed();

    }

    public void exertForce(Vector2 vector, double deltaTime){

        m_Force.set(vector);
        m_Force.scaleBy((float)deltaTime);

        m_Acceleration = m_Force.scaled(m_InverseMass); //TODO  remove mem alloc
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

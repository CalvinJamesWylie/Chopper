package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.game.Affiliation;
import uk.co.calvinwylie.chopperv2.game.BulletManager;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.physics.Dynamics;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;
import uk.co.calvinwylie.chopperv2.models.TextureType;
import uk.co.calvinwylie.chopperv2.util.MatrixHelper;


public class Helicopter extends Vehicle {

    private static final String tag = "Helicopter";

    private static final float TURN_SPEED_MULTIPLIER = 4.0f;
    private TouchHandler m_TouchHandler;
    private boolean m_Firing;
    private Vector3 m_TempVect = new Vector3(); //TODO clean this up and make members
    private Vector3 Up = new Vector3(0,1,0);
    private float m_AngleToTarget;


    public Helicopter(Context context, BulletManager bulletManager, TouchHandler touchHandler){
        super(bulletManager);
        m_TouchHandler = touchHandler;
        m_MaxSpeed = 5.0f;
        m_Mass = 1.5f;              //change this if heli isnt fast enough
        m_AirResistance = 0.75f;   //change this if heli isnt slowing down fast enough
        m_Material = new Material(TextureType.helicopter);
        m_ModelType = ModelType.helicopter;
        m_Engine = new Engine(this);
        m_Affiliation = Affiliation.Blue;
    }

    @Override
    public void update(double deltaTime) {

        m_Gun.update(deltaTime);
        if(m_TouchHandler.leftAnalogStick.isActive()) {
            m_Engine.exertForce(m_TouchHandler.leftAnalogStick.getDelta(), 2.0f);
            m_Velocity.add(m_Engine.getAcceleration());
        }
        if(m_TouchHandler.rightAnalogStick.isActive()){
            m_TargetYaw = m_TouchHandler.rightAnalogStick.getAngle();
            m_Gun.setDirection(m_TouchHandler.rightAnalogStick.getDelta(), m_TargetYaw);
            m_Firing = true;
        }else{
            m_Firing = false;
        }
        move(deltaTime);
        updateModelMatrix();
        m_ForwardVector = MatrixHelper.getColumn(3, getModelMatrix()).scaled(-1);
        m_UpVector = MatrixHelper.getColumn(2, getModelMatrix());
        m_RightVector = MatrixHelper.getColumn(1, getModelMatrix());
    }

    public void move(double deltaTime) {

        m_AngleToTarget = (float) ((m_TargetYaw - m_Yaw + (2 * Math.PI)) % (2 * Math.PI));

        if(m_AngleToTarget > Math.PI){
            m_AngleToTarget = (float)(2*Math.PI) - m_AngleToTarget;
            m_TurnSpeed = (float)(m_AngleToTarget*deltaTime*TURN_SPEED_MULTIPLIER);
            m_Yaw -= m_TurnSpeed;
        }else{
            m_TurnSpeed = (float)(m_AngleToTarget*deltaTime*TURN_SPEED_MULTIPLIER);
            m_Yaw += m_TurnSpeed;
        }

        float fireCutOffAngle = 15.0f;
        if(m_Firing && (m_AngleToTarget <= Math.toRadians(fireCutOffAngle) || m_AngleToTarget >= Math.toRadians(360 - fireCutOffAngle))){
            m_Gun.requestFire();
        }

        m_Yaw = MathsHelper.RoundClamp(m_Yaw, 0, (float) Math.PI * 2);

        if(m_Velocity.isZero()){
            return;
        }
        if (m_Velocity.lengthSquared() < Math.pow(0.01, 2)){
            m_Velocity.setToZero();
            return;
        }

        m_TempVect.set(m_Velocity);
        m_TempVect.scaleBy((float) deltaTime);
        m_Position.add(m_TempVect);
        Dynamics.calcAirResistance(m_Velocity, m_AirResistance, (float) deltaTime);
        m_Rotation.setAxis(Vector3.crossProduct(m_TempVect, Up));
        m_Rotation.setAngle(-m_Velocity.lengthSquared() * (float) (Math.PI/4)); // TODO remove magic number, controlls the amount the heli leans.
    }


    public Vector3 getForwardVector() {
        return m_ForwardVector;
    }

    public Vector3 getRightVector() {
        return m_RightVector;
    }
}

package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;

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
        m_PhysAttribs.speed = 2.0f;
        m_PhysAttribs.mass = 1.5f;
        m_PhysAttribs.airResistance = 0.2f;
        m_Material = new Material(TextureType.helicopter);
        m_ModelType = ModelType.helicopter;
        m_PhysAttribs.engine = new Engine(this.m_PhysAttribs);
        m_Affiliation = Affiliation.Blue;
    }

    @Override
    public void update(double deltaTime) {

        m_Gun.update(deltaTime);
        if(m_TouchHandler.leftAnalogStick.isActive()) {
            m_PhysAttribs.engine.exertForce(m_TouchHandler.leftAnalogStick.getDelta(), m_PhysAttribs.speed);
            m_PhysAttribs.velocity.add(m_PhysAttribs.engine.getAcceleration());
        }
        if(m_TouchHandler.rightAnalogStick.isActive()){
            m_PhysAttribs.targetYaw = m_TouchHandler.rightAnalogStick.getAngle();
            m_Gun.setDirection(m_TouchHandler.rightAnalogStick.getDelta(), m_PhysAttribs.targetYaw);
            m_Firing = true;
        }else{
            m_Firing = false;
        }

        move(deltaTime);
        updateModelMatrix();
        m_PhysAttribs.updateDirVectors();
    }

    public void move(double deltaTime) {

        m_AngleToTarget = (float) ((m_PhysAttribs.targetYaw - m_PhysAttribs.yaw + (2 * Math.PI)) % (2 * Math.PI));

        if(m_AngleToTarget > Math.PI){
            m_AngleToTarget = (float)(2*Math.PI) - m_AngleToTarget;
            m_PhysAttribs.turnSpeed = (float)(m_AngleToTarget*deltaTime*TURN_SPEED_MULTIPLIER);
            m_PhysAttribs.yaw -= m_PhysAttribs.turnSpeed;
        }else{
            m_PhysAttribs.turnSpeed = (float)(m_AngleToTarget*deltaTime*TURN_SPEED_MULTIPLIER);
            m_PhysAttribs.yaw += m_PhysAttribs.turnSpeed;
        }

        float fireCutOffAngle = 15.0f;
        if(m_Firing && (m_AngleToTarget <= Math.toRadians(fireCutOffAngle) || m_AngleToTarget >= Math.toRadians(360 - fireCutOffAngle))){
            m_Gun.requestFire();
        }

        m_PhysAttribs.yaw = MathsHelper.RoundClamp(m_PhysAttribs.yaw, 0, (float) Math.PI * 2);

        if(m_PhysAttribs.velocity.isZero()){
            return;
        }
        if (m_PhysAttribs.velocity.lengthSquared() < Math.pow(0.01, 2)){
            m_PhysAttribs.velocity.setToZero();
            return;
        }

        m_TempVect.set(m_PhysAttribs.velocity);
        m_TempVect.scaleBy((float) deltaTime);
        m_PhysAttribs.position.add(m_TempVect);
        Dynamics.calcAirResistance(m_PhysAttribs.velocity, m_PhysAttribs.airResistance);

        m_PhysAttribs.rotation.setAxis(Vector3.crossProduct(m_TempVect, Up));
        m_PhysAttribs.rotation.setAngle(-m_PhysAttribs.velocity.lengthSquared() * (float) (Math.PI/4)); // TODO remove magic number, controlls the amount the heli leans.
    }


    public Vector3 getForwardVector() {
        return m_PhysAttribs.forwardVector;
    }

    public Vector3 getRightVector() {
        return m_PhysAttribs.rightVector;
    }
}

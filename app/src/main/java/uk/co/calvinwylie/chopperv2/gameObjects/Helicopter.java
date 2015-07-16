package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.game.BulletManager;
import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.models.Mesh;
import uk.co.calvinwylie.chopperv2.models.ModelLoader;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.physics.Dynamics;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;
import uk.co.calvinwylie.chopperv2.util.TextureType;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Calvin on 02/07/2015.
 */
public class Helicopter extends Vehicle {

    private TouchHandler m_TouchHandler;
    private boolean m_Firing;
    private Vector3 m_TempVect = new Vector3(); //TODO clean this up and make members
    private Vector3 Up = new Vector3(0,1,0);
    float m_AngleToTarget;

    private static float[] VERTEX_DATA = {
            //Element Format: X,Y,Z,U,V;

            //Triangle Fan

            0.0f,   0.0f,    0.0f, 0.5f, 0.5f, //Center
            -1.0f, 0.0f, -1.0f, 0.0f, 1.0f,  //Bttm left
            1.0f, 0.0f, -1.0f, 1.0f, 1.0f,  //Bttm Right
            1.0f, 0.0f,  1.0f, 1.0f, 0.0f,  //Tp Right
            -1.0f, 0.0f,  1.0f, 0.0f, 0.0f,  //Tp Left
            -1.0f, 0.0f, -1.0f, 0.0f, 1.0f  //Bttm left
    };

    public Helicopter(Context context, BulletManager bulletManager, TouchHandler touchHandler){
        super(bulletManager);
        m_TouchHandler = touchHandler;
        m_MaxSpeed = 5.0f;
        m_Mass = 2.0f;
        m_AirResistance = -0.75f;
        m_TextureType = TextureType.heli_texture;
        m_ModelType = ModelType.helicopter;
        m_HasModel = true;
        m_Engine = new Engine(this);
        m_VertexArray = new VertexArray(VERTEX_DATA);
    }

    @Override
    public void update(double deltaTime) {
        m_Gun.update(deltaTime);
        if(m_TouchHandler.leftAnalogStick.isActive()) {
            m_Engine.exertForce(m_TouchHandler.leftAnalogStick.getDelta(), deltaTime);
            m_Velocity.add(m_Engine.getAcceleration());
        }
        if(m_TouchHandler.rightAnalogStick.isActive()){
            if(!m_TouchHandler.rightAnalogStick.getDelta().isZero()) {
                m_TargetYaw = m_TouchHandler.rightAnalogStick.getAngle();
                m_Gun.setDirection(m_TouchHandler.rightAnalogStick.getDelta(), m_TargetYaw);
                m_Firing = true;
            }
        }else{
            m_Firing = false;
        }
        move(deltaTime);
        updateModelMatrix();
    }

    public void draw() {
    }

    public void move(double deltaTime) {// TODO make velocity scale by delta time.

        m_AngleToTarget = (float) ((m_TargetYaw - m_Yaw + (2 * Math.PI)) % (2 * Math.PI));

        if(m_AngleToTarget > Math.PI){
            m_AngleToTarget = (float)(2*Math.PI) - m_AngleToTarget;
            m_TurnSpeed = m_AngleToTarget/10;
            m_Yaw -= m_TurnSpeed;
        }else{
            m_TurnSpeed = m_AngleToTarget/10;
            m_Yaw += m_TurnSpeed;
        }
        float fireRange = 15.0f;
        if(m_Firing && (m_AngleToTarget <= Math.toRadians(fireRange) || m_AngleToTarget >= Math.toRadians(360 - fireRange))){
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
        Dynamics.calcAirResistance(m_Velocity, m_AirResistance);
        m_Position.add(m_Velocity);
        m_TempVect.set(m_Velocity);
        m_Rotation.setAxis(m_TempVect.crossProduct(Up)); //TODO remove mem alloc in cross prod
        m_Rotation.setAngle(-m_Velocity.lengthSquared()*3000); // TODO remove magic number, controlls the amount the heli leans.

    }
}

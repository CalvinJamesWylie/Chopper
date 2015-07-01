package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.physics.Dynamics;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;
import uk.co.calvinwylie.chopperv2.util.TextureType;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Calvin on 16/04/2015.
 */
public class Vehicle extends GameObject {

    private String tag = this.getClass().getSimpleName();
    private Vector3 m_ForwardVector;
    private TouchHandler m_TouchHandler;
    private Engine m_Engine;
    private Gun m_Gun;
    private boolean m_Firing;

    private static final float[] VERTEX_DATA_3D = {

             0.0f, 0.0f, 0.0f, 0.5f, 0.5f, //Center             //0
            -0.6f, 0.0f, 0.5f, 0.0f, 0.5f, //left top corner    //1 //change other "1"
            -0.3f, 0.0f, 1.0f, 0.0f, 0.0f, //left tail          //2
             0.0f, 0.0f, 4.0f, 0.0f, 0.0f, //mid tail           //3
             0.3f, 0.0f, 1.0f, 0.0f, 0.0f, //right tail         //4
             0.6f, 0.0f, 0.5f, 0.0f, 0.5f, //right top corner   //5
             0.8f, 0.0f,-1.0f, 0.0f, 0.5f, //right bottom corner//6
             0.4f, 0.0f,-1.5f, 0.0f, 0.0f, //right nose         //7
             0.0f, 0.0f,-1.8f, 0.0f, 0.0f, //mid nose           //8
            -0.4f, 0.0f,-1.5f, 0.0f, 0.0f, //left nose          //9
            -0.8f, 0.0f,-1.0f, 0.0f, 0.0f, //left bottom corner //10
            -0.6f, 0.0f, 0.5f, 0.0f, 0.5f, //left top corner    //1 //change other "1"

    };


    private boolean scaled = false;

    public Vehicle(GameLogic gameLogic, TouchHandler touchHandler){

        m_TouchHandler = touchHandler;
        m_MaxSpeed = 5.0f;
        m_Mass = 2.0f;
        m_AirResistance = -0.75f;
        m_VertexArray = new VertexArray(VERTEX_DATA_3D);
        m_Engine = new Engine(this);
        m_TextureType = TextureType.orange;
        m_Gun = new Gun(this, gameLogic);
    }

    public void update(double deltaTime){
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

    public  void draw(){
        glDrawArrays(GL_TRIANGLE_FAN, 0, 12);
    }

    private Vector3 tempvect = new Vector3(); //TODO clean this up and make members
    private Vector3 Up = new Vector3(0,1,0);
    float angleToTarget;
    public void move(double deltaTime){ // TODO make velocity scale by delta time.

        angleToTarget = (float) ((m_TargetYaw - m_Yaw + (2 * Math.PI)) % (2 * Math.PI));

        if(angleToTarget > Math.PI){
            angleToTarget = (float)(2*Math.PI) - angleToTarget;
            m_TurnSpeed = angleToTarget/10;
            m_Yaw -= m_TurnSpeed;
        }else{
            m_TurnSpeed = angleToTarget/10;
            m_Yaw += m_TurnSpeed;
        }
        float fireRange = 15.0f;
        if(m_Firing && (angleToTarget <= Math.toRadians(fireRange) || angleToTarget >= Math.toRadians(360 - fireRange))){
            m_Gun.requestFire();
        }

        m_Yaw = MathsHelper.RoundClamp(m_Yaw, 0, (float)Math.PI*2);

        Log.i(tag, "" + m_Yaw);

        if(m_Velocity.isZero()){
            return;
        }
        if (m_Velocity.lengthSquared() < Math.pow(0.01, 2)){
            m_Velocity.setToZero();
            return;
        }
        Dynamics.calcAirResistance(m_Velocity, m_AirResistance);
        m_Position.add(m_Velocity);
        tempvect.set(m_Velocity);
        m_Rotation.setAxis(tempvect.crossProduct(Up)); //TODO remove mem alloc in cross prod
        m_Rotation.setAngle(-m_Velocity.lengthSquared()*3000); // TODO remove magic number, controlls the amount the heli leans.

    }

}
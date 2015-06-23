package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.physics.Dynamics;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Calvin on 16/04/2015.
 */
public class Vehicle extends GameObject {

    private String tag = this.getClass().getSimpleName();

    private static final int BYTES_PER_FLOAT = 4;

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
                                     + TEXTURE_COORDINATES_COMPONENT_COUNT)
                                     * BYTES_PER_FLOAT;

    private TouchHandler m_TouchHandler;
    private Engine m_Engine;

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

    public Vehicle(Context context, TouchHandler touchHandler){

        m_TouchHandler = touchHandler;
        m_MaxSpeed = 5.0f;
        m_Mass = 2.0f;
        m_AirResistance = -0.75f;
        m_VertexArray = new VertexArray(VERTEX_DATA_3D);
        m_Engine = new Engine(this);
    }

    public void loadTexture(Context context){//TODO fill this.
        m_Texture = TextureHelper.loadTexture(context, R.drawable.orange);
    }

    public void update(double deltaTime){

            Log.i(tag, ""+m_Velocity.length());

        if(m_TouchHandler.leftAnalogStick.isActive()) {
            m_Engine.exertForce(m_TouchHandler.leftAnalogStick.getDelta(), deltaTime);
            m_Velocity.add(m_Engine.getAcceleration());
        }
        if(m_TouchHandler.rightAnalogStick.isActive()){
            float rot = m_TouchHandler.rightAnalogStick.getAngle();
            m_Rotation = new Rotation((float)Math.toDegrees(rot), 0.0f, 1.0f, 0.0f);
        }
        move();
        updateModelMatrix();
    }

    public  void draw(){
        glDrawArrays(GL_TRIANGLE_FAN, 0, 12);
    }


    public void move(){
        if(m_Velocity.isZero()){
            return;
        }
        if (m_Velocity.length() < 0.01){
            m_Velocity.setToZero();
            return;
        }
        Dynamics.calcAirResistance(m_Velocity, m_AirResistance);
        m_Position.add(m_Velocity);

    }

}
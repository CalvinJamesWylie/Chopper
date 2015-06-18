package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;

import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Calvin on 16/04/2015.
 */
public abstract class GameObject {
    //Log strings
    private final String tag = this.getClass().getSimpleName();
    private String functionTag;

    //Physical attibutes

    protected Rotation  m_Rotation;

    protected Vector3   m_Position;
    protected Vector3   m_Velocity;

    protected float m_MaxSpeed;
    protected float m_Mass;
    protected float m_AirResistance;

    //Model attributes
    private final float[] m_ModelMatrix = new float[16];

    protected int m_Texture = -1;

    protected VertexArray m_VertexArray;
    private float[] VERTEX_DATA = {};


    private int m_Model; //TODO do something with these values, make values for the different ones
    private int m_Shader;

    public GameObject(){
        setIdentityM(m_ModelMatrix, 0);

        m_Position = new Vector3();             //empty Vector constructor sets values to 0;
        m_Velocity = new Vector3();

        m_Rotation = new Rotation();           //empty Rotation constructor sets values to 0 bar the yAxis to ensure no NaNs;

        m_MaxSpeed = 0.0f;

        m_VertexArray = new VertexArray(VERTEX_DATA);

        updateModelMatrix();
    }

    public GameObject(
            Vector3 position,
            Rotation rotation,
            Vector3 velocity,
            float speed
    ){
        m_Position = position;
        m_Rotation = rotation;

        m_Velocity = velocity;
        m_MaxSpeed = speed;

        m_VertexArray = new VertexArray(VERTEX_DATA);

        updateModelMatrix();
    }

    public abstract void update(double deltaTime);

    public abstract void draw();

    public abstract void loadTexture(Context context);

    public int getTexture(){
        return m_Texture;
    }

    public VertexArray getVertexData(){
        return m_VertexArray;
    }

    public void updateModelMatrix() {

        functionTag = "updateModelMatrix";

        setIdentityM(m_ModelMatrix, 0);

        translateM(m_ModelMatrix, 0, m_Position.X, m_Position.Y, m_Position.Z);

        if(m_Rotation.isValid()){
            Matrix.rotateM(m_ModelMatrix, 0, m_Rotation.getAngle(), m_Rotation.getXAxis(), m_Rotation.getYAxis(), m_Rotation.getZAxis());
        }else{
            //Log.e(tag + " " + functionTag, "either angle or axis is set to zero");
        }
    }

    public void setVelocity(Vector3 velocity){
        velocity.scaleBy(m_MaxSpeed);
        m_Velocity = velocity;
    }

    public void setPosition(Vector3 position){
        m_Position = position;
    }

    public Vector3 getPosition(){
        return m_Position;
    }

    public void setRotation(Rotation rotation){
        m_Rotation = rotation;
    }

    public Rotation getRotation(){
        return m_Rotation;
    }

    public float getMass(){
        return m_Mass;
    }

    public float getMaxSpeed(){
        return m_MaxSpeed;
    }

    public float[] getModelMatrix(){
        return m_ModelMatrix;
    }


}

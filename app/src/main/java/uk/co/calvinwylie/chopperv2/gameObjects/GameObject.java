package uk.co.calvinwylie.chopperv2.gameObjects;

import android.opengl.Matrix;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.game.Affiliation;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;

import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

public abstract class GameObject {
    //Log strings
    private final String tag = this.getClass().getSimpleName();
    private String functionTag;

    //Physical attibutes

    protected Vector3 m_ForwardVector = new Vector3(0,0,-1);
    protected Vector3 m_UpVector = new Vector3(0,1,0);
    protected Vector3 m_RightVector = new Vector3(1,0,0);
    protected Rotation  m_Rotation;
    protected Vector3 m_Scale = new Vector3(1.0f, 1.0f, 1.0f);
    protected float m_Yaw = 0.0f;
    protected float m_TargetYaw = 0.0f;
    protected float m_TurnSpeed = (float)Math.PI/180;

    protected Vector3   m_Position;
    protected Vector3   m_Velocity;

    protected float m_MaxSpeed;
    protected float m_Mass;
    protected float m_AirResistance;

    //Model attributes
    private final float[] m_ModelMatrix = new float[16];
    protected Material m_Material;
    protected boolean m_Visible = true;
    protected ModelType m_ModelType;
    protected float m_CollisionRadius = 1.0f;

    protected Affiliation m_Affiliation = Affiliation.Default;

    public GameObject(){
        setIdentityM(m_ModelMatrix, 0);

        m_Position = new Vector3();             //empty Vector constructor sets values to 0;
        m_Velocity = new Vector3();
        m_Rotation = new Rotation();           //empty Rotation constructor sets values to 0 bar the yAxis to ensure no NaNs;

        m_MaxSpeed = 0.0f;

        m_Material = new Material(TextureType.check);

        updateModelMatrix();
    }

    public abstract void update(double deltaTime);

    public Material getMaterial(){
        return m_Material;
    }

    public void updateModelMatrix() {

        functionTag = "updateModelMatrix";

        setIdentityM(m_ModelMatrix, 0);

        translateM(m_ModelMatrix, 0, m_Position.X, m_Position.Y, m_Position.Z);

        Matrix.scaleM(m_ModelMatrix, 0, m_Scale.X, m_Scale.Y, m_Scale.Z);

        if(m_Rotation.isValid()){
            Matrix.rotateM(m_ModelMatrix, 0, m_Rotation.getAngle(), m_Rotation.getAxisX(), m_Rotation.getAxisY(), m_Rotation.getAxisZ());
        }else{
            //Log.e(tag + " " + functionTag, "either angle or axis is set to zero");
        }
        Matrix.rotateM(m_ModelMatrix, 0, (float)Math.toDegrees(m_Yaw), 0.0f, 1.0f, 0.0f);
    }

    public void setVelocity(Vector3 velocity){
        velocity.scaleBy(m_MaxSpeed);
        m_Velocity = velocity;
    }

    public void setPosition(Vector3 position){
        m_Position = position;
    }

    public void setPosition(float x, float y, float z){
        m_Position.X = x;
        m_Position.Y = y;
        m_Position.Z = z;
    }

    public Vector3 getPosition(){
        return m_Position;
    }

    public void setRotation(Rotation rotation){
        m_Rotation = rotation;
    }

    public void setRotation(float angle, float xAxis, float yAxis, float zAxis){
        m_Rotation.setAngle(angle);
        m_Rotation.setXAxis(xAxis);
        m_Rotation.setYAxis(yAxis);
        m_Rotation.setZAxis(zAxis);
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

    public ModelType getModelType() {
        return m_ModelType;
    }

    public Vector3 getVelocity() {
        return m_Velocity;
    }

    public float getCollisionRadius() {
        return m_CollisionRadius;
    }

    public boolean isVisible() {
        return m_Visible;
    }

    public Affiliation getAffiliation() {
        return m_Affiliation;
    }

    public float getScale() {
        return m_Scale.length();
    }
}

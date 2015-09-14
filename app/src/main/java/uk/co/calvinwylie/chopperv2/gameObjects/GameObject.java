package uk.co.calvinwylie.chopperv2.gameObjects;

import android.opengl.Matrix;

import uk.co.calvinwylie.chopperv2.ai.Controller;
import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.game.Affiliation;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.util.MatrixHelper;

import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

public abstract class GameObject {
    //Log strings
    private final String tag = this.getClass().getSimpleName();
    private String functionTag;

    //Physical attibutes
    public class PhysicalAttribs {
        public Vector3 forwardVector = new Vector3(0, 0, -1);
        public Vector3 upVector = new Vector3(0, 1, 0);
        public Vector3 rightVector = new Vector3(1, 0, 0);
        public Rotation rotation;
        public Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);

        public float yaw = 0.0f;
        public float targetYaw = 0.0f;
        public float turnSpeed = (float) Math.PI / 180;

        public Vector3 position;
        public Vector3 velocity;

        public float speed;
        public float mass;          //change this if heli isnt fast enough
        public float airResistance; //change this if heli isnt slowing down fast enough
        public Engine engine;

        public void updateDirVectors(){
            MatrixHelper.getColumn(forwardVector, 3, getModelMatrix());
            forwardVector.scaleBy(-1);
            MatrixHelper.getColumn(upVector,2, getModelMatrix());
            MatrixHelper.getColumn(rightVector, 1, getModelMatrix());
        }
    }

    protected PhysicalAttribs m_PhysAttribs = new PhysicalAttribs();

    //Model attributes
    private final float[] m_ModelMatrix = new float[16];
    protected Material m_Material;
    protected boolean m_Visible = true;
    protected ModelType m_ModelType;
    protected float m_CollisionRadius = 1.0f;

    protected Affiliation m_Affiliation = Affiliation.Default;
    protected Controller m_Controller = null;

    public GameObject(){
        setIdentityM(m_ModelMatrix, 0);

        m_PhysAttribs.position = new Vector3();             //empty Vector constructor sets values to 0;
        m_PhysAttribs.velocity = new Vector3();
        m_PhysAttribs.rotation = new Rotation();           //empty Rotation constructor sets values to 0 bar the yAxis to ensure no NaNs;

        m_PhysAttribs.speed = 0.0f;

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

        translateM(m_ModelMatrix, 0, m_PhysAttribs.position.X, m_PhysAttribs.position.Y, m_PhysAttribs.position.Z);

        Matrix.scaleM(m_ModelMatrix, 0, m_PhysAttribs.scale.X, m_PhysAttribs.scale.Y, m_PhysAttribs.scale.Z);

        if(m_PhysAttribs.rotation.isValid()){
            Matrix.rotateM(m_ModelMatrix, 0, m_PhysAttribs.rotation.getAngle(), m_PhysAttribs.rotation.getAxisX(), m_PhysAttribs.rotation.getAxisY(), m_PhysAttribs.rotation.getAxisZ());
        }else{
            //Log.e(tag + " " + functionTag, "either angle or axis is set to zero");
        }
        Matrix.rotateM(m_ModelMatrix, 0, (float)Math.toDegrees(m_PhysAttribs.yaw), 0.0f, 1.0f, 0.0f);
    }

    public void setVelocity(Vector3 velocity){
        m_PhysAttribs.velocity = velocity;
    }

    public void setPosition(Vector3 position){
        m_PhysAttribs.position = position;
    }

    public void setPosition(float x, float y, float z){
        m_PhysAttribs.position.X = x;
        m_PhysAttribs.position.Y = y;
        m_PhysAttribs.position.Z = z;
    }

    public Vector3 getPosition(){
        return m_PhysAttribs.position;
    }

    public void setRotation(Rotation rotation){
        m_PhysAttribs.rotation = rotation;
    }

    public void setRotation(float angle, float xAxis, float yAxis, float zAxis){
        m_PhysAttribs.rotation.setAngle(angle);
        m_PhysAttribs.rotation.setXAxis(xAxis);
        m_PhysAttribs.rotation.setYAxis(yAxis);
        m_PhysAttribs.rotation.setZAxis(zAxis);
    }

    public Rotation getRotation(){
        return m_PhysAttribs.rotation;
    }

    public float getMass(){
        return m_PhysAttribs.mass;
    }

    public float getSpeed(){
        return m_PhysAttribs.speed;
    }

    public float[] getModelMatrix(){
        return m_ModelMatrix;
    }

    public ModelType getModelType() {
        return m_ModelType;
    }

    public Vector3 getVelocity() {
        return m_PhysAttribs.velocity;
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
        return m_PhysAttribs.scale.length();
    }

    public PhysicalAttribs getPhysicalAttribs(){
        return m_PhysAttribs;
    }

    public Controller getController(){
        return m_Controller;
    }
}

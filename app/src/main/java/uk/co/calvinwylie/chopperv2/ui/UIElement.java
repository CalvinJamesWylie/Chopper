package uk.co.calvinwylie.chopperv2.ui;

import android.content.Context;
import android.opengl.Matrix;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Calvin on 15/06/2015.
 */
public abstract class UIElement {

    private final String tag = this.getClass().getSimpleName();

    static float XScale = 8f;
    static float YScale = 8f;

    private static final float[] VERTEX_DATA = {
            //Element Format: X,Y,Z,U,V;

            //Triangle Fan

             0.0f,   0.0f,    0.0f, 0.5f, 0.5f, //Center
            -XScale, 0.0f, -YScale, 0.0f, 1.0f,  //Bttm left
             XScale, 0.0f, -YScale, 1.0f, 1.0f,  //Bttm Right
             XScale, 0.0f,  YScale, 1.0f, 0.0f,  //Tp Right
            -XScale, 0.0f,  YScale, 0.0f, 0.0f,  //Tp Left
            -XScale, 0.0f, -YScale, 0.0f, 1.0f  //Bttm left
    };


    private final float[] m_ModelMatrix = new float[16];

    protected VertexArray m_VertexArray;
    protected Rotation m_Rotation;
    protected Vector3 m_Position;
    protected int m_Texture;
    protected float m_Width, m_Height;
    protected int m_TextureResourceId;
    protected boolean m_Centered = true;
    protected boolean m_Visible = false;

    public UIElement(){
        setIdentityM(m_ModelMatrix, 0);

        m_Position = new Vector3();             //empty Vector constructor sets values to 0;
        m_Rotation = new Rotation();           //empty Rotation constructor sets values to 0 bar the yAxis to ensure no NaNs;

        m_VertexArray = new VertexArray(VERTEX_DATA);

        updateModelMatrix();
    }

    public VertexArray getVertexData(){
        return m_VertexArray;
    }

    public void loadTexture(Context context) {
        m_Texture = TextureHelper.loadTexture(context, m_TextureResourceId);
    }

    public int getTexture(){
        return m_Texture;
    }


    public void draw() {
        if(m_Visible) {
            glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        }
    }

    public void setCentered(boolean centered){
        m_Centered = centered;
    }

    public void setPosition(Vector3 position){
        m_Position = position;
        updateModelMatrix();
    }


    public void setRotation(float angle, float xAxis, float yAxis, float zAxis){
        m_Rotation.setAngle(angle);
        m_Rotation.setXAxis(xAxis);
        m_Rotation.setYAxis(yAxis);
        m_Rotation.setZAxis(zAxis);
        updateModelMatrix();
    }
    public void setRotation(Rotation rotation){
        m_Rotation = rotation;
        updateModelMatrix();
    }

    public float[] getModelMatrix() {
        return m_ModelMatrix;
    }

    public void updateModelMatrix() {

        setIdentityM(m_ModelMatrix, 0);

        translateM(m_ModelMatrix, 0, m_Position.X, m_Position.Y, m_Position.Z);

        if(m_Rotation.isValid()){
            Matrix.rotateM(m_ModelMatrix, 0, m_Rotation.getAngle(), m_Rotation.getAxisX(), m_Rotation.getAxisY(), m_Rotation.getAxisZ());
        }else{
            //Log.e(tag + " " + functionTag, "either angle or axis is set to zero");
        }
    }

    public void setVisible(boolean visible) {
        m_Visible = visible;
    }
}

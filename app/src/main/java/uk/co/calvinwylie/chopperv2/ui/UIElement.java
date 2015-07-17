package uk.co.calvinwylie.chopperv2.ui;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.TextureType;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Calvin on 15/06/2015.
 */
public abstract class UIElement extends GameObject{

    private final String tag = this.getClass().getSimpleName();

    static float XScale = 1f;
    static float YScale = 1f;

    private static float[] VERTEX_DATA = {
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

    public TextureType getTexture(){
        return m_Material.getTextureType();
    }


    public void draw() {
        if(m_Visible) {
            glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        }
    }

    public void setCentered(boolean centered) {
        m_Centered = centered;
    }

    public void setVisible(boolean visible) {
        m_Visible = visible;
    }
}

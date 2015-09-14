package uk.co.calvinwylie.chopperv2.ui;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.Mesh;
import uk.co.calvinwylie.chopperv2.models.ModelLoader;
import uk.co.calvinwylie.chopperv2.models.ModelType;
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

    private final float[] m_ModelMatrix = new float[16];
    protected boolean m_Visible = false;

    public UIElement(){
        setIdentityM(m_ModelMatrix, 0);
        m_ModelType = ModelType.lamina;
        m_PhysAttribs.position = new Vector3();             //empty Vector constructor sets values to 0;
        m_PhysAttribs.rotation = new Rotation();           //empty Rotation constructor sets values to 0 bar the yAxis to ensure no NaNs;
        updateModelMatrix();
    }


    public TextureType getTexture(){
        return m_Material.getTextureType();
    }

    public void setTexture(TextureType textureType){
        m_Material.setTextureType(textureType);
    }
}

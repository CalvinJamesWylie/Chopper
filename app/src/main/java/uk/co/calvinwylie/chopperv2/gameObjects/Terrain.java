package uk.co.calvinwylie.chopperv2.gameObjects;

import android.opengl.GLES20;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;

/**
 * Created by Calvin on 12/06/2015.
 */
public class Terrain extends GameObject {

    public Terrain(){
        m_Material = new Material(TextureType.check);
        m_ModelType = ModelType.lamina;

        m_Scale = new Vector3(256, 1, 256);
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0,6);
    }

//    @Override
//    public void loadTexture(Context context) {
//        m_Texture = TextureHelper.loadTexture(context, R.drawable.check);
//
//    }
}

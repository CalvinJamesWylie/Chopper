package uk.co.calvinwylie.chopperv2.gameObjects;

import android.opengl.GLES20;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class Terrain extends GameObject {

    public Terrain(){
        m_Material = new Material(TextureType.check);
        m_Material.setSpecularIntensity(1);
        m_Material.setSpecularPower(2);
        m_ModelType = ModelType.lamina;

        m_Scale = new Vector3(256, 1, 256);
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {

    }

//    @Override
//    public void loadTexture(Context context) {
//        m_Texture = TextureHelper.loadTexture(context, R.drawable.check);
//
//    }
}

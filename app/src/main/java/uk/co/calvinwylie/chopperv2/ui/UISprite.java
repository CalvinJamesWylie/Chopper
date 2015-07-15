package uk.co.calvinwylie.chopperv2.ui;

import android.content.Context;
import android.opengl.GLES20;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;
import uk.co.calvinwylie.chopperv2.util.TextureType;

/**
 * Created by Calvin on 15/06/2015.
 */
public class UISprite extends UIElement {

    public UISprite(Vector3 position, Rotation rotation, float width, float height, TextureType textureType){
        m_Position = position;
        m_Rotation = rotation;
        m_Scale.set(width, 0, height);
        m_TextureType = textureType;
        updateModelMatrix();
    }

    public void update(){
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {
        updateModelMatrix();
    }

    @Override
    public void draw(int positionAttribLocation, int textureAttribLocation) {

    }
}

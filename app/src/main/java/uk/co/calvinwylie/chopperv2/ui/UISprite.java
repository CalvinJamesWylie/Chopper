package uk.co.calvinwylie.chopperv2.ui;

import android.content.Context;
import android.opengl.GLES20;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;

/**
 * Created by Calvin on 15/06/2015.
 */
public class UISprite extends UIElement {

    public UISprite(int textureResourceId, int width, int height){
        m_Width = width;
        m_Height = height;
        m_TextureResourceId = textureResourceId;
    }

    @Override
    void loadTexture(Context context) {
        m_Texture = TextureHelper.loadTexture(context, m_TextureResourceId);
    }

    @Override
    void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }


}

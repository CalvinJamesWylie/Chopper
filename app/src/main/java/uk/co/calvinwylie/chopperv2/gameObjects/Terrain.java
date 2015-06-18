package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;
import android.opengl.GLES20;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;

/**
 * Created by Calvin on 12/06/2015.
 */
public class Terrain extends GameObject {

    private static final float[] VERTEX_DATA = {
            //Element Format: X,Y,U,V;

            //TableTop Triangle Fan

                0.0f, 0.0f,    0.0f, 0.5f, 0.5f, //Center
            -1280.0f, 0.0f,-1280.0f, 0.0f, 1.0f,  //Bttm left
             1280.0f, 0.0f,-1280.0f, 1.0f, 1.0f,  //Bttm Right
             1280.0f, 0.0f, 1280.0f, 1.0f, 0.0f,  //Tp Right
            -1280.0f, 0.0f, 1280.0f, 0.0f, 0.0f,  //Tp Left
            -1280.0f, 0.0f,-1280.0f, 0.0f, 1.0f  //Bttm left
    };

    public Terrain(){
        m_VertexArray = new VertexArray(VERTEX_DATA);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0,6);
    }

    @Override
    public void loadTexture(Context context) {
        m_Texture = TextureHelper.loadTexture(context, R.drawable.check);

    }
}

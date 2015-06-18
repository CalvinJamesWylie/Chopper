package uk.co.calvinwylie.chopperv2.ui;

import android.opengl.GLES10;
import android.opengl.Matrix;

/**
 * Created by Calvin on 15/06/2015.
 */
public class UICamera {
    private final float[] m_OrthoMatrix = new float[16];

    public UICamera(){

    }

    public void onSurfaceChanged(int width, int height){
        Matrix.orthoM(m_OrthoMatrix, 0, 0.0f, width, 0.0f, height, 1.0f, 1000.0f);
    }
}

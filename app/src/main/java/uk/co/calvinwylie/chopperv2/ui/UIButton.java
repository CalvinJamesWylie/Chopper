package uk.co.calvinwylie.chopperv2.ui;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by Calvin on 15/06/2015.
 */
public class UIButton extends UIElement {
    @Override
    void loadTexture(Context context) {
        //TODO do eet.
    }

    @Override
    void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}

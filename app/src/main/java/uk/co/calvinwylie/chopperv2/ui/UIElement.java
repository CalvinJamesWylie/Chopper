package uk.co.calvinwylie.chopperv2.ui;

import android.content.Context;

import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;

/**
 * Created by Calvin on 15/06/2015.
 */
public abstract class UIElement {

    private static final float[] VERTEX_DATA = {
            //Element Format: X,Y,U,V;

            //Triangle Fan

            0.0f,  0.0f, 0.5f, 0.5f, //Center
            -1.0f, -1.0f, 0.0f, 1.0f,  //Bttm left
            1.0f, -1.0f, 1.0f, 1.0f,  //Bttm Right
            1.0f,  1.0f, 1.0f, 0.0f,  //Tp Right
            -1.0f,  1.0f, 0.0f, 0.0f,  //Tp Left
            -1.0f, -1.0f, 0.0f, 1.0f  //Bttm left
    };


    protected VertexArray m_VertexArray;
    protected int m_Texture;
    protected int m_Width, m_Height;
    protected int m_TextureResourceId;
    protected boolean m_Centered = false;

    public UIElement(){
        m_VertexArray = new VertexArray(VERTEX_DATA);

    }

    abstract void loadTexture(Context context);

    abstract void draw();

    public void setCentered(boolean centered){
        m_Centered = centered;
    }
}

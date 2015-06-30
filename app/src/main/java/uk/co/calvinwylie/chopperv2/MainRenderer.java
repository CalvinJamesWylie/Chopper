package uk.co.calvinwylie.chopperv2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.ArrayList;

import static android.opengl.GLES20.GL_BACK;
import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LEQUAL;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TEXTURE;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClearDepthf;
import static android.opengl.GLES20.glCullFace;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glDepthMask;
import static android.opengl.GLES20.glDepthRangef;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glTexParameterf;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.game.GamePacket;
import uk.co.calvinwylie.chopperv2.gameObjects.Camera;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.shaderPrograms.ColorShaderProgram;
import uk.co.calvinwylie.chopperv2.shaderPrograms.TextureShaderProgram;
import uk.co.calvinwylie.chopperv2.ui.UIElement;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;


/**
 * Created by Calvin on 16/04/2015.
 */
public class MainRenderer implements GLSurfaceView.Renderer {

    private String tag = this.getClass().getSimpleName();
    private TextureShaderProgram m_TextureProgram;
    private ColorShaderProgram m_ColorProgram;

    private Context m_Context;                              //used for shaders
    private GamePacket m_GamePack;


    // private ArrayList<Model> m_ModelList;

    public MainRenderer(Context context, GamePacket gamePacket){
        m_Context = context;
        m_GamePack = gamePacket;


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.0f, 1.0f, 0.3f, 1.0f);
        //glEnable(GL_CULL_FACE);
        //glCullFace(GL_BACK);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glDepthMask(true);
        glDepthRangef(0.0f, 1.0f);
        glClearDepthf(1.0f);
//
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        m_ColorProgram = new ColorShaderProgram(m_Context);
        m_TextureProgram = new TextureShaderProgram(m_Context);

        for (GameObject go: m_GamePack.m_RenderList){
            go.loadTexture(m_Context);
        }

        for (UIElement uie: m_GamePack.m_UIRenderList){
            uie.loadTexture(m_Context);
        }
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height); //Set the viewport to fill the entire surface.
        m_GamePack.m_Camera.onSurfaceChanged(width, height);
        m_GamePack.m_UICamera.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //Log.i(tag, "onDraw");
        glClear(GL_COLOR_BUFFER_BIT);

        m_GamePack.m_Camera.onDrawFrame();
        for(GameObject go: m_GamePack.m_RenderList){
            if(go.getTexture() == -1){
                go.loadTexture(m_Context);
            }
            m_TextureProgram.useProgram();
            m_TextureProgram.setUniforms(m_GamePack.m_Camera.getMVPMatrix(go.getModelMatrix()), go.getTexture());
            m_TextureProgram.bindData(go.getVertexData());
            go.draw();
        }

        m_GamePack.m_UICamera.onDrawFrame();
        for (UIElement uie: m_GamePack.m_UIRenderList){
            m_TextureProgram.useProgram();
            m_TextureProgram.setUniforms(m_GamePack.m_UICamera.getMVPMatrix(uie.getModelMatrix()), uie.getTexture());
            m_TextureProgram.bindData(uie.getVertexData());
            uie.draw();
        }
    }
}

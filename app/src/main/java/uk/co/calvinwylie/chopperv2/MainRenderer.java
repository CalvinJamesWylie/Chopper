package uk.co.calvinwylie.chopperv2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.ArrayList;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_FRONT;
import static android.opengl.GLES20.GL_LEQUAL;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_BACK;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClearDepthf;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glDepthMask;
import static android.opengl.GLES20.glDepthRangef;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.glCullFace;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import uk.co.calvinwylie.chopperv2.game.GamePacket;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.models.ModelManager;
import uk.co.calvinwylie.chopperv2.ui.UIElement;
import uk.co.calvinwylie.chopperv2.models.TextureManager;
import uk.co.calvinwylie.chopperv2.util.FrameRateLogger;


public class MainRenderer implements GLSurfaceView.Renderer {

    private static String tag = "MainRenderer";

    private TextureManager m_TextureManager = new TextureManager();
    private ModelManager m_ModelManager = new ModelManager();

    private Context m_Context;                              //used for shaders
    private GamePacket m_GamePack;

    private FrameRateLogger m_FRL = new FrameRateLogger(tag);

    public MainRenderer(Context context, GamePacket gamePacket){
        m_Context = context;
        m_GamePack = gamePacket;
        m_FRL.setActive(true);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.0f, 1.0f, 0.3f, 1.0f);
        glEnable(GL_CULL_FACE); //TODO fix camera.
        glCullFace(GL_BACK);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glDepthMask(true);
        glDepthRangef(0.0f, 1.0f);
        glClearDepthf(1.0f);
//
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        m_GamePack.initShaders();
        m_GamePack.getPhongShader().setLightHandles();

        m_TextureManager.loadTextures(m_Context);
        m_ModelManager.loadModels(m_Context);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height); //Set the viewport to fill the entire surface.
        m_GamePack.m_Camera.onSurfaceChanged(width, height);
        m_GamePack.m_UICamera.onSurfaceChanged(width, height);
    }

    private ArrayList<GameObject> m_ClonedRenderList;

    @Override
    public void onDrawFrame(GL10 gl) {

        m_FRL.tick();

        glClear(GL_COLOR_BUFFER_BIT);

        m_GamePack.m_Camera.onDrawFrame();

        m_ClonedRenderList = (ArrayList<GameObject>)m_GamePack.m_RenderList.clone();

        int i = 0;
        GameObject go;
        for(i = 0; i < m_ClonedRenderList.size(); i++){
            go = m_ClonedRenderList.get(i);
            if(go.isVisible()) {
                m_GamePack.getPhongShader().useProgram();
                m_GamePack.getPhongShader().setUniforms(m_GamePack.m_Camera.getMVPMatrix(go.getModelMatrix()), go.getModelMatrix(), m_GamePack.m_Camera.getPosition(), m_TextureManager, go.getMaterial());
                m_ModelManager.getModel(go.getModelType()).draw(m_GamePack.getPhongShader().getPositionAttributeLocation(),
                        m_GamePack.getPhongShader().getTextureCoordinatesAttributeLocation(),
                        m_GamePack.getPhongShader().getNormalAttributeLocation());
            }
        }


        m_GamePack.m_UICamera.onDrawFrame();
        //for (UIElement uie: m_GamePack.m_UIRenderList){
        for (i = 0; i < m_GamePack.m_UIRenderList.size(); i++){
            go = m_GamePack.m_UIRenderList.get(i);
            m_GamePack.getTextureShader().useProgram();
            m_GamePack.getTextureShader().setUniforms(m_GamePack.m_UICamera.getMVPMatrix(go.getModelMatrix()), m_TextureManager.getTexture(go.getMaterial().getTextureType()));
            m_ModelManager.getModel(go.getModelType()).draw(m_GamePack.getTextureShader().getPositionAttributeLocation(),
                    m_GamePack.getTextureShader().getTextureCoordinatesAttributeLocation(),
                    m_GamePack.getTextureShader().getNormalAttributeLocation());
        }



    }

}

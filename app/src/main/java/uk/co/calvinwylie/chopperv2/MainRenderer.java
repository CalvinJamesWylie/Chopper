package uk.co.calvinwylie.chopperv2;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.util.ArrayList;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LEQUAL;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClearDepthf;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glDepthMask;
import static android.opengl.GLES20.glDepthRangef;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import uk.co.calvinwylie.chopperv2.game.GamePacket;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.models.ModelManager;
import uk.co.calvinwylie.chopperv2.ui.UIElement;
import uk.co.calvinwylie.chopperv2.models.TextureManager;


public class MainRenderer implements GLSurfaceView.Renderer {

    private String tag = this.getClass().getSimpleName();

    private TextureManager m_TextureManager = new TextureManager();
    private ModelManager m_ModelManager = new ModelManager();

    private Context m_Context;                              //used for shaders
    private GamePacket m_GamePack;

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

        //Log.i(tag, "onDraw");
        glClear(GL_COLOR_BUFFER_BIT);

        m_GamePack.m_Camera.onDrawFrame();

        m_ClonedRenderList = (ArrayList<GameObject>)m_GamePack.m_RenderList.clone();

       // for(GameObject go: m_ClonedRenderList){
        int i = 0;
        GameObject go;
        for(i = 0; i < m_ClonedRenderList.size(); i++){
            go = m_ClonedRenderList.get(i);

            m_GamePack.getPhongShader().useProgram();
            m_GamePack.getPhongShader().setUniforms(m_GamePack.m_Camera.getMVPMatrix(go.getModelMatrix()), go.getModelMatrix(), m_GamePack.m_Camera.getPosition(), m_TextureManager, go.getMaterial());
            m_ModelManager.getModel(go.getModelType()).draw(m_GamePack.getPhongShader().getPositionAttributeLocation(),
                                                            m_GamePack.getPhongShader().getTextureCoordinatesAttributeLocation(),
                                                            m_GamePack.getPhongShader().getNormalAttributeLocation());
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

package uk.co.calvinwylie.chopperv2.game;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.Camera;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.shaderPrograms.PhongShader;
import uk.co.calvinwylie.chopperv2.shaderPrograms.Shader;
import uk.co.calvinwylie.chopperv2.shaderPrograms.TextureShader;
import uk.co.calvinwylie.chopperv2.ui.UICamera;
import uk.co.calvinwylie.chopperv2.ui.UIElement;


public class GamePacket {

    private String tag = "GamePacket";

    public ArrayList<ArrayList<GameObject>> m_RenderList = new ArrayList<>();
    public ArrayList<UIElement> m_UIRenderList = new ArrayList<>();

    private static PhongShader m_PhongShader;
    private static TextureShader m_TextureShader;

    private Context m_Context;
    public Camera m_Camera; //Todo make not public
    public UICamera m_UICamera;// and this

    public GamePacket(Context context){
        for(int i = 0; i < ModelType.values().length; i++){
            m_RenderList.add(new ArrayList<GameObject>());
            Log.i(tag, "ADD LIST:" + i);
        }
        m_Context = context;

    }

    public void assignCamera(Camera camera){
        m_Camera = camera;
    }

    public void addToRenderer(GameObject go){
        m_RenderList.get(go.getModelType().ordinal()).add(go);
    }

    public void addToRenderer(UIElement uie){
        m_UIRenderList.add(uie);
    }


    public void assignUICamera(UICamera uic) {
        m_UICamera = uic;
    }

    public Context getContext(){
        return m_Context;
    }

    public PhongShader getPhongShader(){
        return m_PhongShader;
    }

    public TextureShader getTextureShader(){
        return m_TextureShader;
    }

    public void initShaders(){
        m_TextureShader = new TextureShader(m_Context);
        m_PhongShader = new PhongShader(m_Context);
    }
}

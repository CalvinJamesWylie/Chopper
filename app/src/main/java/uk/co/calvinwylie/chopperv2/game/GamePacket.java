package uk.co.calvinwylie.chopperv2.game;

import android.content.Context;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.gameObjects.Camera;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.ui.UICamera;
import uk.co.calvinwylie.chopperv2.ui.UIElement;

/**
 * Created by Calvin on 23/04/2015.
 */
public class GamePacket {

    public ArrayList<GameObject> m_RenderList = new ArrayList<>();
    public ArrayList<UIElement> m_UIRenderList = new ArrayList<>();
    private Context m_Context;
    public Camera m_Camera; //Todo make not public
    public UICamera m_UICamera;// and this

    public GamePacket(Context context){
        m_Context = context;
        m_RenderList.clear();
        m_UIRenderList.clear();
    }

    public void assignCamera(Camera camera){
        m_Camera = camera;
    }

    public void addToRenderer(GameObject go){
        m_RenderList.add(go);
    }

    public void addToRenderer(UIElement uie){
        m_UIRenderList.add(uie);
    }

    public void assignUICamera(UICamera uic) {
        m_UICamera = uic;
    }
//    public void update(double deltaTime){
//        for(GameObject go; m_UpdateList){
//            go.update(deltaTime);
//        }
//    }
}

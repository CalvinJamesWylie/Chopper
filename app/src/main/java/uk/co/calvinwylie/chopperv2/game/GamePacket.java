package uk.co.calvinwylie.chopperv2.game;

import android.content.Context;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.gameObjects.Camera;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;

/**
 * Created by Calvin on 23/04/2015.
 */
public class GamePacket {

    public ArrayList<GameObject> m_RenderList = new ArrayList<>();
    private Context m_Context;
    public Camera m_Camera; //Todo make not public

    public GamePacket(Context context){
        m_Context = context;

    }

    public void assignCamera(Camera camera){
        m_Camera = camera;
    }

    public void addToRenderer(GameObject go){
        m_RenderList.add(go);
    }

//    public void update(double deltaTime){
//        for(GameObject go; m_UpdateList){
//            go.update(deltaTime);
//        }
//    }
}

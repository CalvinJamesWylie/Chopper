package uk.co.calvinwylie.chopperv2.game;

import android.content.Context;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.Terrain;
import uk.co.calvinwylie.chopperv2.gameObjects.Vehicle;
import uk.co.calvinwylie.chopperv2.pubSub.PubSubManager;
import uk.co.calvinwylie.chopperv2.gameObjects.Camera;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;

/**
 * Created by Calvin on 23/04/2015.
 */
public class GameLogic {

    private GamePacket m_GamePack;
    private ArrayList<GameObject> m_UpdateList = new ArrayList<>();
    private Camera m_Camera;
    private Vehicle m_Heli;
    private Terrain m_Terrain;
    private PubSubManager m_PubSubManager = new PubSubManager();

    public GameLogic(Context context, GamePacket gamePack, TouchHandler touchHandler) {
        m_GamePack = gamePack;


        m_Terrain = new Terrain();
        m_GamePack.addToRenderer(m_Terrain);

        m_Heli = new Vehicle(context, touchHandler);
        m_Heli.setPosition(new Vector3(0.0f, 10.0f, 0.0f));
        m_GamePack.addToRenderer(m_Heli);
        m_UpdateList.add(m_Heli);

        m_Camera = new Camera(0.0f, 50f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f);
        m_GamePack.assignCamera(m_Camera);
    }

    public void update(double deltaTime){
        m_Camera.update();//Make this happen.
        for (GameObject go: m_UpdateList){
            go.update(deltaTime);
        }
    }
}

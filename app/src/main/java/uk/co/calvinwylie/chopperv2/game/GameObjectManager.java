package uk.co.calvinwylie.chopperv2.game;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;


public class GameObjectManager {
    ArrayList<GameObject> m_GameObjectList = new ArrayList<>();

    public GameObjectManager(){
        m_GameObjectList.clear();
    }

    public void add(GameObject go){
        m_GameObjectList.add(go);
    }

    public void update(double deltaTime){
        for (GameObject go: m_GameObjectList){
            go.update(deltaTime);
        }
    }

    public ArrayList<GameObject> getGameObjectList(){
        return m_GameObjectList;
    }
}

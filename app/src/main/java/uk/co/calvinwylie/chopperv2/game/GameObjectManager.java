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
        GameObject go;
        int i;
        for (i = 0; i < m_GameObjectList.size(); i++){
            go = m_GameObjectList.get(i);
            go.update(deltaTime);
        }
    }

    public ArrayList<GameObject> getGameObjectList(){
        return m_GameObjectList;
    }
}

package uk.co.calvinwylie.chopperv2.game;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.gameObjects.Bullet;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;

/**
 * Created by Calvin on 30/06/2015.
 */
public class BulletManager {
    private ArrayList<Bullet> m_BulletList = new ArrayList<>();

    public BulletManager(){
        m_BulletList.clear();
    }

    public void add(Bullet bullet){
        m_BulletList.add(bullet);
    }

    public void update(double deltaTime){
        for (Bullet bullet: m_BulletList){
            bullet.update(deltaTime);
        }
    }

    public ArrayList<Bullet> getBulletList(){
        return m_BulletList;
    }
}

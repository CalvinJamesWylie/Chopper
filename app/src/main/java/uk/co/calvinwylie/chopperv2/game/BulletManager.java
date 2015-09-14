package uk.co.calvinwylie.chopperv2.game;

import android.util.Log;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Vector;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.Bullet;
import uk.co.calvinwylie.chopperv2.gameObjects.Enemy;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;


public class BulletManager {
    private static String tag = "BulletManager";

    private ArrayList<Bullet> m_BulletList = new ArrayList<>();
    private ArrayList<Integer> m_RecycledBulletIndices = new ArrayList<>();

    private GameLogic m_GameLogic;

    //Variables used in ColDet
    Vector3 newBulletPos = new Vector3();
    Vector3 newGameObjectPos = new Vector3();
    Vector3 tempVector3 = new Vector3();
    Vector2 tempVector2 = new Vector2();

    public BulletManager(GameLogic gameLogic){
        m_GameLogic = gameLogic;
        m_BulletList.clear();
    }

    public void add(Affiliation affiliation, Vector3 position, float yaw, Vector2 forwardVector, float muzzleVelocity) {
        //if we have no recyclable bullets, make a new one. if we do use an old one and give it new params.

        if(m_RecycledBulletIndices.isEmpty()){
            Bullet bullet = new Bullet(affiliation, position, yaw, forwardVector, muzzleVelocity);
            m_BulletList.add(bullet);
            m_GameLogic.getGamePack().addToRenderer(bullet);
        }else{
            int index = m_RecycledBulletIndices.get(0);
            m_BulletList.get(index).recycle(affiliation, position, yaw, forwardVector, muzzleVelocity);
            m_RecycledBulletIndices.remove(0);
        }
    }

    public void update(double deltaTime){
        Bullet bullet;
        GameObject GO;
        int i,j;
        ArrayList<GameObject> GOList = m_GameLogic.getGameObjectManager().getGameObjectList();

        nextBullet:
        for(i = 0; i < m_BulletList.size(); i++){
            bullet = m_BulletList.get(i);

            if(!bullet.isVisible())
                continue;
            if(checkOutOfBounds(bullet)) {
                bullet.setVisibility(false);
                m_RecycledBulletIndices.add(i);
                continue;
            }

            for (j = 0; j < GOList.size(); j++){
                GO = GOList.get(j);
                if(bullet.getAffiliation()!= GO.getAffiliation() && SphereCollisionDetection(bullet, GO, deltaTime)) {
                    bullet.setVisibility(false);
                    m_RecycledBulletIndices.add(i);
                    if(GO instanceof Enemy)
                        ((Enemy)GO).Collision();
                    continue nextBullet;
                }
            }
            bullet.update(deltaTime);
        }
    }

    private boolean checkOutOfBounds(Bullet bullet) {
        int cutOff = 50;
        if(bullet.getPosition().X < -cutOff || bullet.getPosition().X > cutOff ||
                bullet.getPosition().Y < -cutOff || bullet.getPosition().Y > cutOff) {
            return true;
        }

        return false;

    }

    public ArrayList<Bullet> getBulletList(){
        return m_BulletList;
    }

    private boolean SphereCollisionDetection(Bullet bullet, GameObject GO, double deltaTime){

        newBulletPos.set(bullet.getPosition());

        newGameObjectPos.set(GO.getPosition());

        float distBetweenObjsSqr = Vector3.vector2Between(tempVector2, newBulletPos, newGameObjectPos, "XZ").lengthSquared();
        float collisionRadiiSqr =(float) Math.pow((bullet.getCollisionRadius() * bullet.getScale() + (GO.getCollisionRadius()*GO.getScale())), 2);

        if(distBetweenObjsSqr <= collisionRadiiSqr){
            return true;
        }else{
            return false;
        }
    }


}

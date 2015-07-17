package uk.co.calvinwylie.chopperv2.gameObjects;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.game.BulletManager;
import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.game.GamePacket;

/**
 * Created by Calvin on 04/06/2015.
 */
public class Gun {

    private Vector2 m_ForwardVector = new Vector2(0,-1);
    private float m_Yaw;
    private boolean m_WeaponReady = false;
    private GameObject m_Parent;
    private BulletManager m_BulletManager;
    private float m_MuzzleVelocity = 1.0f;
    private float m_TimeSinceLastFire = 0.0f;
    private float m_ReloadTime = 0.25f;

    public Gun(GameObject parent, BulletManager bulletManager){
        m_Parent = parent;
        m_BulletManager = bulletManager;
    }

    public void update(double deltaTime){
        m_TimeSinceLastFire += deltaTime;
        if(!m_WeaponReady && m_TimeSinceLastFire >= m_ReloadTime){
            m_WeaponReady = true;
        }
    }

    public void fire(){
        m_TimeSinceLastFire = 0.0f;
        Bullet bullet = new Bullet(m_Parent.getPosition(), m_Yaw, m_ForwardVector, m_MuzzleVelocity);
        m_BulletManager.add(bullet);
        m_WeaponReady = false;
    }

    public void setDirection(Vector2 direction, float yaw) {
        m_ForwardVector = direction;
        m_ForwardVector.normalise();

        m_Yaw = yaw;
    }

    public void requestFire(){
        if(m_WeaponReady){
            fire();
        }
    }

    public Vector2 getForwardVector() {
        return m_ForwardVector;
    }
}

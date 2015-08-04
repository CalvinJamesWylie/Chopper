package uk.co.calvinwylie.chopperv2.gameObjects;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.game.BulletManager;
import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.game.GamePacket;


public class Gun {

    private GameObject m_Parent;

    private Vector2 m_ForwardVector = new Vector2(0,-1);
    private float m_Yaw;
    private boolean m_WeaponReady = false;
    private BulletManager m_BulletManager;

    private float m_MuzzleVelocity = 20.0f;
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
        m_BulletManager.add(m_Parent.getAffiliation(), m_Parent.getPosition(), m_Yaw, m_ForwardVector, m_MuzzleVelocity);
        m_WeaponReady = false;
    }

    //direction is for bullet travel, yaw is for rotation of the bullet.
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

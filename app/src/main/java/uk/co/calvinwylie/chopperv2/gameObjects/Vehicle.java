package uk.co.calvinwylie.chopperv2.gameObjects;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.game.BulletManager;
import uk.co.calvinwylie.chopperv2.physics.Engine;


public abstract class Vehicle extends GameObject {

    private String tag = this.getClass().getSimpleName();
    private Vector3 m_ForwardVector;
    protected Engine m_Engine;
    protected Gun m_Gun;

    public Vehicle(BulletManager bulletManager){
        m_Gun = new Gun(this, bulletManager);
    }

    public Gun getGun() {
        return m_Gun;
    }
}
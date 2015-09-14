package uk.co.calvinwylie.chopperv2.gameObjects;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.game.Affiliation;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class Bullet extends GameObject {

    private Vector3 m_OrginalVelocity = new Vector3();

    public Bullet(Affiliation affiliation, Vector3 position, float yaw, Vector2 forwardVector, float muzzleVelocity){
        init(affiliation, position, yaw, forwardVector, muzzleVelocity);
        m_PhysAttribs.scale.set(0.5f);
        m_Material = new Material(TextureType.orange);
        m_ModelType = ModelType.cube;
    }

    private void init(Affiliation affiliation, Vector3 position, float yaw, Vector2 forwardVector, float muzzleVelocity){
        m_Affiliation = affiliation;
        m_PhysAttribs.position.set(position);
        forwardVector.scaleBy(muzzleVelocity);
        m_OrginalVelocity.set(forwardVector);
        m_PhysAttribs.velocity.set(forwardVector);
        m_PhysAttribs.rotation.setAngle((float) Math.toDegrees(yaw));
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        updateModelMatrix();
    }


    private void move(double deltaTime){
        m_PhysAttribs.velocity.scaleBy((float)deltaTime);
        m_PhysAttribs.position.add(m_PhysAttribs.velocity);
        m_PhysAttribs.velocity.set(m_OrginalVelocity);
    }

    public void setVisibility(boolean visible){
        m_Visible = visible;
    }


    public void recycle(Affiliation affiliation, Vector3 position, float yaw, Vector2 forwardVector, float muzzleVelocity) {
        init(affiliation, position, yaw, forwardVector, muzzleVelocity);
        m_Visible = true;
    }
}

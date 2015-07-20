package uk.co.calvinwylie.chopperv2.gameObjects;

import android.opengl.GLES20;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class Bullet extends GameObject {


    public Bullet(Vector3 position, float yaw, Vector2 forwardVector, float speed){
        m_Position.set(position);
        forwardVector.scaleBy(speed);
        m_Velocity.set(forwardVector);
        m_Rotation.setAngle((float) Math.toDegrees(yaw));
        m_Scale.set(0.5f);
        m_Material = new Material(TextureType.orange);
        m_ModelType = ModelType.cube;
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        updateModelMatrix();
    }


    private void move(double deltaTime){
        //m_Velocity.scaleBy((float)deltaTime);
        m_Position.add(m_Velocity);
    }
}

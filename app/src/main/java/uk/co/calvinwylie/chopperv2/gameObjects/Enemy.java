package uk.co.calvinwylie.chopperv2.gameObjects;


import java.util.Random;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;

public class Enemy extends GameObject{

    public Enemy(){
        m_ModelType = ModelType.robot;
        m_Material.setTextureType(TextureType.robot);
       // m_Scale.set(3.0f, 3.0f, 3.0f);
        //m_Position.set(10.0f, 0.0f, 10.0f);
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {
        m_Position.add(m_ForwardVector, deltaTime);
        updateModelMatrix();
    }

    public void Collision(){
        Random rand = new Random();
        m_Position.set(rand.nextFloat()*50 - 25, 0.0f, rand.nextFloat()*50 - 25);
    }
}

package uk.co.calvinwylie.chopperv2.gameObjects;


import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;

public class Enemy extends GameObject{

    public Enemy(){
        m_ModelType = ModelType.sphere;
        m_Material.setTextureType(TextureType.orange);
        m_Scale.set(3.0f, 3.0f, 3.0f);
        m_Position.set(10.0f, 0.0f, 10.0f);
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {
        updateModelMatrix();
    }
}

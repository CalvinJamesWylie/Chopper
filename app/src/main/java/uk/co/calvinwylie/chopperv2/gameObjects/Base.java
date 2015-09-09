package uk.co.calvinwylie.chopperv2.gameObjects;

import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;

/**
 * Created by Calvin on 09/09/2015.
 */
public class Base extends GameObject {
    public Base(){
        m_ModelType = ModelType.base;
        m_Material.setTextureType(TextureType.base);
    }

    @Override
    public void update(double deltaTime) {

    }
}

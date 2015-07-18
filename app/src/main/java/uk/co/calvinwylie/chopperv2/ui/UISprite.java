package uk.co.calvinwylie.chopperv2.ui;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class UISprite extends UIElement {

    public UISprite(Vector3 position, Rotation rotation, float width, float height, TextureType textureType){
        m_Position = position;
        m_Rotation = rotation;
        m_Scale.set(width, 0, height);
        m_Material.setTextureType(textureType);
        updateModelMatrix();
    }

    public void update(){
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {
        updateModelMatrix();
    }


}

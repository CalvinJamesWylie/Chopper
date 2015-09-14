package uk.co.calvinwylie.chopperv2.ui;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class UIButton extends UIElement {
    public UIButton(Vector3 position, Rotation rotation, float width, float height, TextureType textureType){
        m_PhysAttribs.position = position;
        m_PhysAttribs.rotation = rotation;
        m_PhysAttribs.scale.set(width, 0, height);
        m_Material.setTextureType(textureType);
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {

    }

}

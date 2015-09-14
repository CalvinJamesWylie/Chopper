package uk.co.calvinwylie.chopperv2.gameObjects;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class Terrain extends GameObject {

    public Terrain(){
        m_Material = new Material(TextureType.grass);
        m_Material.setSpecularIntensity(0.0f);
        m_Material.setSpecularPower(0.0f);
        m_ModelType = ModelType.lamina;

        m_PhysAttribs.scale = new Vector3(100, 1, 100);
        updateModelMatrix();
    }

    @Override
    public void update(double deltaTime) {

    }

}

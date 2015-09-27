package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.level.MapCreator;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureManager;
import uk.co.calvinwylie.chopperv2.models.TextureType;


public class Terrain extends GameObject {

    public Terrain(){
        m_ModelType = ModelType.lamina;
        m_Material.setTextureType(TextureType.terrain);
        m_PhysAttribs.scale.set(100, 1, 100);
        updateModelMatrix();
        m_Material.setSpecularPower(0.0f);
        m_Material.setSpecularIntensity(0.0f);
    }

    @Override
    public void update(double deltaTime) {

    }
}

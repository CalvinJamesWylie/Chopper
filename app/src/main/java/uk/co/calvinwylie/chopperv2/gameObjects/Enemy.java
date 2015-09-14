package uk.co.calvinwylie.chopperv2.gameObjects;


import java.util.Random;

import uk.co.calvinwylie.chopperv2.ai.AIController;
import uk.co.calvinwylie.chopperv2.game.Affiliation;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;
import uk.co.calvinwylie.chopperv2.physics.Engine;

public class Enemy extends GameObject{

    public Enemy(){
        m_ModelType = ModelType.robot;
        m_Material.setTextureType(TextureType.robot);
        updateModelMatrix();
        m_Affiliation = Affiliation.Red;
        m_PhysAttribs.turnSpeed = 1.0f;
        m_PhysAttribs.speed = 2.0f;
        m_PhysAttribs.mass = 1.5f;
        m_PhysAttribs.airResistance = 1.0f;
        m_PhysAttribs.engine = new Engine(this.m_PhysAttribs);
        m_Controller = new AIController(this.m_PhysAttribs);
    }

    public void setTarget(GameObject target){
        ((AIController)m_Controller).setTarget(target);
    }

    @Override
    public void update(double deltaTime) {
        m_Controller.update(deltaTime);
        updateModelMatrix();
    }

    public void Collision(){
    }
}

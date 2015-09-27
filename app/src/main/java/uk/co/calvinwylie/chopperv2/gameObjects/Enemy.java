package uk.co.calvinwylie.chopperv2.gameObjects;


import java.util.Random;

import uk.co.calvinwylie.chopperv2.ai.AIController;
import uk.co.calvinwylie.chopperv2.game.Affiliation;
import uk.co.calvinwylie.chopperv2.models.ModelType;
import uk.co.calvinwylie.chopperv2.models.TextureType;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;

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
        int i = MathsHelper.rand.nextInt(4);
        switch (i % 4) {
            case 0:
                setPosition(MathsHelper.rand.nextFloat() * 100 - 50, 0, 50);
                break;
            case 1:
                setPosition(50, 0, MathsHelper.rand.nextFloat() * 100 - 50);
                break;
            case 2:
                setPosition(MathsHelper.rand.nextFloat() * 100 - 50, 0, -50);
                break;
            case 3:
                setPosition(-50, 0, MathsHelper.rand.nextFloat() * 100 - 50);
                break;
        }

    }
}

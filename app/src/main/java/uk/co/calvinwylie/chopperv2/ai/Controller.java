package uk.co.calvinwylie.chopperv2.ai;

import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;

/**
 * Created by Calvin on 14/09/2015.
 */
public abstract class Controller {
    protected GameObject.PhysicalAttribs m_ActorAttribs;

    public Controller (GameObject.PhysicalAttribs actorAttribs){
        m_ActorAttribs = actorAttribs;
    }

    public abstract void update(double deltaTime);

}

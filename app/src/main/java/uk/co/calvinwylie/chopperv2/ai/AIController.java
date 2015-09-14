package uk.co.calvinwylie.chopperv2.ai;

import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.physics.Dynamics;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;

public class AIController extends Controller {


    private GameObject m_Target;
    private Vector3 m_TargetPosition = new Vector3();
    private Vector3 m_DeltaPosition = new Vector3();
    float m_AngleToTarget;

    private Vector3 m_TempVect = new Vector3();

    public AIController(GameObject.PhysicalAttribs actorAttribs){
        super(actorAttribs);

        m_TargetPosition = new Vector3(0,0,0);

    }

    public void setTarget(GameObject target){
        m_Target = target;
    }

    @Override
    public void update(double deltaTime) {
        navigate(deltaTime);
    }

    public void navigate(double deltaTime){

        move(deltaTime);
        turn(deltaTime);
        m_ActorAttribs.updateDirVectors();
    }

    private void turn(double deltaTime){

        Vector3.vector3Between(m_DeltaPosition, m_ActorAttribs.position, (m_Target != null) ? m_Target.getPosition() : m_TargetPosition);

        float x = m_DeltaPosition.X;
        float z = m_DeltaPosition.Z;

        float hyp = (float) Math.sqrt((x*x) + (z*z)); //TODO remove this sqrt
        if(hyp != 0){
            m_ActorAttribs.targetYaw = (float) Math.asin(z / hyp);
            m_ActorAttribs.targetYaw += Math.PI / 2;
            if (x > 0) {
                m_ActorAttribs.targetYaw = (float) (2 * Math.PI) - m_ActorAttribs.targetYaw;
            }
        }else{
            m_ActorAttribs.targetYaw = 0;
        }

        m_AngleToTarget = (float) ((m_ActorAttribs.targetYaw - m_ActorAttribs.yaw + (2 * Math.PI)) % (2 * Math.PI));

        if(m_AngleToTarget > Math.PI){
            m_AngleToTarget = (float)(2*Math.PI) - m_AngleToTarget;
            m_ActorAttribs.yaw -= m_ActorAttribs.turnSpeed*deltaTime;
        }else{
            m_ActorAttribs.yaw += m_ActorAttribs.turnSpeed*deltaTime;
        }

        m_ActorAttribs.yaw = MathsHelper.RoundClamp(m_ActorAttribs.yaw, 0, (float) Math.PI * 2);

    }


    private void move(double deltaTime) {
        m_ActorAttribs.engine.exertForce(m_ActorAttribs.forwardVector, m_ActorAttribs.speed);
        m_ActorAttribs.velocity.add(m_ActorAttribs.engine.getAcceleration());
        m_TempVect.set(m_ActorAttribs.velocity);
        m_TempVect.scaleBy((float) deltaTime);
        m_ActorAttribs.position.add(m_TempVect);
        Dynamics.calcAirResistance(m_ActorAttribs.velocity, m_ActorAttribs.airResistance);
    }

}

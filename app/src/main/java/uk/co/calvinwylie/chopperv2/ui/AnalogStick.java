package uk.co.calvinwylie.chopperv2.ui;

import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Rotation;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;

/**
 * Created by Calvin on 29/05/2015.
 */
public class AnalogStick {
    private String tag = this.getClass().getSimpleName();

    private Vector2 m_Center          = new Vector2();
    private Vector2 m_CurrentPosition = new Vector2();
    private Vector2 m_ButtonPressPos  = new Vector2();
    private Vector2 m_DeltaPosition   = new Vector2();

    private float m_Angle = 0.0f;
    private float m_MaxRange = 75.0f;

    private boolean m_Active = false;
    private boolean m_Moved  = false;

    private int m_AssociatedPointerId = -1;

    private UISprite m_CenterMarker;

    public AnalogStick(int textureResourceId){
        m_CenterMarker = new UISprite(new Vector3(), new Rotation(), 1.0f , 1.0f, textureResourceId); //TODO add params
    }

    public void activate(Vector2 center, int id) {
        m_Center.set(center);
        m_CurrentPosition.set(center);
        m_DeltaPosition = new Vector2();
        m_Active = true;
        m_AssociatedPointerId = id;

        Log.i(tag, ""+center.toString());
        m_CenterMarker.setPosition(new Vector3(center.X, 0.0f, -center.Y));
        m_CenterMarker.setVisible(true);
    }

    public void deactivate(Vector2 lastTouch) {
        if (!m_Moved){
            m_ButtonPressPos = lastTouch;
        }
        m_Active = false;
        m_CenterMarker.setVisible(false);
    }

    public Vector2 getDelta(){
        return m_DeltaPosition;
    }

    public float getAngle(){
        return m_Angle;
    }

    public int getAssociatedPointerId(){
        return m_AssociatedPointerId;
    }

    public void updateCurrentPosition(Vector2 touchVec) {

        // -- Set the new normalised stick position between 0 and max range -- //
        m_CurrentPosition.set(touchVec);
        m_DeltaPosition = Vector2.vectorBetween(m_CurrentPosition, m_Center);

        float length = m_DeltaPosition.length();
        if(length > m_MaxRange){
            length = m_MaxRange;
        }

        m_DeltaPosition.normalise();

        float ratio = length/m_MaxRange;
        m_DeltaPosition.scaleBy(ratio); // Delta Position returned between 0 and 1;

        // -- Update the angle the stick is currently at. -- //
        float x = m_DeltaPosition.X;
        float y = m_DeltaPosition.Y;

        float hyp = (float) Math.sqrt((x*x) + (y*y)); //TODO remove this sqrt
        if(hyp != 0){
            m_Angle = (float) Math.asin(y / hyp);
            m_Angle += Math.PI / 2;
            if (x > 0) {
                m_Angle = (float) (2 * Math.PI) - m_Angle;
            }
        }else{
            m_Angle = 0;
        }

        m_CenterMarker.setRotation((float)Math.toDegrees(-m_Angle), 0.0f, 1.0f, 0.0f);

//        if(!m_Moved && m_DeltaPosition.length() > 10){
//            m_Moved = true;
//        }
    }

    public boolean isActive(){
        return m_Active;
    }

    public UIElement getSprite() {
        return m_CenterMarker;
    }
}

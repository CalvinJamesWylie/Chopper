package uk.co.calvinwylie.chopperv2.ui;

import android.util.Log;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;

/**
 * Created by Calvin on 13/05/2015.
 */
public class TouchHandler {

    private String tag = this.getClass().getSimpleName();

    public AnalogStick leftAnalogStick  = new AnalogStick();
    public AnalogStick rightAnalogStick = new AnalogStick();

    private int m_ScreenWidth, m_ScreenHeight;

    public TouchHandler(int screenWidth, int screenHeight){
        m_ScreenWidth = screenWidth;
        m_ScreenHeight = screenHeight;
    }

    public void handleActionDown(Vector2 touchVec, int pointerId) {
        Log.i(tag, "Down");
        if (touchVec.X < m_ScreenWidth/2){
            leftAnalogStick.activate(touchVec, pointerId);
        }else {
            rightAnalogStick.activate(touchVec, pointerId);
        }
    }

    public void handleActionUp(Vector2 touchVec, int pointerId){

        if(rightAnalogStick.getAssociatedPointerId() == pointerId){
            rightAnalogStick.deactivate(touchVec);
        }
        if(leftAnalogStick.getAssociatedPointerId() == pointerId){
            leftAnalogStick.deactivate(touchVec);
        }
    }


    public void handleActionMove(Vector2 touchVec, int pointerId) {
        if(rightAnalogStick.getAssociatedPointerId() == pointerId){
            rightAnalogStick.updateCurrentPosition(touchVec);
        }
        if(leftAnalogStick.getAssociatedPointerId() == pointerId){
            leftAnalogStick.updateCurrentPosition(touchVec);
        }
    }

}
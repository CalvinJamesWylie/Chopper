package uk.co.calvinwylie.chopperv2.util;

import android.util.Log;


public class FrameRateLogger {
    private String m_Name;
    private long m_OldTime = 0;
    private long m_NewTime = 0;
    private long m_FrameRate = 0;

    public FrameRateLogger(String name){
        m_Name = name;
    }

    public void tick(){
        m_NewTime = System.currentTimeMillis();

        if(m_OldTime == 0){
            m_OldTime = m_NewTime - 1;
        }

        m_FrameRate = 1000/(m_NewTime - m_OldTime);
        Log.i(m_Name, "FPS: "+ m_FrameRate);

        m_OldTime = m_NewTime;
    }



}

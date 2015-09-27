package uk.co.calvinwylie.chopperv2.util;

import android.util.Log;


public class Timer {
    private String m_Name;
    private long m_OldTime = 0;
    private long m_NewTime = 0;
    private long m_StartTime = 0;
    private long m_EndTime = 0;
    private long m_DeltaTime = 0;
    private long m_FrameRate = 0;
    private boolean m_Active = true;
    private boolean m_HasEnded = false;


    public Timer(String name){
        m_Name = name;
        m_OldTime = System.currentTimeMillis();
    }

    public void start(){
        if(!m_Active) return;

        m_StartTime = System.currentTimeMillis();

    }

    public void end(){
        if(!m_Active) return;

        m_EndTime = System.currentTimeMillis();
        Log.i(m_Name, "Time: " + (m_EndTime - m_StartTime));


    }

    public void tick(){
        if (!m_Active)
            return;

        m_NewTime = System.currentTimeMillis();

        m_DeltaTime = m_NewTime - m_OldTime;
        m_FrameRate = 1000/m_DeltaTime;

        Log.i(m_Name, "FPS: "+ m_FrameRate);

        m_OldTime = m_NewTime;
    }

    public void setActive(boolean active){
        m_Active = active;
    }

}

package uk.co.calvinwylie.chopperv2;

import android.opengl.GLSurfaceView;

import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.util.Timer;


public class GameThread extends Thread {

    private boolean m_Paused = false;
    private boolean m_Running = true;
    private GameLogic m_Logic;
    private GLSurfaceView m_View;
    private long m_NewTime;
    private long m_OldTime;
    private long m_DeltaTime;
    private long m_FrameRate = 1000L / 30;
    private static int MILLIS_IN_SECOND = 1000;
    Timer FRL = new Timer("GameThread");



    public GameThread(GameLogic logic, GLSurfaceView view){
        m_Logic = logic;
        m_View = view;

        m_NewTime = 0L;
        m_OldTime = 0L;
        m_DeltaTime = 0L;

        FRL.setActive(false);
    }

    @Override
    public void run(){

        m_NewTime = System.currentTimeMillis();

        while(!m_Paused){

            m_OldTime = m_NewTime;
            m_NewTime = System.currentTimeMillis();
            m_DeltaTime += (m_NewTime - m_OldTime);

            if (m_DeltaTime >= m_FrameRate) {

                FRL.tick();

                m_Logic.update((double) m_DeltaTime / MILLIS_IN_SECOND); //convert to seconds.

                m_View.requestRender();
                m_DeltaTime = 0;
            }

        }
    }

    public void setPaused(boolean paused) {
        m_Paused = paused;
    }
    public boolean isPaused(){
        return m_Paused;
    }
}

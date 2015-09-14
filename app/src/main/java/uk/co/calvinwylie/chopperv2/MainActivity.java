package uk.co.calvinwylie.chopperv2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.support.v4.view.MotionEventCompat;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.game.GamePacket;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;
import uk.co.calvinwylie.chopperv2.util.LoggerConfig;


public class MainActivity extends Activity {

    private String tag = this.getClass().getSimpleName();

    private GLSurfaceView m_GlSurfaceView;
    private boolean m_RendererSet = false;
    private TouchHandler m_TouchHandler;
    private Vector2 m_TempTouchVector = new Vector2();
    private GamePacket m_GamePack;
    private GameLogic m_Logic;
    private GameThread m_GameThread;
    private MainRenderer m_MainRenderer;
    private Point m_DisplaySize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        m_GamePack     = new GamePacket(this);
        m_MainRenderer = new MainRenderer(this, m_GamePack);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        WindowManager wm = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        m_DisplaySize = new Point();
        display.getRealSize(m_DisplaySize);

        if(supportEs2){
            m_GlSurfaceView = new GLSurfaceView(this);
            m_GlSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 24, 8);
            m_GlSurfaceView.setEGLContextClientVersion(2);
            m_GlSurfaceView.setRenderer(m_MainRenderer);
            m_GlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

            m_RendererSet  = true;
            m_TouchHandler = new TouchHandler(m_DisplaySize.x, m_DisplaySize.y, R.drawable.analog_stick);
            m_Logic        = new GameLogic(this, m_GamePack, m_TouchHandler);
            m_GameThread   = new GameThread(m_Logic, m_GlSurfaceView);
            m_GameThread.start();

        }else{
            Toast.makeText(this, "This device does not support OpenGL 2.0", Toast.LENGTH_LONG).show();
            return;
        }

        m_GlSurfaceView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            if(event != null){
                int action = MotionEventCompat.getActionMasked(event);
                int index  = MotionEventCompat.getActionIndex(event);
                int id     = MotionEventCompat.getPointerId(event, index);

                //convert touch coords into normalised device
                //coords, keeping in mind that Android Y coords are inverted.
                //float normalisedX =   (MotionEventCompat.getX(event, index) / (float) v.getWidth())  * 2 - 1;
                //float normalisedY = -((MotionEventCompat.getY(event, index) / (float) v.getHeight()) * 2 - 1);

                float touchX =  MotionEventCompat.getX(event, index);
                float touchY =  MotionEventCompat.getY(event, index);

                touchX -= m_DisplaySize.x/2;
                touchY -= m_DisplaySize.y/2;

                m_TempTouchVector.set(touchX, touchY);

                //Log.i(tag, event.toString());

                switch(action){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:

                        //Log.i(tag, "pointer down: " + id);
                        m_TouchHandler.handleActionDown(m_TempTouchVector, id);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        //Log.i(tag, "pointer up: " + id);
                        m_TouchHandler.handleActionUp(m_TempTouchVector, id);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int pointerCount = event.getPointerCount();
                        for(int i = 0; i < pointerCount; i++) {
                            index = i;
                            id = MotionEventCompat.getPointerId(event, index);

                            touchX = MotionEventCompat.getX(event, index);
                            touchY = MotionEventCompat.getY(event, index);

                            touchX -= m_DisplaySize.x/2;
                            touchY -= m_DisplaySize.y/2;

                           // normalisedX =   (x / (float) v.getWidth())  * 2 - 1;
                            //normalisedY = -((y / (float) v.getHeight()) * 2 - 1);

                            m_TempTouchVector.set(touchX, touchY);

                            m_TouchHandler.handleActionMove(m_TempTouchVector, id);
                        }

                        break;

                }
                return true;
            } else{
                return false;
            }
            }
        });

        setContentView(m_GlSurfaceView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        LoggerConfig.DEBUG = true;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(m_RendererSet){
            m_GlSurfaceView.onPause();
        }
        if(!m_GameThread.isPaused()){
            m_GameThread.setPaused(true);
            //TODO stop thread here
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();

        if(m_RendererSet){
            m_GlSurfaceView.onResume();
        }
        if(m_GameThread.isPaused()){
            m_GameThread.setPaused(false);
            //m_GameThread.start();
        }
    }
}

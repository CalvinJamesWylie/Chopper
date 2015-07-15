package uk.co.calvinwylie.chopperv2.gameObjects;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.game.BulletManager;
import uk.co.calvinwylie.chopperv2.game.GameLogic;
import uk.co.calvinwylie.chopperv2.physics.Dynamics;
import uk.co.calvinwylie.chopperv2.physics.Engine;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;
import uk.co.calvinwylie.chopperv2.util.TextureType;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Calvin on 16/04/2015.
 */
public abstract class Vehicle extends GameObject {

    private String tag = this.getClass().getSimpleName();
    private Vector3 m_ForwardVector;
    protected Engine m_Engine;
    protected Gun m_Gun;

    public Vehicle(BulletManager bulletManager){
        m_Gun = new Gun(this, bulletManager);
    }

}
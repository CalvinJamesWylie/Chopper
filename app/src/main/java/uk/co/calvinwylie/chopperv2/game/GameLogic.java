package uk.co.calvinwylie.chopperv2.game;

import android.content.Context;

import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.Enemy;
import uk.co.calvinwylie.chopperv2.gameObjects.Helicopter;
import uk.co.calvinwylie.chopperv2.gameObjects.Terrain;
import uk.co.calvinwylie.chopperv2.gameObjects.Vehicle;
import uk.co.calvinwylie.chopperv2.lights.Attenuation;
import uk.co.calvinwylie.chopperv2.lights.BaseLight;
import uk.co.calvinwylie.chopperv2.lights.PointLight;
import uk.co.calvinwylie.chopperv2.lights.SpotLight;
import uk.co.calvinwylie.chopperv2.pubSub.PubSubManager;
import uk.co.calvinwylie.chopperv2.gameObjects.Camera;
import uk.co.calvinwylie.chopperv2.gameObjects.GameObject;
import uk.co.calvinwylie.chopperv2.shaderPrograms.PhongShader;
import uk.co.calvinwylie.chopperv2.ui.TouchHandler;
import uk.co.calvinwylie.chopperv2.ui.UICamera;
import uk.co.calvinwylie.chopperv2.util.MathsHelper;


public class GameLogic {

    private GamePacket m_GamePack;
    private Context m_Context;
    private Camera m_Camera;
    private UICamera m_UICamera;
    private Helicopter m_Heli;
    private Enemy m_Enemy;
    private Terrain m_Terrain;

    //-- Managers -- //
    private PubSubManager m_PubSubManager = new PubSubManager();
    private GameObjectManager m_GameObjectManager = new GameObjectManager();
    private BulletManager m_BulletManager = new BulletManager(this);

    double m_TotalTime = 0.0;

    public GameLogic(Context context, GamePacket gamePack, TouchHandler touchHandler) {
        m_Context = context;
        m_GamePack = gamePack;

        m_GamePack.addToRenderer(touchHandler.leftAnalogStick.getSprite());
        m_GamePack.addToRenderer(touchHandler.rightAnalogStick.getSprite());

        m_Terrain = new Terrain();
        m_GamePack.addToRenderer(m_Terrain);

        m_Heli = new Helicopter(m_Context, m_BulletManager, touchHandler);
        m_Heli.setPosition(new Vector3(0.0f, 2, 0.0f));
        m_GamePack.addToRenderer(m_Heli);
        m_GameObjectManager.add(m_Heli);

        m_Enemy = new Enemy();
        m_GamePack.addToRenderer(m_Enemy);
        m_GameObjectManager.add(m_Enemy);

        m_Camera = new Camera(0.0f, 50f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f);
        m_Camera.setFollow(m_Heli);
        m_GamePack.assignCamera(m_Camera);

        m_UICamera = new UICamera();
        m_GamePack.assignUICamera(m_UICamera);

        PointLight pLight1 = new PointLight(
                new BaseLight(new Vector3(1,1,1), 10),
                new Attenuation(0,0,0.2f),
                new Vector3(-0, 0, 0),
                30);
        PointLight pLight2 = new PointLight(
                new BaseLight(new Vector3(1,0,0), 40),
                new Attenuation(0,0,0.2f),
                new Vector3(0,0,0),
                30);
        PointLight pLight3 = new PointLight(
                new BaseLight(new Vector3(0,1,0), 10),
                new Attenuation(0,0,0.2f),
                new Vector3(0,0, 0),
                30);
        PointLight pLight4 = new PointLight(
                new BaseLight(new Vector3(0,0,1), 10),
                new Attenuation(0,0,0.2f),
                new Vector3(0,0, 0),
                30);

        SpotLight sLight1 = new SpotLight(pLight1, new Vector3(1,0,1), 0.9f);
        SpotLight sLight2 = new SpotLight(pLight2, new Vector3(1,0,1), 0.9f);
        SpotLight sLight3 = new SpotLight(pLight3, new Vector3(1,0,1), 0.9f);
        SpotLight sLight4 = new SpotLight(pLight4, new Vector3(1,0,1), 0.9f);

        PhongShader.setSpotLights(new SpotLight[]{sLight1, sLight2, sLight3, sLight4});

    }

    public void update(double deltaTime){

        m_Camera.update(deltaTime);//Make this happen.
        m_GameObjectManager.update(deltaTime);
        m_BulletManager.update(deltaTime);

        m_TotalTime += deltaTime;

        MathsHelper.RoundClamp((float) deltaTime, 0, (float)Math.PI);

        float tempX = (float)Math.cos(m_TotalTime);
        float tempY = (float)Math.sin(m_TotalTime);

        Vector3 forward = m_Heli.getForwardVector();
        Vector3 right = m_Heli.getRightVector();


        PhongShader.getSpotLights()[0].getPointLight().setPosition(m_Heli.getPosition());
        PhongShader.getSpotLights()[1].getPointLight().setPosition(m_Heli.getPosition());
        PhongShader.getSpotLights()[2].getPointLight().setPosition(m_Heli.getPosition());
        PhongShader.getSpotLights()[3].getPointLight().setPosition(m_Heli.getPosition());

        PhongShader.getSpotLights()[0].getDirection().set(forward.X, forward.Y, forward.Z);
        PhongShader.getSpotLights()[1].getDirection().set(right.X, right.Y, right.Z);
        PhongShader.getSpotLights()[2].getDirection().set(-forward.X, -forward.Y, -forward.Z);
        PhongShader.getSpotLights()[3].getDirection().set(-right.X, -right.Y, -right.Z);

    }

    public Context getContext() {
        return m_Context;
    }

    public GamePacket getGamePack(){
        return m_GamePack;
    }

    public BulletManager getBulletManager() {
        return m_BulletManager;
    }

    public GameObjectManager getGameObjectManager() {
        return m_GameObjectManager;
    }
}



package uk.co.calvinwylie.chopperv2.models;

import android.content.Context;

import uk.co.calvinwylie.chopperv2.R;

/**
 * Created by Calvin on 15/07/2015.
 */
public class ModelManager {

    Mesh[] m_Models = new Mesh[ModelType.values().length];

    public void loadModels(Context context){
        ModelType[] models = ModelType.values();

        for(ModelType modelType: models){
            switch(modelType){
                case helicopter:
                    m_Models[modelType.ordinal()] = ModelLoader.loadModel(context, "heli_final.obj");
                    break;
            }
        }
    }

    public Mesh getModel(ModelType modelType){
        return m_Models[modelType.ordinal()];
    }
}

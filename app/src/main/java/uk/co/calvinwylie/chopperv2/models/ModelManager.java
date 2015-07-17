package uk.co.calvinwylie.chopperv2.models;

import android.content.Context;

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
                    m_Models[modelType.ordinal()] = ModelLoader.loadModel(context, modelType.name() + ".obj");
                    break;
                case lamina:
                    m_Models[modelType.ordinal()] = ModelLoader.loadModel(context, modelType.name() + ".obj");
                    break;
                case cube:
                    m_Models[modelType.ordinal()] = ModelLoader.loadModel(context, modelType.name() + ".obj");
                    break;
            }
        }
    }

    public Mesh getModel(ModelType modelType){
        return m_Models[modelType.ordinal()];
    }
}

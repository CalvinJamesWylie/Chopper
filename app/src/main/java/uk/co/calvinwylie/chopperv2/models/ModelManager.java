package uk.co.calvinwylie.chopperv2.models;

import android.content.Context;


public class ModelManager {

    Mesh[] m_Models = new Mesh[ModelType.values().length];

    public void loadModels(Context context){
        ModelType[] models = ModelType.values();

        for(ModelType modelType: models){
            m_Models[modelType.ordinal()] = ModelLoader.loadModel(context, modelType.name() + ".obj");
        }
    }

    public Mesh getModel(ModelType modelType){
        return m_Models[modelType.ordinal()];
    }
}

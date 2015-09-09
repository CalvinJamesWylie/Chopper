package uk.co.calvinwylie.chopperv2.ui;

import android.opengl.GLES10;
import android.opengl.Matrix;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setLookAtM;


public class UICamera {
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] m_ViewProjectionMatrix = new float[16];
    private final float[] m_OrthoMatrix = new float[16];
    private final float[] m_ViewMatrix = new float[16];

    private Vector3 m_Position;
    private Vector3 m_LookAt;
    private Vector3 m_UpVector;

    public UICamera(){
        m_Position = new Vector3(0, 1, 0);
        m_LookAt   = new Vector3(0, 0, 0);
        m_UpVector = new Vector3(0, 0, -1);

    }

    public float[] getMVPMatrix(float[] modelMatrix) {
        multiplyMM(modelViewProjectionMatrix, 0 , m_ViewProjectionMatrix, 0 , modelMatrix, 0);
        return modelViewProjectionMatrix;
    }


    public void onSurfaceChanged(int width, int height){
        float ratio = width/height;

        Matrix.orthoM(m_OrthoMatrix, 0, -width/2, width/2, -height/2, height/2, 1.0f, 1000.0f);//-ratio, ratio, -1.0f, 1.0f, 1.0f, 1000.0f);
        //Matrix.orthoM(m_OrthoMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 1.0f, 1000.0f);
       // Matrix.orthoM(m_OrthoMatrix, 0, 0, width, 0, height, 1.0f, 1000f);

        Matrix.setLookAtM(m_ViewMatrix, 0,
                m_Position.X, m_Position.Y, m_Position.Z,
                m_LookAt.X,   m_LookAt.Y,   m_LookAt.Z,
                m_UpVector.X, m_UpVector.Y, m_UpVector.Z);
    }
    public void onDrawFrame() {
        multiplyMM(m_ViewProjectionMatrix, 0, m_OrthoMatrix, 0, m_ViewMatrix, 0);
    }
}

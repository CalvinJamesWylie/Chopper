package uk.co.calvinwylie.chopperv2.dataTypes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Calvin on 16/04/2015.
 */
public class VertexArray {

    private final FloatBuffer m_FloatBuffer;

    public VertexArray(float[] vertexData){
        m_FloatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * (Float.SIZE/8)) //divide by 8 to convert to bytes from bits.
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride){
        m_FloatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, m_FloatBuffer);
        glEnableVertexAttribArray(attributeLocation);
        m_FloatBuffer.position(0);
    }

    public void scale(float multiplier){

    }
}

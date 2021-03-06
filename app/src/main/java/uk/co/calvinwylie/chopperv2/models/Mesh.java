package uk.co.calvinwylie.chopperv2.models;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

import static android.opengl.GLES10.glDrawElements;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES11.glGenBuffers;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;


public class Mesh {
    private int size;
    private FloatBuffer m_VertexBuffer;
    private FloatBuffer m_TexCoordBuffer;
    private FloatBuffer m_NormalBuffer;
    private ShortBuffer m_IndexBuffer;


    public Mesh(){
        size = 0;
    }

    public void addVertices(Vector3[] positions, Vector2[] texCoords, Vector3[] normals, short[] indices){
        size = indices.length;
        m_VertexBuffer = ModelUtil.createFlippedBuffer(positions);
        m_TexCoordBuffer = ModelUtil.createFlippedBuffer(texCoords);
        m_NormalBuffer = ModelUtil.createFlippedBuffer(normals);
        m_IndexBuffer = ModelUtil.createFlippedBuffer(indices);
    }

    public void enableVertexAttribArrays(int positionAttributeLocation, int textureAttributeLocation, int normalAttributeLocation){
        glEnableVertexAttribArray(positionAttributeLocation);
        glVertexAttribPointer(positionAttributeLocation, 3, GL_FLOAT, false, Vector3.SIZE << 2, m_VertexBuffer);
        glEnableVertexAttribArray(textureAttributeLocation);
        glVertexAttribPointer(textureAttributeLocation, 2, GL_FLOAT, false, Vector2.SIZE << 2, m_TexCoordBuffer);
        glEnableVertexAttribArray(normalAttributeLocation);
        glVertexAttribPointer(normalAttributeLocation, 3, GL_FLOAT, false, Vector3.SIZE << 2, m_NormalBuffer);
    }

    public void draw(){

        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_SHORT, m_IndexBuffer);

    }
}

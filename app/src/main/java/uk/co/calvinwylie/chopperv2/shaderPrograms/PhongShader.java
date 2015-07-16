package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by Calvin on 15/07/2015.
 */
public class PhongShader  extends Shader {


    private static Vector3 m_AmbientLight = new Vector3(0.5f, 0.5f, 0.5f);

    public PhongShader(Context context){
        super(context, R.raw.phong_vertex_shader, R.raw.phong_fragment_shader);

        addUniform("u_Matrix");
        addUniform("u_TextureUnit");
        addUniform("u_AmbientLight");

        addAttribute("a_Position");
        addAttribute("a_TextureCoords");
    }

    public void setUniforms(float[] matrix, int textureId){
        //Pass the matrix into the shader program
        setUniform("u_Matrix", matrix);
        setUniform("u_AmbientLight", m_AmbientLight);
        //Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);
        //Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId);
        //Tell the texture uniform sample to use this texture in the shader by
        //telling it to read from texture unit 0.
        setUniform("u_TextureUnit", 0);
    }

    public int getPositionAttributeLocation(){
        int result = getAttributeLocation("a_Position");
        if(result == 0xFFFFFFFF){
            Log.e("", "a_Position aint a thing");
            return result;
        }else {
            return result;
        }
    }
    public int getTextureCoordinatesAttributeLocation(){
        int result = getAttributeLocation("a_TextureCoords");
        if(result == 0xFFFFFFFF){
            Log.e("", "a_TextureCoords aint a thing");
            return result;
        }else {
            return result;
        }
    }

    public static Vector3 getAmbientLight() {
        return m_AmbientLight;
    }

    public static void setAmbientLight(Vector3 m_AmbientLight) {
        PhongShader.m_AmbientLight = m_AmbientLight;
    }

}



package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;


public class TextureShader extends Shader {

    private static final String tag = "TextureShader";

    public TextureShader(Context context){
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        addUniform("u_Matrix");
        addUniform("u_TextureUnit");

        addAttribute("a_Position");
        addAttribute("a_TextureCoordinates");
        addAttribute("a_Normal");
    }

    public void setUniforms(float[] matrix, int textureId){
        //Pass the matrix into the shader program
        setUniform("u_Matrix", matrix);
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
        if(result == -1){
            Log.e(tag, "a_Position aint a thing");
            return result;
        }else {
            return result;
        }
    }
    public int getTextureCoordinatesAttributeLocation(){
        int result = getAttributeLocation("a_TextureCoordinates");

        if(result == -1){
            Log.e(tag, "a_TextureCoords aint a thing");
            return result;
        }else {
            return result;
        }
    }

    public int getNormalAttributeLocation() {
        int result = getAttributeLocation("a_Normal");
        if(result == -1){
            Log.e(tag, "a_Normal aint a thing");
            return result;
        }else {
            return result;
        }
    }
}

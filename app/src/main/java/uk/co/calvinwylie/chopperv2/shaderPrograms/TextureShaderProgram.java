package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;

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

/**
 * Created by Calvin on 16/04/2015.
 */
public class TextureShaderProgram extends ShaderProgram {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE =
                    ( POSITION_COMPONENT_COUNT
                    + TEXTURE_COORDINATES_COMPONENT_COUNT )
                    * 4; // TODO remove magic number.

    //Uniform locations
    private final int u_MatrixLocation;
    private final int u_TextureUnitLocation;
    //private final int uTextureUnit1Location;

    //Attribute locations
    private final int a_PositionLocation;
    private final int a_TextureCoordinatesLocation;

    public TextureShaderProgram(Context context){
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        //Retrieve uniform locations for the shader program.
        u_MatrixLocation = glGetUniformLocation(program, U_MATRIX);
        u_TextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
       // uTextureUnit1Location = glGetUniformLocation(program, U_TEXTURE_UNIT_1);

        //Retrieve attribute locations for the shader program.
        a_PositionLocation = glGetAttribLocation(program, A_POSITION);
        a_TextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }
    public void setUniforms(float[] matrix, int textureId){
        //Pass the matrix into the shader program
        glUniformMatrix4fv(u_MatrixLocation, 1, false, matrix, 0);

        //Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);
        //Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId);
        //Tell the texture uniform sample to use this texture in the shader by
        //telling it to read from texture unit 0.
        glUniform1i(u_TextureUnitLocation, 0);

//        glActiveTexture(GL_TEXTURE1);
//        glBindTexture(GL_TEXTURE_2D, textureId2);
//        glUniform1i(uTextureUnit1Location, 1);

    }

    public void bindData(VertexArray vertexArray){
        vertexArray.setVertexAttribPointer(
                0,
                getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
    }



    public int getPositionAttributeLocation(){
        return a_PositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation(){
        return a_TextureCoordinatesLocation;
    }
}
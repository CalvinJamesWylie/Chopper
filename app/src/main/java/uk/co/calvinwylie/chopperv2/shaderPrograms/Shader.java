package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;
import android.util.Log;

import uk.co.calvinwylie.chopperv2.util.ShaderHelper;
import uk.co.calvinwylie.chopperv2.util.TextResourceReader;

import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Calvin on 16/04/2015.
 */
public class Shader {
    //Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";

    //Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    //Shader program
    protected final int program;

    protected Shader(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){
        //Compile the shaders and link the program.

        //Log.e("ddd", TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId));

        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId)
        );
    }

    public void useProgram(){
        //Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }
}
